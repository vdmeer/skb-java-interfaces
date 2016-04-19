/* Copyright 2016 Sven van der Meer <vdmeer.sven@mykolab.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.vandermeer.skb.interfaces.transformers.textformat;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.text.StrTokenizer;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.tuple.Pair;

import de.vandermeer.skb.interfaces.transformers.IsTransformer;

/**
 * Takes some text and returns formatted text with optionally different width for top/bottom.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public interface Text_To_WrappedFormat extends IsTransformer<String, Pair<ArrayList<String>, ArrayList<String>>> {

	/** The line break used for conditional line breaks. */
	static String LINEBREAK = "<br />";

	/**
	 * Returns the width for the overall wrapping, defaults to 80.
	 * @return overall wrapping width
	 */
	default int getWidth(){
		return 80;
	}

	/**
	 * Returns the settings for the top part of the formatted text, defaults to null.
	 * @return top settings
	 */
	default Pair<Integer, Integer> getTopSettings(){
		return null;
	}

	@Override
	default Pair<ArrayList<String>, ArrayList<String>> transform(String input) {
		Validate.notBlank(input);
		Validate.isTrue(this.getWidth()>0);

		ArrayList<String> topList = new ArrayList<>();
		ArrayList<String> bottomList = new ArrayList<>();

		//an emergency break, counting loops to avoid endless loops
		int count;

		String text = StringUtils.replacePattern(input, "\\r\\n|\\r|\\n", LINEBREAK);
		text = StringUtils.replace(text, "<br>", LINEBREAK);
		text = StringUtils.replace(text, "<br/>", LINEBREAK);

		StrBuilder sb = new StrBuilder(text);
		if(this.getTopSettings()!=null){
			//we have a top request, do that one first
			Validate.notNull(this.getTopSettings().getLeft());
			Validate.notNull(this.getTopSettings().getRight());
			Validate.isTrue(this.getTopSettings().getLeft()>0);
			Validate.isTrue(this.getTopSettings().getRight()>0);

			int topLines = this.getTopSettings().getLeft();
			int topWidth = this.getTopSettings().getRight();
			count = 0;

			while(sb.size()>0 && topLines>0 && count++<200){
				if(sb.startsWith(LINEBREAK)){
					sb.replaceFirst(LINEBREAK, "");
				}
				String s = null;
				boolean wln = false;
				if(sb.indexOf(LINEBREAK)>0){
					s = sb.substring(0, sb.indexOf(LINEBREAK));
					wln = true;
					//sb.replace(0, sb.indexOf(LINEBREAK) + LINEBREAK.length(), "");
				}
				else{
					s = sb.toString();
					//sb.clear();
				}
				String wrap = WordUtils.wrap(s, topWidth, LINEBREAK, true);
				StrTokenizer tok = new StrTokenizer(wrap, LINEBREAK).setIgnoreEmptyTokens(false);
				String[] ar = tok.getTokenArray();
				if(ar.length<=topLines){
					//all lines done, cleanup
					for(String str : ar){
						topList.add(str.trim());
					}
					if(wln==true){
						//if we had a conditional linebreak there might be more text, remove the line we processed
						sb.replace(0, sb.indexOf(LINEBREAK) + LINEBREAK.length(), "");
					}
					else{
						//no conditional line break, clean builder
						sb.clear();
					}
					topLines = 0;
				}
				else{
					//we have more lines than we need, so remove the text we have from the builder and copy processed lines
					StrBuilder replace = new StrBuilder();
					for(int i=0; i<topLines; i++){
						topList.add(ar[i].trim());
						replace.appendSeparator(' ').append(ar[i]);
					}
					if(wln==true){
						replace.append(LINEBREAK);
					}
					sb.replaceFirst(replace.toString(), "");
					topLines = 0;
				}
			}
		}

		//no top, simple wrapping with recognition of conditional line breaks
		count = 0;
		while(sb.size()>0 && count++<200){
			if(sb.startsWith(LINEBREAK)){
				sb.replaceFirst(LINEBREAK, "");
			}
			String s = null;
			if(sb.indexOf(LINEBREAK)>0){
				s = sb.substring(0, sb.indexOf(LINEBREAK));
				sb.replace(0, sb.indexOf(LINEBREAK) + LINEBREAK.length(), "");
			}
			else{
				s = sb.toString();
				sb.clear();
			}
			s = WordUtils.wrap(s, this.getWidth(), LINEBREAK, true);
			StrTokenizer tok = new StrTokenizer(s, LINEBREAK).setIgnoreEmptyTokens(false);
			for(String str : tok.getTokenArray()){
				bottomList.add(str.trim());
			}
		}

		return Pair.of(topList, bottomList);
	}

	/**
	 * Returns a new transformer.
	 * @param width the width to wrap lines for
	 * @return new transformer
	 */
	static Text_To_WrappedFormat create(int width){
		return new Text_To_WrappedFormat() {
			@Override
			public int getWidth(){
				return width;
			}
		};
	}

	/**
	 * Returns a new transformer.
	 * @param width the width to wrap lines for
	 * @param top the settings for top as pair of lines and width
	 * @return new transformer
	 */
	static Text_To_WrappedFormat create(int width, Pair<Integer, Integer> top){
		return new Text_To_WrappedFormat() {
			@Override
			public int getWidth(){
				return width;
			}

			@Override
			public Pair<Integer, Integer> getTopSettings(){
				return top;
			}
		};
	}

	/**
	 * Transforms text to wrapped lines.
	 * @param text the text to transform
	 * @param width the width to wrap lines for
	 * @return transformed text
	 */
	static Pair<ArrayList<String>, ArrayList<String>> convert(String text, int width){
		return create(width).transform(text);
	}

	/**
	 * Transforms text to wrapped lines.
	 * @param text the text to transform
	 * @param width the width to wrap lines for
	 * @param top the settings for top as pair of lines and width
	 * @return transformed text
	 */
	static Pair<ArrayList<String>, ArrayList<String>> convert(String text, int width, Pair<Integer, Integer> top){
		return create(width, top).transform(text);
	}
}
