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

/**
 * A standard typed application options.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.2
 */
public interface ApoBaseTyped<T> extends ApoBase {

	/**
	 * Returns the default value of the option.
	 * @return option default value, blank if not set
	 */
	T getDefaultValue();

	/**
	 * Returns the value of the option.
	 * In the base interface this is the default value.
	 * @return application value, null if none found
	 */
	default T getValue(){
		return this.getDefaultValue();
	}

	/**
	 * Tests if the option is set.
	 * A typed option is set if it has a default value.
	 * @return true if set, false otherwise
	 */
	@Override
	default boolean isSet(){
		return this.getValue()!=null;
	}

}
