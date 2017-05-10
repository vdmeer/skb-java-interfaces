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

package de.vandermeer.skb.interfaces.messages.errors;

/**
 * Standard CLI General errors.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public enum Templates_CliGeneral implements IsErrorTemplate {

	/**
	 * A general parse exception was caught, most likely a missing required CLI option or missing mandatory arguments.
	 * Arguments: error message.
	 */
	PARSE_EXCEPTION(
			-140,
			"general error parsing command line: {}",
			"A general parse exception was caught, most likely a missing required CLI option or missing mandatory arguments."
	),

	/**
	 * CLI parser has detected an option already selected, more than one option in an option group has been provided.
	 * Arguments: error message.
	 */
	ALREADY_SELECTED(
			-141,
			"CLI error already selected: {}",
			"CLI parser has detected an option already selected, more than one option in an option group has been provided."
	),

	/**
	 * CLI parser is missing an argument, an option requiring an argument is not provided with an argument.
	 * Arguments: error message.
	 */
	MISSING_ARGUMENT(
			-142,
			"CLI error missing argument: {}",
			"CLI parser is missing an argument, an option requiring an argument is not provided with an argument."
	),

	/**
	 * CLI parser is missing an option, a required option has not been provided.
	 * Arguments: error message.
	 */
	MISSING_OPTION(
			-143,
			"CLI error missing option: {}",
			"CLI parser is missing an option, a required option has not been provided."
	),

	/**
	 * CLI parser detected an unrecognized option, an unrecognized option was seen.
	 * Arguments: error message.
	 */
	UNRECOGNIZED_OPTION(
			-144,
			"CLI error unrecognized option: {}",
			"CLI parser detected an unrecognized option, an unrecognized option was seen."
	),

	;

	/** The numeric error code. */
	private final int code;

	/** The error message using `{}` for argument substitution. */
	private final String message;

	/** A description for the error code. */
	private final String description;

	/**
	 * Creates a new error.
	 * @param code the error code, must be smaller than 0, in the range of the used category, and unique in the enumerate
	 * @param message the message, must not be blank and contain at least one `{}` for the application name
	 * @param description a description for the error code, must not be blank
	 * @param category the error code category, will be used for testing code, must not be null
	 */
	Templates_CliGeneral(final int code, final String message, final String description){
		//only for app name, all other validations in test code
		//Validate.validState(message.contains("{}"));

		this.code = code;
		this.message = message;
		this.description = description;
	}

	@Override
	public IsErrorCategory getCategory() {
		return StandardErrorCategories.CLI_GENERAL;
	}

	@Override
	public int getCode() {
		return this.code;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getDisplayName() {
		return this.name();
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
