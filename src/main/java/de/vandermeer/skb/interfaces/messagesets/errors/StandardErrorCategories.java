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
 * Collection of standard error categories.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public enum StandardErrorCategories implements IsErrorCategory {

	/**
	 * Category for unexpected runtime exceptions, which usually indicate a bug in the application.
	 */
	RUNTIME_EXCEPTIONS_UNEXPECTED(
			-1, -29, "Runtime Exceptions (unexpected)",
			"Category for unexpected runtime exceptions, which usually indicate a bug in the application."
	),

	/**
	 * Category for (somehow) expected runtime exceptions, for instance when validating parameters.
	 */
	RUNTIME_EXCEPTIONS_EXPECTED(
			-30, -59, "Runtime Exceptions (expected)",
			"Category for (somehow) expected runtime exceptions, for instance when validating parameters."
	),

	/**
	 * Category for miscellaneous exceptions.
	 */
	MISC_EXCEPTIONS(
			-60, -99, "Miscellaneous Exceptions",
			"Category for miscellaneous exceptions."
	),

	/**
	 * Category for errors that happen during the start of an application, for instance missing resources or problems with early exit options.
	 */
	APP_START(
			-100, -119, "Application Start",
			"Category for errors that happen during the start of an application, for instance missing resources or problems with early exit options."
	),

	/**
	 * Category for general errors with application options.
	 */
	APP_OPTIONS_GENERAL(
			-120, -139, "Application Options (general)",
			"Category for general errors with application options."
	),

	/**
	 * Category for general errors when dealing with the Command Line Interface (CLI).
	 */
	CLI_GENERAL(
			-140, -149, "CLI (general)",
			"Category for general errors when dealing with the Command Line Interface (CLI)."
	),

	/**
	 * Category for errors when setting values on options from the command line arguments.
	 */
	CLI_OPTIONS(
			-150, -159, "CLI (options)",
			"Category for errors when setting values on options from the command line arguments."
	),

	/**
	 * Category for general errors when dealing with environment settings.
	 */
	ENV_GENERAL(
			-160, -169, "Environment (general)",
			"Category for general errors when dealing with environment settings."
	),

	/**
	 * Category for errors when setting values on options from the environment.
	 */
	ENV_OPTIONS(
			-170, -179, "Environment (options)",
			"Category for errors when setting values on options from the environment."
	),

	/**
	 * Category for general errors when dealing with settings from properties.
	 */
	PROPERTIES_GENERAL(
			-180, -189, "Properties (general)",
			"Category for general errors when dealing with settings from properties."
	),

	/**
	 * Category for errors when setting values on options from properties.
	 */
	PROPERTIES_OPTIONS(
			-190, -199, "Properties (options)",
			"Category for errors when setting values on options from properties."
	),

	/**
	 * Category for general input errors.
	 */
	INPUT_GENERAL(
			-200, -209, "Input (general)",
			"Category for general input errors."
	),

	/**
	 * Category for errors when dealing with input files, e.g. file name not set or file does not exist.
	 */
	INPUT_FILE(
			-210, -239, "Input (file)",
			"Category for errors when dealing with input files, e.g. file name not set or file does not exist."
	),

	/**
	 * Category for errors when dealing with input resources, e.g. file does not exist in resources.
	 */
	INPUT_RESOURCE(
			-240, -269, "Input (resource)",
			"Category for errors when dealing with input resources, e.g. file does not exist in resources."
	),

	/**
	 * Category for errors when dealing with input directories, e.g. directory name not set or directory does not exist.
	 */
	INPUT_DIRECTORY(
			-270, -299, "Input (directory)",
			"Category for errors when dealing with input directories, e.g. directory name not set or directory does not exist."
	),

	/**
	 * Category for errors when dealing with output files, e.g. file name not set or file does not exist.
	 */
	OUTPUT_GENERAL(
			-300, -309, "Output (general)",
			"Category for errors when dealing with output files, e.g. file name not set or file does not exist."
	),

	/**
	 * Category for errors when dealing with output resources, e.g. file does not exist in resources.
	 */
	OUTPUT_FILE(
			-310, -339, "Output (file)",
			"Category for errors when dealing with output resources, e.g. file does not exist in resources."
	),

	/**
	 * Category for errors when dealing with output resources, e.g. file does not exist in resources.
	 */
	OUTPUT_RESOURCE(
			-340, -369, "Output (resource)",
			"Category for errors when dealing with output resources, e.g. file does not exist in resources."
	),

	/**
	 * Category for errors when dealing with output directories, e.g. directory name not set or directory does not exist.
	 */
	OUTPUT_DIRECTORY(
			-370, -399, "Output (directory)",
			"Category for errors when dealing with output directories, e.g. directory name not set or directory does not exist."
	),

	/**
	 * Category for errors when dealing with String Template groups, group could not be initialized or does not provide required template.
	 */
	STG(
			-400, -449, "String Template Group",
			"Category for errors when dealing with String Template groups, group could not be initialized or does not provide required template."
	),

	/**
	 * Category for errors when dealing with String Templates, e.g. template does not provide reqired argument.
	 */
	ST(
			-450, -499, "String Template",
			"Category for errors when dealing with String Templates, e.g. template does not provide reqired argument."
	),

	/**
	 * Category for errors when dealing with sources, for instance wrong source given.
	 */
	SOURCE(
			-500, -549, "Source",
			"Category for errors when dealing with sources, for instance wrong source given."
	),

	/**
	 * Category for errors when dealing with targets, for instance target not supported.
	 */
	TARGET(
			-550, -599, "Target",
			"Category for errors when dealing with targets, for instance target not supported."
	),

	/**
	 * Category for errors that occurred on a server or the server side of an application.
	 */
	SERVER(
			-600, -649, "Server (server side)",
			"Category for errors that occurred on a server or the server side of an application."
	),

	/**
	 * Category for errors that occurred on a client or the client side of an application.
	 */
	CLIENT(
			-650, -699, "Client (client side)",
			"Category for errors that occurred on a client or the client side of an application."
	),

	;

	/** Category display name. */
	private final String displayName;

	/** Start for error codes (minimum error code). */
	private final int start;

	/** End for error codes (maximum error code). */
	private final int end;

	/** Category description. */
	private final String description;

	/**
	 * Creates a new category.
	 * @param start category start
	 * @param end category end
	 * @param displayName category display name, must not be blank
	 * @param description category description, must not be blank
	 */
	StandardErrorCategories(final int start, final int end, final String displayName, final String description){
		Validate.validState(end<start);
		Validate.notBlank(displayName);
		Validate.notBlank(description);

		this.start = start;
		this.end = end;
		this.displayName = displayName;
		this.description = description;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getDisplayName() {
		return this.displayName;
	}

	@Override
	public int getEnd() {
		return this.end;
	}

	@Override
	public int getStart() {
		return this.start;
	}

}
