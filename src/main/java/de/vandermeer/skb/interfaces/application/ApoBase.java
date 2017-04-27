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

package de.vandermeer.skb.interfaces.application;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.stringtemplate.v4.ST;

import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.categories.has.HasDescription;

/**
 * Base for an application option.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.2
 */
public interface ApoBase extends CategoryIs, HasDescription {

	/**
	 * Returns help information for the option.
	 * The information contains all possible settings.
	 * This might include CLI, property, environment, and other settings.
	 * @return help information, must not be null
	 */
	ST getHelp();

	/**
	 * Returns a long description of the option.
	 * For more complex options, the description should include use case and other information.
	 * @return long description for the option, blank if not set
	 */
	String getLongDescription();

	/**
	 * Tests if the option is set.
	 * @return true if set, false otherwise
	 */
	boolean isSet();

	/**
	 * Sets the long description.
	 * @param description new description, ignored if null or blank
	 */
	void setLongDescription(ST description);

	/**
	 * Sets the long description.
	 * @param description new description, ignored if null or blank
	 */
	void setLongDescription(String description);

	/**
	 * Validates the option.
	 * @throws IllegalStateException for any validation error
	 */
	default void validate() throws IllegalStateException {
		Validate.validState(!StringUtils.isBlank(this.getDescription()), "Apo: description cannot be blank");
	}
}
