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

import org.stringtemplate.v4.ST;

/**
 * A standard typed environment options.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.2
 */
public interface Apo_TypedE<T> extends ApoBaseE, ApoBaseTyped<T> {

	/**
	 * Returns the environment value of the option if any set.
	 * @return environment value, null if none set
	 */
	T getEnvironmentValue();

	@Override
	default ST getHelp(){
		return this.getHelpEnv();
	}

	/**
	 * Returns an ST with a help information for the option.
	 * @return ST with help information, must not be null
	 */
	default ST getHelpEnv(){
		ST st = ApoBaseE.super.getHelpEnv();
		st.add("defaultValue", this.getDefaultValue());
		return st;
	}

	/**
	 * Returns the value of the option.
	 * First the environment value is tested and if not null it is returned.
	 * Last the default value is returned.
	 * @return application value, null if none found
	 */
	default T getValue(){
		return (this.getEnvironmentValue()!=null)?this.getEnvironmentValue():this.getDefaultValue();
	}

	/**
	 * Tests if the option is set.
	 * A typed option is set if it has a value that is not `null`.
	 * @return true if set, false otherwise
	 */
	@Override
	default boolean isSet(){
		return this.getValue()!=null;
	}
}
