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
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.tuple.Pair;

import de.vandermeer.skb.interfaces.strategies.IsCollectionStrategy;
import de.vandermeer.skb.interfaces.strategies.collections.list.ArrayListStrategy;
import de.vandermeer.skb.interfaces.transformers.ClusterElementTransformer;
import de.vandermeer.skb.interfaces.transformers.IsTransformer;
import de.vandermeer.skb.interfaces.transformers.Transformer;

/**
 * Swiss army knife for formatting text, with several options for alignments, formats, inserted characters, and variable width.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public interface Text_To_FormattedText extends IsTransformer<String, Collection<StrBuilder>> {

	/** Alignment option for left aligned text. */
	static int ALIGN_LEFT = 1;

	/** Alignment option for centered text. */
	static int ALIGN_CENTER = 2;

	/** Alignment option for right aligned text. */
	static int ALIGN_RIGHT = 3;

	/** Alignment option for justified text. */
	static int ALIGN_JUSTIFIED = 4;

	/** Alignment option for justified text, last line left aligned. */
	static int ALIGN_JUSTIFIED_LEFT = 5;

	/** Alignment option for justified text, last line right aligned. */
	static int ALIGN_JUSTIFIED_RIGHT = 6;

	/** Format option for no special formatting. */
	static int FORMAT_NONE = 100;

	/** Format option for text with the first line using an indentation. */
	static int FORMAT_FIRST_LINE = 101;

	/** Format option for text with all lines but the first line using an indentation (hanging paragraph). */
	static int FORMAT_HANGING_PARAGRAPH = 102;

	/** Format option combining a first line indentation with a hanging paragraph. */
	static int FORMAT_FIRSTLINE_AND_HANGINGPARAGRAPH = 103;

	/** Format option for text using a dropped capital letter replacing the first character of the first word of the first sentence, requries a special library of those letters. */
	static int FORMAT_DROPCAP = 120;

	/** Format option for text with a dropped capital letter using a padding. */
	static int FORMAT_DROPCAP_WITH_PADDING = 121;

	/** The default width, set to 80. */
	static int DEFAULT_TEXT_WIDTH = 80;

	/** Default left padding character, set to ' '. */
	static char DEFAULT_LEFT_PADDING_CHARACTER = ' ';

	/** Default right padding character, set to ' '. */
	static char DEFAULT_RIGHT_PADDING_CHARACTER = ' ';

	/** Default white space replacement character, set to ' '. */
	static char DEFAULT_INNER_WHITESPACE_CHARACTER = ' ';

	/** Default hanging indent, set to 4. */
	static int DEFAULT_HANGING_INDENTATION = 4;

	/** Default first line indentation, set to 4. */
	static int DEFAULT_FIRSTLINE_INDENTATION = 4;

	/** Characters to be used between the drop cap and the text, set to 3. */
	static int DEFAULT_CHARS_BETWEEN_DROPCAP_AND_TEXT = 3;

	/** Empty lines to be added after the drop cap, set to 1. */
	static int DEFAULT_LINES_AFTER_DROPCAP = 1;

	/** Default collection strategy for the returned collection, defaults to an array list strategy. */
	static IsCollectionStrategy<?, StrBuilder> DEFAULT_COLLECTION_STRATEGY = ArrayListStrategy.create();

	/**
	 * Returns the required alignment, defaults to {@link #ALIGN_JUSTIFIED_LEFT}.
	 * @return alignment
	 */
	default int getAlignment(){
		return ALIGN_JUSTIFIED_LEFT;
	}

	/**
	 * Returns the required format, defaults to {@link #FORMAT_NONE}.
	 * @return format
	 */
	default int getFormat(){
		return FORMAT_NONE;
	}

	/**
	 * Returns the in-text whitespace character, defaults to {@link #DEFAULT_INNER_WHITESPACE_CHARACTER}.
	 * @return in-text whitespace character
	 */
	default Character getInnerWsChar(){
		return DEFAULT_INNER_WHITESPACE_CHARACTER;
	}

	/**
	 * Returns the left padding character, defaults to {@link #DEFAULT_LEFT_PADDING_CHARACTER}.
	 * @return left padding character
	 */
	default Character getLeftPaddingChar(){
		return DEFAULT_LEFT_PADDING_CHARACTER;
	}

	/**
	 * Returns the right padding character, defaults to {@link #DEFAULT_RIGHT_PADDING_CHARACTER}.
	 * @return right padding character
	 */
	default Character getRightPaddingChar(){
		return DEFAULT_RIGHT_PADDING_CHARACTER;
	}

	/**
	 * Returns the text width, defaults to {@link #DEFAULT_TEXT_WIDTH}.
	 * @return text width
	 */
	default int getTextWidth(){
		return DEFAULT_TEXT_WIDTH;
	}

	/**
	 * Returns the collection strategy, defaults to {@link #DEFAULT_COLLECTION_STRATEGY}.
	 * @return collection strategy
	 */
	default IsCollectionStrategy<?, StrBuilder> getCollectionStrategy(){
		return DEFAULT_COLLECTION_STRATEGY;
	}

	/**
	 * Returns the hanging paragraph indentation, defaults to {@link #DEFAULT_HANGING_INDENTATION}.
	 * @return hanging paragraph indentation
	 */
	default int getHangingIndentation(){
		return DEFAULT_HANGING_INDENTATION;
	}

	/**
	 * Returns the first line indentation, defaults to {@link #DEFAULT_FIRSTLINE_INDENTATION}.
	 * @return first line indentation
	 */
	default int getFirstlineIndentation(){
		return DEFAULT_FIRSTLINE_INDENTATION;
	}

	/**
	 * Returns the number of characters between a dropped capital letter and text lines, default to {@link #DEFAULT_CHARS_BETWEEN_DROPCAP_AND_TEXT}.
	 * @return characters between a dropped capital letter and text lines
	 */
	default int getCharsBetweenDroppcapAndText(){
		return DEFAULT_CHARS_BETWEEN_DROPCAP_AND_TEXT;
	}

	/**
	 * Returns the lines added after a dropped capital letter, defaults to {@link #DEFAULT_LINES_AFTER_DROPCAP}.
	 * @return lines added after a dropped capital letter
	 */
	default int getLinesAfterDropcap(){
		return DEFAULT_LINES_AFTER_DROPCAP;
	}

	/**
	 * Transforms a string builder to a collection using the main transform method.
	 * @param sb input string builder, cannot be null
	 * @return formatted text as a collection of string builders
	 */
	default Collection<StrBuilder> transform(StrBuilder sb) {
		Validate.notNull(sb);
		return this.transform(sb.toString());
	}

	/**
	 * Returns the dropped capital letter used if such format is required.
	 * @return dropped capital letter, cannot be null or have null elements, each line in array must have same length
	 */
	String[] getDropCap();

	@Override
	default Collection<StrBuilder> transform(String s) {
		//check all settings for being valid, throw exceptions if not
		IsTransformer.super.transform(s);
		Validate.validState(ArrayUtils.contains(new int[]{ALIGN_LEFT, ALIGN_RIGHT, ALIGN_CENTER, ALIGN_JUSTIFIED, ALIGN_JUSTIFIED_LEFT, ALIGN_JUSTIFIED_RIGHT}, this.getAlignment()), "unknown alignment <" + this.getAlignment() + ">");
		Validate.validState(ArrayUtils.contains(new int[]{FORMAT_NONE, FORMAT_HANGING_PARAGRAPH, FORMAT_FIRST_LINE, FORMAT_FIRSTLINE_AND_HANGINGPARAGRAPH, FORMAT_DROPCAP, FORMAT_DROPCAP_WITH_PADDING}, this.getFormat()), "unknown format <" + this.getFormat() + ">");
		Validate.validState(this.getTextWidth()>0, "text width is less than 1, was <" + this.getTextWidth() + ">");
		Validate.notNull(this.getInnerWsChar());
		Validate.notNull(this.getLeftPaddingChar());
		Validate.notNull(this.getRightPaddingChar());
		Validate.notNull(this.getCollectionStrategy());
		Validate.validState(this.getHangingIndentation()>0, "hanging paragraph indentation was less than null, setting was <" + this.getHangingIndentation() + ">");
		Validate.validState(this.getFirstlineIndentation()>0, "first line indentation was less than null, setting was <" + this.getFirstlineIndentation() + ">");
		Validate.validState(this.getCharsBetweenDroppcapAndText()>0, "characters between dropped capital letter and text lines was less than 1, setting was <" + this.getCharsBetweenDroppcapAndText() + ">");
		Validate.validState(this.getLinesAfterDropcap()>0, "lines added after a dropped capital letter was less than 1, setting was <" + this.getLinesAfterDropcap() + ">");

		Collection<StrBuilder> ret = this.getCollectionStrategy().get();

		//if nothing is to be done return string with blanks
		if(StringUtils.isBlank(s)){
			ret.add(new StrBuilder().appendPadding(this.getTextWidth(), ' '));
			return ret;
		}

		if(this.getFormat()==FORMAT_DROPCAP || this.getFormat()==FORMAT_DROPCAP_WITH_PADDING){
			Validate.notNull(this.getDropCap());
			Validate.noNullElements(this.getDropCap());
			int l = 0;
			for(String ds : this.getDropCap()){
				if(l!=0){
					Validate.validState(l==ds.length(), "dropped capital letter has some variations in length in the array, not alowed");
				}
				l = ds.length();
			}
		}

		//all validated, start the transformation
		//first remove all excessive whitespaces from the string
		String text = String_To_NoWs.convert(s);

		//create a string array from the input text according to the format settings
		Pair<ArrayList<String>, ArrayList<String>> pair = null;
		int topWidth = 0;
		int bottomWidth = 0;
		switch(this.getFormat()){
			case FORMAT_NONE:
				topWidth = bottomWidth = this.getTextWidth();
				pair = Text_To_WrappedFormat.convert(text, topWidth);
				Validate.isTrue(pair.getLeft().size()==0);
				break;
			case FORMAT_HANGING_PARAGRAPH:
				topWidth = this.getTextWidth();
				bottomWidth = this.getTextWidth()-this.getHangingIndentation();
				pair = Text_To_WrappedFormat.convert(text, bottomWidth, Pair.of(1, topWidth));
				Validate.isTrue(pair.getLeft().size()==1);
				break;
			case FORMAT_FIRST_LINE:
				topWidth = this.getTextWidth()-this.getFirstlineIndentation();
				bottomWidth = this.getTextWidth();
				pair = Text_To_WrappedFormat.convert(text, bottomWidth, Pair.of(1, topWidth));
				Validate.isTrue(pair.getLeft().size()==1);
				break;
			case FORMAT_FIRSTLINE_AND_HANGINGPARAGRAPH:
				topWidth = this.getTextWidth()-this.getFirstlineIndentation();
				bottomWidth = this.getTextWidth()-this.getHangingIndentation();
				pair = Text_To_WrappedFormat.convert(text, bottomWidth, Pair.of(1, topWidth));
				Validate.isTrue(pair.getLeft().size()==1);
				break;
			case FORMAT_DROPCAP:
				topWidth = this.getTextWidth()-this.getDropCap()[0].length()-1;
				bottomWidth = this.getTextWidth();
				pair = Text_To_WrappedFormat.convert(text.substring(1), bottomWidth, Pair.of(this.getDropCap().length+this.getLinesAfterDropcap(), topWidth));
				Validate.isTrue(pair.getLeft().size()==this.getDropCap().length+1);
				break;
			case FORMAT_DROPCAP_WITH_PADDING:
				topWidth = this.getTextWidth()-this.getDropCap()[0].length()-this.getCharsBetweenDroppcapAndText();
				bottomWidth = this.getTextWidth();
				pair = Text_To_WrappedFormat.convert(text.substring(1), bottomWidth, Pair.of(this.getDropCap().length+this.getLinesAfterDropcap(), topWidth));
				Validate.isTrue(pair.getLeft().size()==this.getDropCap().length+1);
		}

		//we have a pair of wrapped lines (top and bottom), apply alignment
		Transformer<String, StrBuilder> topTr = null;
		Transformer<String, StrBuilder> bottomTr = null;
		switch(this.getAlignment()){
			case ALIGN_LEFT:
				topTr = String_To_LeftPadded.create(topWidth, this.getRightPaddingChar(), this.getInnerWsChar(), null);
				bottomTr = String_To_LeftPadded.create(bottomWidth, this.getRightPaddingChar(), this.getInnerWsChar(), null);
				break;
			case ALIGN_RIGHT:
				topTr = String_To_RightPadded.create(topWidth, this.getLeftPaddingChar(), this.getInnerWsChar(), null);
				bottomTr = String_To_RightPadded.create(bottomWidth, this.getLeftPaddingChar(), this.getInnerWsChar(), null);
				break;
			case ALIGN_CENTER:
				topTr = String_To_Centered.create(topWidth, this.getLeftPaddingChar(), this.getRightPaddingChar(), this.getInnerWsChar(), null);
				bottomTr = String_To_Centered.create(bottomWidth, this.getLeftPaddingChar(), this.getRightPaddingChar(), this.getInnerWsChar(), null);
				break;
			case ALIGN_JUSTIFIED:
			case ALIGN_JUSTIFIED_LEFT:
			case ALIGN_JUSTIFIED_RIGHT:
				topTr = String_To_Justified.create(topWidth, this.getInnerWsChar(), null);
				bottomTr = String_To_Justified.create(bottomWidth, this.getInnerWsChar(), null);
				break;
		}

		Collection<StrBuilder> top = ClusterElementTransformer.create().transform(pair.getLeft(), topTr, this.getCollectionStrategy());
		Collection<StrBuilder> bottom = ClusterElementTransformer.create().transform(pair.getRight(), bottomTr, this.getCollectionStrategy());

		//adjust the last line of we had the special justified alignments
		if(bottom.size()>0 && (this.getAlignment()==ALIGN_JUSTIFIED_LEFT || this.getAlignment()==ALIGN_JUSTIFIED_RIGHT)){
			// remove last entry in the collection, we want to replace that one
			Object[] objAr = bottom.toArray();
			Object line = objAr[objAr.length-1];
			bottom.remove(objAr[objAr.length-1]);

			//get the string back to a normal string
			String lineString = line.toString().replaceAll(this.getInnerWsChar().toString(), " ").replaceAll("\\h+", " ");

			// now add a new one with the requested alignment
			if(this.getAlignment()==ALIGN_JUSTIFIED_LEFT){
				bottom.add(String_To_LeftPadded.convert(lineString, bottomWidth, this.getRightPaddingChar()));
			}
			if(this.getAlignment()==ALIGN_JUSTIFIED_RIGHT){
				bottom.add(String_To_RightPadded.convert(lineString, bottomWidth, this.getLeftPaddingChar()));
			}
		}

		//do the format post processing
		switch(this.getFormat()){
			case FORMAT_NONE:
				ret.addAll(top);
				ret.addAll(bottom);
				break;
			case FORMAT_HANGING_PARAGRAPH:
				ret.addAll(top);
				for(StrBuilder b : bottom){
					ret.add(new StrBuilder().appendPadding(this.getHangingIndentation(), this.getLeftPaddingChar()).append(b));
				}
				break;
			case FORMAT_FIRST_LINE:
				for(StrBuilder t : top){
					ret.add(new StrBuilder().appendPadding(this.getFirstlineIndentation(), this.getLeftPaddingChar()).append(t));
				}
				ret.addAll(bottom);
				break;
			case FORMAT_FIRSTLINE_AND_HANGINGPARAGRAPH:
				for(StrBuilder t : top){
					ret.add(new StrBuilder().appendPadding(this.getFirstlineIndentation(), this.getLeftPaddingChar()).append(t));
				}
				for(StrBuilder b : bottom){
					ret.add(new StrBuilder().appendPadding(this.getHangingIndentation(), this.getLeftPaddingChar()).append(b));
				}
				break;
			case FORMAT_DROPCAP:
				int count = 0;
				for(StrBuilder t : top){
					if(count<this.getDropCap().length){
						ret.add(new StrBuilder().append(this.getDropCap()[count]).append(' ').append(t));
					}
					else{
						ret.add(new StrBuilder().appendPadding(this.getDropCap()[0].length(), ' ').append(' ').append(t));
					}
					count++;
				}
				ret.addAll(bottom);
				break;
			case FORMAT_DROPCAP_WITH_PADDING:
				count = 0;
				for(StrBuilder t : top){
					if(count<this.getDropCap().length){
						ret.add(new StrBuilder().append(this.getDropCap()[count]).appendPadding(this.getCharsBetweenDroppcapAndText(), ' ').append(t));
					}
					else{
						ret.add(new StrBuilder().appendPadding(this.getDropCap()[0].length(), ' ').appendPadding(this.getCharsBetweenDroppcapAndText(), ' ').append(t));
					}
					count++;
				}
				ret.addAll(bottom);
		}

		return ret;
	}

	/**
	 * Creates a new transformer.
	 * @param textWidth the text width (width of each line in the returned collection), should not be less than 1
	 * @param alignment the option for text alignment, must be a valid option
	 * @param format the option for text formatting, must be a valid option
	 * @param leftPadding character for padding on the left side of a line, can be null (then default is used)
	 * @param rightPadding character for padding on the right side of a line, can be null (then default is used)
	 * @param innerWS character for replacing whitespaces within a line (between first and last word), can be null (then default is used)
	 * @param hangingIndentation indentation for a hanging paragraph format, 0 will result in default being used
	 * @param firstlineIndentation indentation for the first line in the first line format, 0 will result in default being used
	 * @param dropCap a dropped capital letter for dropped capital formats, should not be null if relevant format is set
	 * @param charsBetweenDroppcapAndText characters between the dropped capital letter and text, 0 will result in default being used
	 * @param linesAfterDropcap lines added after the dropped capital letter with same indentation, 0 will result in default being used
	 * @param strategy the collection strategy for the returned collection, null means use default
	 * @return new transformer
	 */
	static Text_To_FormattedText create(int textWidth, int alignment, int format, Character leftPadding, Character rightPadding, Character innerWS, int hangingIndentation, int firstlineIndentation, String[] dropCap, int charsBetweenDroppcapAndText, int linesAfterDropcap, IsCollectionStrategy<?, StrBuilder> strategy){
		return new Text_To_FormattedText() {
			@Override
			public int getAlignment(){
				return alignment;
			}

			@Override
			public int getFormat(){
				return format;
			}

			@Override
			public int getTextWidth(){
				return textWidth;
			}

			@Override
			public Character getInnerWsChar(){
				return (innerWS==null)?Text_To_FormattedText.super.getInnerWsChar():innerWS;
			}

			@Override
			public Character getLeftPaddingChar(){
				return (leftPadding==null)?Text_To_FormattedText.super.getLeftPaddingChar():leftPadding;
			}

			@Override
			public Character getRightPaddingChar(){
				return (rightPadding==null)?Text_To_FormattedText.super.getRightPaddingChar():rightPadding;
			}

			@Override
			public IsCollectionStrategy<?, StrBuilder> getCollectionStrategy(){
				return (strategy==null)?Text_To_FormattedText.super.getCollectionStrategy():strategy;
			}

			@Override
			public int getHangingIndentation(){
				return (hangingIndentation<1)?Text_To_FormattedText.super.getHangingIndentation():hangingIndentation;
			}

			@Override
			public int getFirstlineIndentation(){
				return (firstlineIndentation<1)?Text_To_FormattedText.super.getFirstlineIndentation():firstlineIndentation;
			}

			@Override
			public int getCharsBetweenDroppcapAndText(){
				return (charsBetweenDroppcapAndText<1)?Text_To_FormattedText.super.getCharsBetweenDroppcapAndText():charsBetweenDroppcapAndText;
			}

			@Override
			public int getLinesAfterDropcap(){
				return (linesAfterDropcap<1)?Text_To_FormattedText.super.getLinesAfterDropcap():linesAfterDropcap;
			}

			@Override
			public String[] getDropCap() {
				return dropCap;
			}
		};
	}

	/**
	 * Transforms text to left aligned lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @return transformed text
	 */
	static Collection<StrBuilder> left(String text, int textWidth){
		return left(text, textWidth, FORMAT_NONE, null, null, null);
	}

	/**
	 * Transforms text to left aligned lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @return transformed text
	 */
	static Collection<StrBuilder> left(String text, int textWidth, int format){
		return left(text, textWidth, format, null, null, null);
	}

	/**
	 * Transforms text to left aligned lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param padding character for padding on the right side
	 * @return transformed text
	 */
	static Collection<StrBuilder> left(String text, int textWidth, int format, Character padding){
		return left(text, textWidth, format, padding, null, null);
	}

	/**
	 * Transforms text to left aligned lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param padding character for padding on the right side
	 * @param innerWS character for replacing whitespaces in the text
	 * @return transformed text
	 */
	static Collection<StrBuilder> left(String text, int textWidth, int format, Character padding, Character innerWS){
		return left(text, textWidth, format, padding, innerWS, null);
	}

	/**
	 * Transforms text to left aligned lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param padding character for padding on the right side
	 * @param innerWS character for replacing whitespaces in the text
	 * @param strategy the strategy for the returned collection
	 * @return transformed text
	 */
	static Collection<StrBuilder> left(String text, int textWidth, int format, Character padding, Character innerWS, IsCollectionStrategy<?, StrBuilder> strategy){
		return create(
				textWidth, ALIGN_LEFT, format, null, padding, innerWS,
				0, 0, null, 0, 0, strategy
		).transform(text);
	}

	/**
	 * Transforms text to centered lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @return transformed text
	 */
	static Collection<StrBuilder> center(String text, int textWidth){
		return center(text, textWidth, FORMAT_NONE, null, null, null, null);
	}

	/**
	 * Transforms text to centered lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @return transformed text
	 */
	static Collection<StrBuilder> center(String text, int textWidth, int format){
		return center(text, textWidth, format, null, null, null, null);
	}

	/**
	 * Transforms text to centered lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param padding left and right padding character
	 * @return transformed text
	 */
	static Collection<StrBuilder> center(String text, int textWidth, int format, Character padding){
		return center(text, textWidth, format, padding, padding, null, null);
	}

	/**
	 * Transforms text to centered lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param leftPadding left padding character
	 * @param rightPadding right padding character
	 * @return transformed text
	 */
	static Collection<StrBuilder> center(String text, int textWidth, int format, Character leftPadding, Character rightPadding){
		return center(text, textWidth, format, leftPadding, rightPadding, null, null);
	}

	/**
	 * Transforms text to centered lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param leftPadding left padding character
	 * @param rightPadding right padding character
	 * @param innerWS character for replacing whitespaces in the text
	 * @return transformed text
	 */
	static Collection<StrBuilder> center(String text, int textWidth, int format, Character leftPadding, Character rightPadding, Character innerWS){
		return center(text, textWidth, format, leftPadding, rightPadding, innerWS, null);
	}

	/**
	 * Transforms text to centered lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param leftPadding left padding character
	 * @param rightPadding right padding character
	 * @param innerWS character for replacing whitespaces in the text
	 * @param strategy the strategy for the returned collection
	 * @return transformed text
	 */
	static Collection<StrBuilder> center(String text, int textWidth, int format, Character leftPadding, Character rightPadding, Character innerWS, IsCollectionStrategy<?, StrBuilder> strategy){
		return create(
				textWidth, ALIGN_CENTER, format, leftPadding, rightPadding, innerWS,
				0, 0, null, 0, 0, strategy
		).transform(text);
	}

	/**
	 * Transforms text to right aligned lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @return transformed text
	 */
	static Collection<StrBuilder> right(String text, int textWidth){
		return right(text, textWidth, FORMAT_NONE, null, null, null);
	}

	/**
	 * Transforms text to right aligned lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @return transformed text
	 */
	static Collection<StrBuilder> right(String text, int textWidth, int format){
		return right(text, textWidth, format, null, null, null);
	}

	/**
	 * Transforms text to right aligned lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param padding character for padding on the left side of each line
	 * @return transformed text
	 */
	static Collection<StrBuilder> right(String text, int textWidth, int format, Character padding){
		return right(text, textWidth, format, padding, null, null);
	}

	/**
	 * Transforms text to right aligned lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param padding character for padding on the left side of each line
	 * @param innerWS character for replacing whitespaces in the text
	 * @return transformed text
	 */
	static Collection<StrBuilder> right(String text, int textWidth, int format, Character padding, Character innerWS){
		return right(text, textWidth, format, padding, innerWS, null);
	}

	/**
	 * Transforms text to right aligned lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param padding character for padding on the left side of each line
	 * @param innerWS character for replacing whitespaces in the text
	 * @param strategy the strategy for the returned collection
	 * @return transformed text
	 */
	static Collection<StrBuilder> right(String text, int textWidth, int format, Character padding, Character innerWS, IsCollectionStrategy<?, StrBuilder> strategy){
		return create(
				textWidth, ALIGN_RIGHT, format, padding, null, innerWS,
				0, 0, null, 0, 0, strategy
		).transform(text);
	}

	/**
	 * Transforms text to justified lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @return transformed text
	 */
	static Collection<StrBuilder> justified(String text, int textWidth){
		return justified(text, textWidth, FORMAT_NONE, null, null);
	}

	/**
	 * Transforms text to justified lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @return transformed text
	 */
	static Collection<StrBuilder> justified(String text, int textWidth, int format){
		return justified(text, textWidth, format, null, null);
	}

	/**
	 * Transforms text to justified lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param innerWS character for replacing whitespaces in the text
	 * @return transformed text
	 */
	static Collection<StrBuilder> justified(String text, int textWidth, int format, Character innerWS){
		return justified(text, textWidth, format, innerWS, null);
	}

	/**
	 * Transforms text to justified lines.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param innerWS character for replacing whitespaces in the text
	 * @param strategy the strategy for the returned collection
	 * @return transformed text
	 */
	static Collection<StrBuilder> justified(String text, int textWidth, int format, Character innerWS, IsCollectionStrategy<?, StrBuilder> strategy){
		return create(
				textWidth, ALIGN_JUSTIFIED, format, null, null, innerWS,
				0, 0, null, 0, 0, strategy
		).transform(text);
	}

	/**
	 * Transforms text to justified lines, last line being left aligned.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @return transformed text
	 */
	static Collection<StrBuilder> justifiedLeft(String text, int textWidth){
		return justifiedLeft(text, textWidth, FORMAT_NONE, null, null, null);
	}

	/**
	 * Transforms text to justified lines, last line being left aligned.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @return transformed text
	 */
	static Collection<StrBuilder> justifiedLeft(String text, int textWidth, int format){
		return justifiedLeft(text, textWidth, format, null, null, null);
	}

	/**
	 * Transforms text to justified lines, last line being left aligned.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param padding character for padding for the last line
	 * @return transformed text
	 */
	static Collection<StrBuilder> justifiedLeft(String text, int textWidth, int format, Character padding){
		return justifiedLeft(text, textWidth, format, padding, null, null);
	}

	/**
	 * Transforms text to justified lines, last line being left aligned.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param padding character for padding for the last line
	 * @param innerWS character for replacing whitespaces in the text
	 * @return transformed text
	 */
	static Collection<StrBuilder> justifiedLeft(String text, int textWidth, int format, Character padding, Character innerWS){
		return justifiedLeft(text, textWidth, format, padding, innerWS, null);
	}

	/**
	 * Transforms text to justified lines, last line being left aligned.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param padding character for padding for the last line
	 * @param innerWS character for replacing whitespaces in the text
	 * @param strategy the strategy for the returned collection
	 * @return transformed text
	 */
	static Collection<StrBuilder> justifiedLeft(String text, int textWidth, int format, Character padding, Character innerWS, IsCollectionStrategy<?, StrBuilder> strategy){
		return create(
				textWidth, ALIGN_JUSTIFIED_LEFT, format, null, padding, innerWS,
				0, 0, null, 0, 0, strategy
		).transform(text);
	}

	/**
	 * Transforms text to justified lines, last line being right aligned.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @return transformed text
	 */
	static Collection<StrBuilder> justifiedRight(String text, int textWidth){
		return justifiedRight(text, textWidth, FORMAT_NONE, null, null, null);
	}

	/**
	 * Transforms text to justified lines, last line being right aligned.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @return transformed text
	 */
	static Collection<StrBuilder> justifiedRight(String text, int textWidth, int format){
		return justifiedRight(text, textWidth, format, null, null, null);
	}

	/**
	 * Transforms text to justified lines, last line being right aligned.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param padding character for padding for the last line
	 * @return transformed text
	 */
	static Collection<StrBuilder> justifiedRight(String text, int textWidth, int format, Character padding){
		return justifiedRight(text, textWidth, format, padding, null, null);
	}

	/**
	 * Transforms text to justified lines, last line being right aligned.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param padding character for padding for the last line
	 * @param innerWS character for replacing whitespaces in the text
	 * @return transformed text
	 */
	static Collection<StrBuilder> justifiedRight(String text, int textWidth, int format, Character padding, Character innerWS){
		return justifiedRight(text, textWidth, format, padding, innerWS, null);
	}

	/**
	 * Transforms text to justified lines, last line being right aligned.
	 * @param text the input text
	 * @param textWidth the width of each line
	 * @param format a format for the text
	 * @param padding character for padding for the last line
	 * @param innerWS character for replacing whitespaces in the text
	 * @param strategy the strategy for the returned collection
	 * @return transformed text
	 */
	static Collection<StrBuilder> justifiedRight(String text, int textWidth, int format, Character padding, Character innerWS, IsCollectionStrategy<?, StrBuilder> strategy){
		return create(
				textWidth, ALIGN_JUSTIFIED_RIGHT, format, padding, null, innerWS,
				0, 0, null, 0, 0, strategy
		).transform(text);
	}
}
