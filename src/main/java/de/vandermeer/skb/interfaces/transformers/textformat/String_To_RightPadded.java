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

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.interfaces.transformers.IsTransformer;

/**
 * Converts a string to a right-padded String of given length.
 * If the given string length is less than the required length, padding characters will be added to the right.
 * The returned string will be of the required length, blank strings will have only padding characters.
 * 
 * The method can be further customized by
 * - Setting an inner white space character - replace all white spaces in the text with that character
 * 
 * The default length is {@link #DEFAULT_LENGTH}.
 * The default padding character is {@link #DEFAULT_PADDING_CHARACTER}.
 * The default inner white space character is {@link #DEFAULT_INNER_WHITESPACE_CHARACTER}.
 * 
 * The actual return object is of type {@link StrBuilder} to minimize the creation of lots of strings for more complex transformations.
 * An implementation can chose to provide a builder to append the padded string to as well.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public interface String_To_RightPadded extends IsTransformer<String, StrBuilder> {

	/** The default width, set to 80. */
	static int DEFAULT_LENGTH = 80;

	/** Default padding character, set to ' '. */
	static char DEFAULT_PADDING_CHARACTER = ' ';

	/** Default white space replacement character, set to ' '. */
	static char DEFAULT_INNER_WHITESPACE_CHARACTER = ' ';

	/**
	 * Returns the required length for the conversion.
	 * @return length, default is {@link #DEFAULT_LENGTH}
	 */
	default int getLength(){
		return DEFAULT_LENGTH;
	}

	/**
	 * Returns the white space replacement character.
	 * @return white space replacement character, default is {@link #DEFAULT_INNER_WHITESPACE_CHARACTER}
	 */
	default Character getInnerWsChar(){
		return DEFAULT_INNER_WHITESPACE_CHARACTER;
	}

	/**
	 * Returns the padding character for the conversion.
	 * @return padding character, cannot be null, default is {@link #DEFAULT_PADDING_CHARACTER}
	 */
	default Character getPaddingChar(){
		return DEFAULT_PADDING_CHARACTER;
	}

	/**
	 * Returns a builder to append the padded string to, rather than creating a new builder.
	 * @return builder, can be null, if not null the padding string will be appended to it
	 */
	default StrBuilder getBuilderForAppend(){
		return null;
	}

	@Override
	default StrBuilder transform(String s) {
		IsTransformer.super.transform(s);
		StrBuilder ret = (this.getBuilderForAppend()==null)?new StrBuilder(this.getLength()):this.getBuilderForAppend();

		String right = (s==null)?"":s;
		right = right.replace(' ', this.getInnerWsChar());
		ret.appendFixedWidthPadLeft(right, this.getLength(), this.getPaddingChar());
		return ret;
	}

	/**
	 * Creates a transformer that converts a string to a right-padded string of given length.
	 * @param length the required length (must be &gt;0)
	 * @param paddingChar the padding character, default is used if null
	 * @param innerWsChar inner white space replacement character
	 * @param builder an optional builder to append the padded string to, used if set, ignored if null
	 * @return new transformer
	 * @see String_To_RightPadded interface description for how the converter works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static String_To_RightPadded create(int length, Character paddingChar, Character innerWsChar, StrBuilder builder){
		Validate.validState(length>0, "cannot work with lenght of less than 1");
		return new String_To_RightPadded() {
			@Override
			public int getLength(){
				return length;
			}

			@Override
			public Character getInnerWsChar() {
				return (innerWsChar==null)?String_To_RightPadded.super.getInnerWsChar():innerWsChar;
			}

			@Override
			public Character getPaddingChar(){
				return (paddingChar==null)?String_To_RightPadded.super.getPaddingChar():paddingChar;
			}

			@Override
			public StrBuilder getBuilderForAppend(){
				return builder;
			}
		};
	}

	/**
	 * Returns right-padded string of given length using the default padding character.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @return right-padded string
	 * @see String_To_RightPadded interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length){
		return String_To_RightPadded.create(length, null, null, null).transform(s);
	}

	/**
	 * Returns right-padded string of given length.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @param builder an optional builder to append the padded string to, used if set, ignored if null
	 * @return right-padded string
	 * @see String_To_RightPadded interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length, StrBuilder builder){
		return String_To_RightPadded.create(length, null, null, builder).transform(s);
	}

	/**
	 * Returns right-padded string of given length.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @param paddingChar the padding character, default is used if null
	 * @return right-padded string
	 * @see String_To_RightPadded interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length, Character paddingChar){
		return String_To_RightPadded.create(length, paddingChar, null, null).transform(s);
	}

	/**
	 * Returns right-padded string of given length.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @param paddingChar the padding character, default is used if null
	 * @param innerWsChar inner white space replacement character, default is used if null
	 * @return right-padded string
	 * @see String_To_RightPadded interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length, Character paddingChar, Character innerWsChar){
		return String_To_RightPadded.create(length, paddingChar, innerWsChar, null).transform(s);
	}

	/**
	 * Returns right-padded string of given length.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @param paddingChar the padding character, default is used if null
	 * @param builder an optional builder to append the padded string to, used if set, ignored if null
	 * @return right-padded string
	 * @see String_To_RightPadded interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length, Character paddingChar, StrBuilder builder){
		return String_To_RightPadded.create(length, paddingChar, null, builder).transform(s);
	}

	/**
	 * Returns right-padded string of given length.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @param paddingChar the padding character, default is used if null
	 * @param innerWsChar inner white space replacement character, default is used if null
	 * @param builder an optional builder to append the padded string to, used if set, ignored if null
	 * @return right-padded string
	 * @see String_To_RightPadded interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length, Character paddingChar, Character innerWsChar, StrBuilder builder){
		return String_To_RightPadded.create(length, paddingChar, innerWsChar, builder).transform(s);
	}
}
