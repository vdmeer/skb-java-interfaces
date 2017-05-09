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

package de.vandermeer.skb.interfaces.render;

import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.categories.CategoryHas;

/**
 * Interface for objects that do provide a text representation.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface HasText extends CategoryHas {

	/**
	 * Creates a new simple text object.
	 * @param text input text, cannot be blank
	 * @return new simple text object
	 * @throws NullPointerException if `text` was null
	 * @throws IllegalArgumentException if `text` was blank
	 */
	static HasText create(final String text){
		Validate.notBlank(text);
		return new HasText() {
			@Override
			public String getText() {
				return text;
			}
		};
	}

	/**
	 * Returns text representation of an object.
	 * @return text, should not be blank
	 */
	String getText();
}
