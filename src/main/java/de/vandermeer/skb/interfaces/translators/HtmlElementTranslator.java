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

package de.vandermeer.skb.interfaces.translators;

/**
 * Interface for an HTML Element translator.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface HtmlElementTranslator {

	/**
	 * Translates HTML elements into a temporary representation that is not picked up by any character translator.
	 * @param input string to translate
	 * @return translated string
	 */
	String text2tmp(String input);

	/**
	 * Translates a temporary representation of HTML elements to a target representation.
	 * @param input string to translate, with temporary HTML element representations
	 * @return translated string, with target representations of HTML elements
	 */
	String tmp2target(String input);

	/**
	 * Translates HTML elements found in the input to a target representation, without any temporary representation.
	 * @param input object to translate (toString is used)
	 * @return translated string, with target representation of HTML elements
	 */
	default String translateHtmlElements(Object input){
		if(input!=null){
			return this.translateHtmlElements(input.toString());
		}
		return null;
	}

	/**
	 * Translates HTML elements found in the input to a target representation, without any temporary representation.
	 * @param input string to translate
	 * @return translated string, with target representation of HTML elements
	 */
	String translateHtmlElements(String input);
}
