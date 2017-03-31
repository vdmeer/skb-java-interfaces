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

import de.vandermeer.skb.interfaces.transformers.IsTransformer;

/**
 * Converts a string to a string without any excessive whitespace characters.
 * Excessive white spaces are consecutive spaces, tabulators, and line breaks.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public interface String_To_NoWs extends IsTransformer<String, String> {

	/**
	 * Transforms a String to a String removing all excessive whitespace characters.
	 * @param s input string
	 * @return String with excessive white spaces removed, `null` if input was null
	 */
	@Override
	default String transform(String s) {
		IsTransformer.super.transform(s);
		s.replaceAll("\\s+", " ");
		return s;
	}

	/**
	 * Creates a transformer that takes a String and returns a String without excessive whitespace characters.
	 * @return new transformer
	 */
	static String_To_NoWs create(){
		return new String_To_NoWs() {};
	}

	/**
	 * Returns a string with all excessive white spaces removed.
	 * @param s input string
	 * @return `null` if `s` was null, string with no excessive white spaces otherwise
	 */
	static String convert(String s){
		return String_To_NoWs.create().transform(s);
	}
}
