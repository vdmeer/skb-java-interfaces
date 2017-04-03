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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.interfaces.transformers.IsTransformer;

/**
 * Converts a string to a centered string of given length.
 * If the given string length is less than the required length, padding characters will be added to the right and to the left.
 * By default, the returned string will be of the required length, blank strings will have only left padding characters.
 * 
 * The method can be further customized by
 * - Setting an inner white space character - replace all white spaces in the text with that character
 * 
 * The default length is {@link #DEFAULT_LENGTH}.
 * The default right padding character is {@link #DEFAULT_RIGHT_PADDING_CHARACTER}.
 * The default left padding character is {@link #DEFAULT_LEFT_PADDING_CHARACTER}.
 * The default inner white space character is {@link #DEFAULT_INNER_WHITESPACE_CHARACTER}.
 * 
 * The actual return object is of type {@link StrBuilder} to minimize the creation of lots of strings for more complex transformations.
 * An implementation can chose to provide a builder to append the padded string to as well.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public interface String_To_Centered extends IsTransformer<String, StrBuilder> {

	/** The default width, set to 80. */
	static int DEFAULT_LENGTH = 80;

	/** Default right padding character, set to ' '. */
	static char DEFAULT_RIGHT_PADDING_CHARACTER = ' ';

	/** Default left padding character, set to ' '. */
	static char DEFAULT_LEFT_PADDING_CHARACTER = ' ';

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
	 * Returns the right padding character for the conversion.
	 * @return right padding character, cannot be null, default is {@link #DEFAULT_RIGHT_PADDING_CHARACTER}
	 */
	default Character getRightPaddingChar(){
		return DEFAULT_RIGHT_PADDING_CHARACTER;
	}

	/**
	 * Returns the left padding character for the conversion.
	 * @return left padding character, cannot be null, default is {@link #DEFAULT_LEFT_PADDING_CHARACTER}
	 */
	default Character getLeftPaddingChar(){
		return DEFAULT_LEFT_PADDING_CHARACTER;
	}

	/**
	 * Returns a builder to append the centered string to, rather than creating a new builder.
	 * @return builder, can be null, if not null the centered string will be appended to it
	 */
	default StrBuilder getBuilderForAppend(){
		return null;
	}

	@Override
	default StrBuilder transform(String s) {
		IsTransformer.super.transform(s);
		StrBuilder ret = (this.getBuilderForAppend()==null)?new StrBuilder(this.getLength()):this.getBuilderForAppend();

		// set string and replace all inner ws with required character
		String center = (s==null)?"":s;
		center = center.replace(' ', this.getInnerWsChar());

		//get a char[] with a centered string, using paddingLedft in left and right side
		char[] car = StringUtils.center(center, this.getLength()).toCharArray();

		//change all right padding chars to the actual right padding char
		for(int i = car.length-1; i>0; i--){
			if(car[i]==' '){
				car[i] = this.getRightPaddingChar();
			}
			else{
				break;
			}
		}

		//change all remaining tmp padding chars to the actual left padding char
		for(int i = 0; i<car.length; i++){
			if(car[i]==' '){
				car[i] = this.getLeftPaddingChar();
			}
			else{
				break;
			}
		}

		//add this new string to the render object
		ret.append(car);
		return ret;
	}

	/**
	 * Creates a transformer that converts a string to a centered string of given length.
	 * @param length the required length (must be &gt;0)
	 * @param leftPaddingChar the left padding character, default is used if null
	 * @param rightPaddingChar the right padding character, default is used if null
	 * @param innerWsChar inner white space replacement character
	 * @param builder an optional builder to append the padded string to, used if set, ignored if null
	 * @return new transformer
	 * @see String_To_Centered interface description for how the converter works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static String_To_Centered create(int length, Character leftPaddingChar, Character rightPaddingChar, Character innerWsChar, StrBuilder builder){
		Validate.validState(length>0, "cannot work with lenght of less than 1");
		return new String_To_Centered() {
			@Override
			public Character getInnerWsChar() {
				return (innerWsChar==null)?String_To_Centered.super.getInnerWsChar():innerWsChar;
			}

			@Override
			public int getLength(){
				return (length<1)?String_To_Centered.super.getLength():length;
			}

			@Override
			public Character getLeftPaddingChar(){
				return (leftPaddingChar==null)?String_To_Centered.super.getLeftPaddingChar():leftPaddingChar;
			}

			@Override
			public Character getRightPaddingChar(){
				return (rightPaddingChar==null)?String_To_Centered.super.getRightPaddingChar():rightPaddingChar;
			}

			@Override
			public StrBuilder getBuilderForAppend(){
				return builder;
			}
		};
	}

	/**
	 * Returns centered string of given length using default padding characters.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @return centered string
	 * @see String_To_Centered interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length){
		return String_To_Centered.create(length, null, null, null, null).transform(s);
	}

	/**
	 * Returns centered string of given length using default padding characters.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @param builder an optional builder to append the padded string to, used if set, ignored if null
	 * @return centered string
	 * @see String_To_Centered interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length, StrBuilder builder){
		return String_To_Centered.create(length, null, null, null, builder).transform(s);
	}

	/**
	 * Returns centered string of given length using the same padding character on both sides.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @param paddingChar the padding character for left and right padding, default is used if null
	 * @return centered string
	 * @see String_To_Centered interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length, Character paddingChar){
		return String_To_Centered.create(length, paddingChar, paddingChar, null, null).transform(s);
	}

	/**
	 * Returns centered string of given length using the same padding character on both sides.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @param paddingChar the padding character for left and right padding, default is used if null
	 * @param builder an optional builder to append the padded string to, used if set, ignored if null
	 * @return centered string
	 * @see String_To_Centered interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length, Character paddingChar, StrBuilder builder){
		return String_To_Centered.create(length, paddingChar, paddingChar, null, builder).transform(s);
	}

	/**
	 * Returns centered string of given length with specific padding characters.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @param leftPaddingChar the left padding character, default is used if null
	 * @param rightPaddingChar the right padding character, default is used if null
	 * @return centered string
	 * @see String_To_Centered interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length, Character leftPaddingChar, Character rightPaddingChar){
		return String_To_Centered.create(length, leftPaddingChar, rightPaddingChar, null, null).transform(s);
	}

	/**
	 * Returns centered string of given length with specific padding characters.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @param leftPaddingChar the left padding character, default is used if null
	 * @param rightPaddingChar the right padding character, default is used if null
	 * @param innerWsChar inner white space replacement character, default is used if null
	 * @return centered string
	 * @see String_To_Centered interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length, Character leftPaddingChar, Character rightPaddingChar, Character innerWsChar){
		return String_To_Centered.create(length, leftPaddingChar, rightPaddingChar, innerWsChar, null).transform(s);
	}

	/**
	 * Returns centered string of given length with specific padding characters.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @param leftPaddingChar the left padding character, default is used if null
	 * @param rightPaddingChar the right padding character, default is used if null
	 * @param builder an optional builder to append the padded string to, used if set, ignored if null
	 * @return centered string
	 * @see String_To_Centered interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length, Character leftPaddingChar, Character rightPaddingChar, StrBuilder builder){
		return String_To_Centered.create(length, leftPaddingChar, rightPaddingChar, null, builder).transform(s);
	}

	/**
	 * Returns centered string of given length with specific padding characters.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @param leftPaddingChar the left padding character, default is used if null
	 * @param rightPaddingChar the right padding character, default is used if null
	 * @param innerWsChar inner white space replacement character, default is used if null
	 * @param builder an optional builder to append the padded string to, used if set, ignored if null
	 * @return centered string
	 * @see String_To_Centered interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length, Character leftPaddingChar, Character rightPaddingChar, Character innerWsChar, StrBuilder builder){
		return String_To_Centered.create(length, leftPaddingChar, rightPaddingChar, innerWsChar, builder).transform(s);
	}
}
