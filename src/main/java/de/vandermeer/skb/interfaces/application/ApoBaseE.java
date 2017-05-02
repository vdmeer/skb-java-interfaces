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

/**
 * Base for an environment options.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.2
 */
public interface ApoBaseE extends ApoBase {

	/**
	 * Returns the environment key of the option.
	 * @return environment key, must not be blank
	 */
	String getEnvironmentKey();

	@Override
	default ST getHelp(){
		ST st = ApoBase.super.getHelp();
		st.add("envKey", this.getEnvironmentKey());
		return st;
	}

	/**
	 * Tests if the option was present in the environment.
	 * @return true if it was present, false otherwise
	 */
	boolean inEnvironment();

	@Override
	default void validate() throws IllegalStateException {
		ApoBase.super.validate();
		Validate.validState(!StringUtils.isBlank(this.getEnvironmentKey()), "Apo: envKey must have a value");
	}
}
