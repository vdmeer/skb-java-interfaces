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

package de.vandermeer.skb.interfaces.categories.has;

import org.apache.commons.lang3.text.StrBuilder;

/**
 * Interface for objects that have special log methods.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public interface HasToLogContainment extends HasToLog {

	/**
	 * Returns the container (containment) class for logging.
	 * @return container class
	 */
	Class<?> getContainerClass();

	/**
	 * Returns a builder using containment class, class and value.
	 * @param contained contained object class
	 * @param values values, printed comma separated
	 * @return a StrBuilder combining the inputs
	 */
	default StrBuilder toLog(Class<?> contained, Object ... values){
		StrBuilder ret = new StrBuilder(50)
				.append(this.getContainerClass().getSimpleName())
				.append('(')
				.append(contained.getSimpleName())
				.append(')')
				.append(": ")
				.appendWithSeparators(values, ", ");
			;
		return ret;
	}
}
