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

import de.vandermeer.skb.interfaces.messages.errors.IsError;

/**
 * A standard typed CLI option.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.2
 */
public interface Apo_TypedC<T> extends ApoBaseTyped<T>, ApoBaseC {

	/**
	 * Returns the flag for an argument being optional or not.
	 * The default implementation is `false`.
	 * @return true if argument is optional, false if not.
	 */
	default boolean cliArgIsOptional(){
		return false;
	}

	/**
	 * Returns a description of the CLI argument.
	 * The CLI argument is the value that is expected with in the command line.
	 * For instance, if the option is `-h COMMAD` there is a argument `COMMAND` expected from the command line.
	 * This description then should explain what `COMMAD` is or what values can be used.
	 * @return CLI argument description, must not be blank
	 */
	String getCliArgumentDescription();

	/**
	 * Returns the name of the CLI argument.
	 * For instance, if the option is `-h COMMAD` there is a argument `COMMAND` expected from the command line.
	 * The name returned in this example should be `COMMAND`.
	 * @return CLI argument name, must not be blank
	 */
	String getCliArgumentName();

	/**
	 * Returns the CLI value of the option if any set.
	 * @return CLI value, null if none set
	 */
	T getCliValue();

	@Override
	default ST getHelp(){
		ST st = ApoBaseC.super.getHelp();
		ST cliST = (ST)st.getAttribute("cli");
		cliST.add("cliArgName", this.getCliArgumentName());
		cliST.add("cliArgOptional", this.cliArgIsOptional());
		cliST.add("cliArgDescr", this.getCliArgumentDescription());

		st.add("defaultValue", this.getDefaultValue());

		return st;
	}

	/**
	 * Returns the value of the option.
	 * First the CLI value is tested and if not null it is returned.
	 * Last the default value is returned.
	 * @return application value, null if none found
	 */
	default T getValue(){
		return (this.getCliValue()!=null)?this.getCliValue():this.getDefaultValue();
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

	/**
	 * Sets the CLI value of the option.
	 * @param value the value read from the command line, must not be null (or blank in case of a string)
	 * @return null on success, an error message on error
	 */
	IsError setCliValue(final Object value);

	@Override
	default void validate() throws IllegalStateException {
		ApoBaseC.super.validate();
		Validate.validState(!StringUtils.isBlank(this.getCliArgumentName()), "Apo: CLI argName cannot be blank");
		Validate.validState(!StringUtils.isBlank(this.getCliArgumentDescription()), "Apo: CLI argDescr cannot be blank");
	}

	/**
	 * Returns the type of the value of the command.
	 * @return type of value, must not be null
	 */
	String valueType();
}
