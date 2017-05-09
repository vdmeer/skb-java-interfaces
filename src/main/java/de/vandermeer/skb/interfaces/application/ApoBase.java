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
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.categories.has.HasDescription;

/**
 * Base for an application option.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.2
 */
public interface ApoBase extends CategoryIs, HasDescription {

	/** The STG file for long help. */
	static final String STG_FILE = "de/vandermeer/skb/interfaces/application/long-help.stg";

	/**
	 * Returns the display name of the option.
	 * @return display name, must not be blank
	 */
	String getDisplayName();

	/**
	 * Returns help information for the option.
	 * The information contains all possible settings, except the long description.
	 * This might include CLI, property, environment, and other settings.
	 * @return help information, must not be null
	 */
	default ST getHelp(){
		STGroupFile stg = new STGroupFile(STG_FILE);
		ST st = stg.getInstanceOf("longHelp");

		st.add("displayName", this.getDisplayName());
		st.add("shortDescr", this.getDescription());
		return st;
	}

	/**
	 * Returns a long description of the option.
	 * For more complex options, the description should include use case and other information.
	 * @return long description for the option, blank if not set
	 */
	Object getLongDescription();

	/**
	 * Tests if the option is set.
	 * @return true if set, false otherwise
	 */
	boolean isSet();

	/**
	 * Validates the option.
	 * @throws IllegalStateException for any validation error
	 */
	default void validate() throws IllegalStateException {
		Validate.validState(!StringUtils.isBlank(this.getDisplayName()), "Apo: displayName cannot be blank");
		Validate.validState(!StringUtils.isBlank(this.getDescription()), "Apo: description cannot be blank");
	}

}
