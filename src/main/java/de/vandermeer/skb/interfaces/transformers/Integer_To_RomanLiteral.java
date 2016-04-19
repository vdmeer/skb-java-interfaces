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
import org.apache.commons.lang3.Validate;

/**
 * Takes an integer and returns a Roman number literal using upper case ASCII characters.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public interface Integer_To_RomanLiteral extends IsTransformer<Integer, String> {

	/** Array to convert numbers. */
	public final static int[] NUMBERS = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

	/** Array of Roman number literals. */
	public final static String[] LETTERS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

	@Override
	default String transform(Integer number){
		Validate.notNull(number);
		Validate.validState(-1<number && number<4001, "Roman literals are only supported between 0 and 4000, number was: " + number);

		String ret = "";
		for(int i=0; i<NUMBERS.length; i++){
			while(number>=NUMBERS[i]){
				ret += LETTERS[i];
				number -= NUMBERS[i];
			}
		}
		return ret;
	}

	/**
	 * Creates a transformer that takes an integer and returns a Roman number literal using upper case ASCII characters.
	 * @return new transformer
	 */
	static Integer_To_RomanLiteral create(){
		return new Integer_To_RomanLiteral() {};
	}

	/**
	 * Takes an integer and returns a Roman number literal using upper case ASCII characters.
	 * @param number input number
	 * @return Roman number literal using upper case ASCII characters
	 */
	static String convert(Integer number){
		return StringUtils.join(Integer_To_RomanLiteral.create().transform(number), "");
	}
}
