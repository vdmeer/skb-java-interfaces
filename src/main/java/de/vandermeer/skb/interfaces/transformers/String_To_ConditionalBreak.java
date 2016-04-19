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

package de.vandermeer.skb.interfaces.transformers;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrTokenizer;

/**
 * Converts a String to a String array processing conditional line breaks.
 * Conditional line breaks are CR LF, CR, LF, &lt;br&gt;, and &lt;br/&gt;.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public interface String_To_ConditionalBreak extends IsTransformer<String, String[]> {

	/**
	 * Transforms a String to a String[] processing conditional line breaks.
	 * Conditional line breaks are CR LF, CR, LF, &lt;br&gt;, and &lt;br/&gt;.
	 * 
	 * The method proceeds as follows:
	 * 
	 *     . replace all line breaks (CR LF, CR, LF) into HTML4 line break entity &lt;br&gt;
	 *     . replace all HTML4 line break entities to HTML5 entities (as in self-closing &lt;br/&gt; entity).
	 *     . use a `tokenizer` to process the resulting string (not ignoring empty tokens, since they mark required line breaks).
	 *     . return the array of the `tokenizer`
	 * 
	 * As a result, a string containing 1 line break will be converted into an array length 2:
	 * ----
	 * String: "paragraph 1\nparagraph 2"
	 * Array:  {paragraph 1,paragraph 2}
	 * ----
	 * 
	 * A string containing 2 line breaks will be converted into a string array with 3 entries (first paragraph, additional line break, second paragraph):
	 * ----
	 * String: "paragraph 1\n\nparagraph 2"
	 * Array: {paragraph 1,,paragraph 2}
	 * ----
	 * 
	 * @param s input string
	 * @return array with conditional line breaks converted to empty entries, `null` if `s` was `null`
	 */
	@Override
	default String[] transform(String s) {
		IsTransformer.super.transform(s);
		if("".equals(s)){
			return new String[]{""};
		}

		String lfRep = StringUtils.replacePattern(s.toString(), "\\r\\n|\\r|\\n", "<br>");
		lfRep = StringUtils.replace(lfRep, "<br>", "<br/>");
		lfRep = StringUtils.replace(lfRep, "<br/>", "<br />");
		StrTokenizer tok = new StrTokenizer(lfRep, "<br />").setIgnoreEmptyTokens(false);
		return tok.getTokenArray();
	}

	/**
	 * Creates a transformer that takes a String and returns a String[] with conditional line breaks being processed.
	 * Conditional line breaks are CR LF, CR, LF, &lt;br&gt;, and &lt;br/&gt;.
	 * @return new transformer
	 */
	static String_To_ConditionalBreak create(){
		return new String_To_ConditionalBreak() {};
	}

	/**
	 * Returns a string array with conditional line breaks processed.
	 * Conditional line breaks are CR LF, CR, LF, &lt;br&gt;, and &lt;br/&gt;.
	 * @param s input string
	 * @return array with conditional line breaks converted to empty entries, `null` if `s` was `null`
	 */
	static String[] convert(String s){
		return String_To_ConditionalBreak.create().transform(s);
	}

}
