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
 * A standard typed option that combines CLI, property, and environment settings.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.2
 */
public interface Apo_TypedCPE<T> extends Apo_TypedC<T>, Apo_TypedP<T>, Apo_TypedE<T> {

	@Override
	default ST getHelp(){
		ST st = Apo_TypedC.super.getHelp();

		st.add("envKey", this.getEnvironmentKey());
		st.add("envRequired", this.environmentIsRequired());

		st.add("propKey", this.getPropertyKey());
		st.add("propRequired", this.propertyIsRequired());

		return st;
	}

	/**
	 * Returns the value of the option.
	 * First, the CLI value is tested and if not null it is returned.
	 * Second, the property value is tested and if not it is returned.
	 * Third, the environment value is tested and if not it is returned.
	 * Last the default value is returned.
	 * @return application value, null if none found
	 */
	default T getValue(){
		if(this.getCliValue()!=null){
			return this.getCliValue();
		}
		if(this.getPropertyValue()!=null){
			return this.getPropertyValue();
		}
		if(this.getEnvironmentValue()!=null){
			return this.getEnvironmentValue();
		}
		return this.getDefaultValue();
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

	@Override
	default void validate() throws IllegalStateException {
		Apo_TypedC.super.validate();
		Apo_TypedP.super.validate();
		Apo_TypedE.super.validate();
	}
}
