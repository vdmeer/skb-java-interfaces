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

import de.vandermeer.skb.interfaces.messages.errors.IsError;

/**
 * A typed command: name plus one typed value.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface Sh_TypedCmd<T> extends Sh_CmdBase {

	/**
	 * Flag for required arguments.
	 * @return true if required, false otherwise
	 */
	boolean argIsRequired();

	/**
	 * Returns a description of the argument.
	 * @return argument description, must not be blank
	 */
	String getArgumentDecription();

	/**
	 * Returns a name or display name for the argument.
	 * @return argument name for display, must not be blank
	 */
	String getArgumentName();

	/**
	 * Returns the command value.
	 * @return command value, null if not set
	 */
	T getCmdValue();

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
		if(this.getCmdValue()!=null){
			return this.getCmdValue();
		}
		return this.getDefaultValue();
	}

	/**
	 * Tests if the argument was present in a parsed command, a value was set.
	 * @return true if it was present, false otherwise
	 */
	default boolean inCmd(){
		return this.getCmdValue()!=null;
	}

	/**
	 * Tests if the option is set.
	 * A typed option is set if it has a default value.
	 * @return true if set, false otherwise
	 */
	default boolean isSet(){
		return this.getValue()!=null;
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
