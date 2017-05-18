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
 * Standard Application Option errors.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public enum Templates_STG implements IsErrorTemplate {

	/**
	 * A required String Template Group was null.
	 * Arguments: none.
	 */
	STG_NULL(
			-400,
			"a required STG was null",
			"A required String Template Group was null."
	),

	/**
	 * An STGroup did run into a compile time error.
	 * Arguments: error message.
	 */
	ST_COMPILE_TIME(
			-405,
			"STGroup compile time error: {}",
			"An STGroup did run into a compile time error."
	),

	/**
	 * An STGroup did run into an internal error.
	 * Arguments: error message.
	 */
	ST_INTERNAL(
			-406,
			"STGroup internal error: {}",
			"An STGroup did run into an internal error."
	),

	/**
	 * An STGroup did run into an IO error.
	 * Arguments: error message.
	 */
	ST_IO(
			-407,
			"STGroup IO error: {}",
			"An STGroup did run into an IO error."
	),

	/**
	 * An STGroup did run into a runtime error.
	 * Arguments: error message.
	 */
	ST_RUNTIME(
			-408,
			"STGroup runtime error: {}",
			"An STGroup did run into a runtime error."
	),

	/**
	 * A loaded STGroup tried to load template ST but resulting ST was null.
	 * Arguments: STG name, template name.
	 */
	ST_IS_NULL(
			-409,
			"STGroup <{}> tried to load template <{}> but as null",
			"A loaded STGroup tried to load template ST but resulting ST was null."
	),

	/**
	 * A loaded STGroup does not define a template ST that was expected to be defined.
	 * Arguments: STG name, template name.
	 */
	MISSING_EXPECTED_ST(
			-410,
			"STGroup <{}> does not define expected template <{}>",
			"A loaded STGroup does not define a template ST that was expected to be defined."
	),

	/**
	 * A loaded STGroup does define an unexpected template ST.
	 * Arguments: STG name, template name.
	 */
	EXTRA_ST(
			-411,
			"STGroup <{}> does define unexpected template <{}>",
			"A loaded STGroup does define an unexpected template ST."
	),

	/**
	 * A loaded STGroup found errors with a defined template ST, detailed errors should follow.
	 * Arguments: STG name, template name.
	 */
	MISSING_ST_ARG_ERRORS(
			-412,
			"STGroup <{}> found problems with template <{}>",
			"A loaded STGroup found errors with a defined template ST, detailed errors should follow."
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
	Templates_STG(final int code, final String message, final String description){
		//only for app name, all other validations in test code
		//Validate.validState(message.contains("{}"));

		this.code = code;
		this.message = message;
		this.description = description;
	}

	@Override
	public IsErrorCategory getCategory() {
		return StandardErrorCategories.STG;
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
