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
 * Standard Environment General errors.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public enum Templates_EnvironmentGeneral implements IsErrorTemplate {

	/**
	 * Reading environment settings caught a security exception, probably because a security manager doesn't allow access to the process environment.
	 * Arguments: exception message.
	 */
	SECURITY_EXCEPTION(
			-160,
			"security exception reading environment settings: <{}>",
			"Reading environment settings caught a security exception, probably because a security manager doesn't allow access to the process environment."
	),

	/**
	 * Environment parser has detected a key already selected, more than one key in a key group has been provided.
	 * Arguments: environment key.
	 */
	ALREADY_SELECTED(
			-161,
			"Environment error, key <{}> already selected",
			"Environment parser has detected a key already selected, more than one key in a key group has been provided."
	),

	/**
	 * Environment parser is missing an argument, a key requiring an argument is not provided with an argument.
	 * Arguments: environment key.
	 */
	MISSING_ARGUMENT(
			-162,
			"Environment error, missing argument for key <{}>",
			"Environment parser is missing an argument, a key requiring an argument is not provided with an argument."
	),

	/**
	 * Environment parser is missing a key, a required key has not been provided.
	 * Arguments: environment key.
	 */
	MISSING_KEY(
			-163,
			"Environment error, missing key <{}>",
			"Environment parser is missing a key, a required key has not been provided."
	),

	/**
	 * The underlying system in which the application was executed does not support environment settings.
	 * Argument: none.
	 */
	SYSTEM_NO_ENV(
			-164,
			"the underlying system does not support environment settings",
			"The underlying system in which the application was executed does not support environment settings."
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
	Templates_EnvironmentGeneral(final int code, final String message, final String description){
		//only for app name, all other validations in test code
		//Validate.validState(message.contains("{}"));

		this.code = code;
		this.message = message;
		this.description = description;
	}

	@Override
	public IsErrorCategory getCategory() {
		return StandardErrorCategories.ENV_GENERAL;
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
