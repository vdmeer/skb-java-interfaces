/* Copyright 2017 Sven van der Meer <vdmeer.sven@mykolab.com>
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

package de.vandermeer.skb.interfaces.messagesets.errors;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.categories.has.HasDescription;

/**
 * A category for an application error code.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface IsErrorCategory extends HasDescription, CategoryIs {

	/**
	 * Returns the category display name.
	 * @return display name
	 */
	String getDisplayName();

	/**
	 * Returns the start of the error codes.
	 * @return error code start
	 */
	int getStart();

	/**
	 * Returns the end of the error codes.
	 * @return error code end
	 */
	int getEnd();

	/**
	 * Validates an array of categories for not overlapping in star/end.
	 * @param categories the categories to validate, must not be null or have null elements
	 * @throws NullPointerException if parameter was null
	 * @throws IllegalArgumentException if parameter had null elements
	 * @throws IllegalStateException if a category did overlap with another one (first overlap found triggers exception)
	 */
	static void validate(IsErrorCategory[] categories){
		Validate.noNullElements(categories, "The array contain null at position %d");
		Map<Integer, String> inuse = new HashMap<>();
		for(IsErrorCategory cat : categories){
			for (int i=cat.getStart(); i>=cat.getEnd(); i--){
				Validate.isTrue(!inuse.containsKey(i), i + " == " + inuse.get(i) + " ==> " + cat.getDisplayName());
				inuse.put(i, cat.getDisplayName());
			}
		}
	}
}
