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
 * Converts a string to a justified string of given length.
 * If the given string length is less than the required length, it will be stretched by inserting white spaces.
 * The returned string will be of the required length, blank strings will have only blanks characters.
 * 
 * The method can be further customized by
 * - Setting an inner white space character - replace all white spaces in the text with that character
 * 
 * The default length is {@link #DEFAULT_LENGTH}.
 * The default inner white space character is {@link #DEFAULT_INNER_WHITESPACE_CHARACTER}.
 * 
 * The actual return object is of type {@link StrBuilder} to minimize the creation of lots of strings for more complex transformations.
 * An implementation can chose to provide a builder to append the padded string to as well.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public interface String_To_Justified extends IsTransformer<String, StrBuilder> {

	/** The default width, set to 80. */
	static int DEFAULT_LENGTH = 80;

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
	 * Returns a builder to append the justified string to, rather than creating a new builder.
	 * @return builder, can be null, if not null the justified string will be appended to it
	 */
	default StrBuilder getBuilderForAppend(){
		return null;
	}

	@Override
	default StrBuilder transform(String s) {
		IsTransformer.super.transform(s);
		StrBuilder ret = (this.getBuilderForAppend()==null)?new StrBuilder(this.getLength()):this.getBuilderForAppend();

		// set string and replace all inner ws with required character
		String[] ar = StringUtils.split((s==null)?"":s);
		int length = 0;
		for(String str : ar){
			length += str.length();
		}

		//first spaces to distributed (even)
		//do that until all firsts have been consumed
		int l = ((ar.length-1)==0)?1:(ar.length-1); // null safe dividend
		int first = ((this.getLength() - length) / l) * (ar.length-1);
		while(first>0){
			for(int i=0; i<ar.length-1; i++){
				if(first!=0){
					ar[i] += this.getInnerWsChar();
					first--;
				}
			}
		}

		//second space to distributed (leftovers, as many as there are)
		//do seconds from the back to front, until all seconds have been consumed
		//reverse means do not append to the last array element!
		int second = (this.getLength() - length) % l;
		while(second>0){
			for(int i=ar.length-2; i>0; i--){
				if(second!=0){
					ar[i] += this.getInnerWsChar();
					second--;
				}
			}
		}
		ret.append(StringUtils.join(ar));
		while(ret.length()<this.getLength()){
			ret.append(' ');
		}
		return ret;
	}

	/**
	 * Creates a transformer that converts a string to a justified string of given length.
	 * @param length the required length (must be &gt;0)
	 * @param innerWsChar inner white space replacement character
	 * @param builder an optional builder to append the padded string to, used if set, ignored if null
	 * @return new transformer
	 * @see String_To_Justified interface description for how the converter works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static String_To_Justified create(int length, Character innerWsChar, StrBuilder builder){
		Validate.validState(length>0, "cannot work with lenght of less than 1");
		return new String_To_Justified() {
			@Override
			public int getLength(){
				return (length<1)?String_To_Justified.super.getLength():length;
			}

			@Override
			public StrBuilder getBuilderForAppend(){
				return builder;
			}

			@Override
			public Character getInnerWsChar() {
				return (innerWsChar==null)?String_To_Justified.super.getInnerWsChar():innerWsChar;
			}
		};
	}

	/**
	 * Returns justified string of given length.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @return justified string
	 * @see String_To_Justified interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length){
		return String_To_Justified.create(length, null, null).transform(s);
	}

	/**
	 * Returns justified string of given length.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @param innerWsChar inner white space replacement character, default is used if null
	 * @return justified string
	 * @see String_To_Justified interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length, Character innerWsChar){
		return String_To_Justified.create(length, innerWsChar, null).transform(s);
	}

	/**
	 * Returns justified string of given length.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @param builder an optional builder to append the padded string to, used if set, ignored if null
	 * @return justified string
	 * @see String_To_Justified interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length, StrBuilder builder){
		return String_To_Justified.create(length, null, builder).transform(s);
	}

	/**
	 * Returns justified string of given length.
	 * @param s input string
	 * @param length the required length (must be &gt;0)
	 * @param innerWsChar inner white space replacement character, default is used if null
	 * @param builder an optional builder to append the padded string to, used if set, ignored if null
	 * @return justified string
	 * @see String_To_Justified interface description for how the conversion works
	 * @throws NullPointerException if an argument was unexpectedly null
	 * @throws IllegalArgumentException if an argument was illegal
	 */
	static StrBuilder convert(String s, int length, Character innerWsChar, StrBuilder builder){
		return String_To_Justified.create(length, innerWsChar, builder).transform(s);
	}
}
