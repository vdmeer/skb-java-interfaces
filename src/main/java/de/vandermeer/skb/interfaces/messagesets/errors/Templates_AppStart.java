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

package de.vandermeer.skb.interfaces.messagesets.errors;

import org.apache.commons.lang3.Validate;

/**
 * Standard APP Start errors.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public enum Templates_AppStart implements IsErrorTemplate {

	/**
	 * The early exit option help with an argument did not find the application option to print help for.
	 * Arguments: application name, unknown option.
	 */
	HELP_UKNOWN_OPTION(
			-100,
			"{}: unknown option <{}> for help",
			"The early exit option help with an argument did not find the application option to print help for."
	),

	/**
	 * An application did expect to be executed from an executable jar, but was not.
	 * Argument: application name.
	 */
	MUST_BE_IN_EXEC_JAR(
			-101,
			"{}: application expected to be run from an executable jar",
			"An application did expect to be executed from an executable jar, but was not."
	),

	/**
	 * An application did expect to read a JAR path but was not able to.
	 * Argument: application name, error message.
	 */
	NO_JAR_PATH(
			-102,
			"{}: could not get a JAR path to search for implementations: {}",
			"An application did expect to read a JAR path but was not able to."
	),

	/**
	 * An application expected to be executed on a particular operating system, but was not.
	 * Argument: application name, supported OS.
	 */
	OS_NOT_SUPPORTED(
			-103,
			"{}: operating system not supported, should be: {}",
			"An application expected to be executed on a particular operating system, but was not."
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
	Templates_AppStart(final int code, final String message, final String description){
		//only for app name, all other validations in test code
		Validate.validState(message.contains("{}"));

		this.code = code;
		this.message = message;
		this.description = description;
	}

	@Override
	public IsErrorCategory getCategory() {
		return StandardErrorCategories.APP_START;
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
