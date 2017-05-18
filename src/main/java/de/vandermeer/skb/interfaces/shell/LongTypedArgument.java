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

package de.vandermeer.skb.interfaces.shell;

import de.vandermeer.skb.interfaces.categories.has.HasDescription;
import de.vandermeer.skb.interfaces.categories.has.HasDisplayName;
import de.vandermeer.skb.interfaces.categories.has.HasName;
import de.vandermeer.skb.interfaces.messages.errors.IsError;

/**
 * An argument for a {@link LongTypedCmd}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface LongTypedArgument<T> extends HasName, HasDisplayName, HasDescription {

	/**
	 * Returns the command value.
	 * @return command value, null if not set
	 */
	T getCmdValue();

	/**
	 * Returns the value of the option.
	 * In the base interface this is the default value.
	 * @return application value, null if none found
	 */
	default T getValue(){
		return this.getCmdValue();
	}

	/**
	 * Resets the command value to null, for instance for a new command parse process.
	 */
	void resetCmdValue();

	/**
	 * Sets the command value of the argument.
	 * @param value the value read from the command, null will be set
	 * @return null on success, an error message on error
	 */
	IsError setCmdValue(final String value);

	/**
	 * Returns the type of the value of the command.
	 * @return type of value, must not be null
	 */
	String valueType();

}
