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

/**
 * Converts a String to a Boolean.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public interface String_To_Boolean extends IsTransformer<String, Boolean> {

	/**
	 * Transforms a String to a Boolean.
	 * Returns true if the string is `true` or `on` and false if the string is `false` or `off` (all string are tested ignoring case).
	 * @param s input string
	 * @return true if string was `true` or `on`, false if string was `false` or `off`, `null` otherwise
	 */
	@Override
	default Boolean transform(String s) {
		if(s!=null){
			if("true".equalsIgnoreCase(s) || "on".equalsIgnoreCase(s)){
				return new Boolean(true);
			}
			if("false".equalsIgnoreCase(s) || "off".equalsIgnoreCase(s)){
				return new Boolean(false);
			}
		}
		return null;
	}

	/**
	 * Creates a transformer that takes a String and returns a Boolean.
	 * @return new transformer
	 */
	static String_To_Boolean create(){
		return new String_To_Boolean() {};
	}

}
