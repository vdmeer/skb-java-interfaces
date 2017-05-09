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
 * Standard Input Directory errors.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public enum Templates_InputDirectory implements IsErrorTemplate {

	/**
	 * A required directory name for an input directory is missing.
	 * Arguments: application name, directory type.
	 */
	NODN(
			-270,
			"{}: no {} directory name given",
			"A required directory name for an input directory is missing."
	),

	/**
	 * A required directory name for an input directory was `null`.
	 * Arguments: application name, directory type.
	 */
	DN_NULL(
			-271,
			"{}: {} directory name was `null`",
			"A required directory name for an input directory was `null`."
	),

	/**
	 * A required directory name for an input directory was blank, contained only whitespaces.
	 * Arguments: application name, directory type.
	 */
	DN_BLANK(
			-272,
			"{}: {} directory name was blank",
			"A required directory name for an input directory was blank, contained only whitespaces."
	),

	/**
	 * A given input directory name has an extension, which is not expected by the application.
	 * Arguments: application name, directory type. directory name.
	 */
	DN_HAS_EXTENSION(
			-273,
			"{}: {} directory name <{}> has a file extension",
			"A given input directory name has an extension, which is not expected by the application."
	),

	/**
	 * There was no success creating a valid URL for a given output directory name (file system, resources), directory does probably not exist.
	 * Arguments: application name, directory type, directory name.
	 */
	URL_NULL(
			-274,
			"{}: {} directory with name <{}>: could not create valid URL from filesystem or resources, directory does probably not exist",
			"There was no success creating a valid URL for a given output directory name (file system, resources), directory does probably not exist."
	),

	/**
	 * A required input directory does not exist.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_NOTEXIST(
			-275,
			"{}: {} directory with name <{}> does not exist",
			"A required input directory does not exist."
	),

	/**
	 * A required input directory does not exist and there is no option in the application to create one.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_NOTEXIST_NOCREATE(
			-276,
			"{}: {} directory with name <{}> does not exist and no option to create one",
			"A required input directory does not exist and there is no option in the application to create one."
	),

	/**
	 * The input directory name points to an existing artifact, but it is not a directory.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_NOTDIRECTORY(
			-277,
			"{}: {} directory with name <{}> is not a directory",
			"The input directory name points to an existing artifact, but it is not a directory."
	),

	/**
	 * The input directory name points to an existing artifact, which is a file.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_IS_FILE(
			-278,
			"{}: {} directory with name <{}> is a file",
			"The input directory name points to an existing artifact, which is a file."
	),

	/**
	 * The input directory name points to a hidden directory, the application did not expect that.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_IS_HIDDEN(
			-279,
			"{}: {} directory with name <{}> is hidden",
			"The input directory name points to a hidden directory, the application did not expect that."
	),

	/**
	 * The given input directory exists, is a directory, but the application cannot read the directory.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_CANT_READ(
			-280,
			"{}: cannot read {} directory <{}>, please check permissions",
			"The given input directory exists, is a directory, but the application cannot read the directory."
	),

	/**
	 * The given input directory exists, is a directory, but the application cannot write to directory.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_CANT_WRITE(
			-281,
			"{}: cannot write {} directory <{}>, please check permissions",
			"The given input directory exists, is a directory, but the application cannot write to directory."
	),

	/**
	 * The given input directory exists, is a directory, but the application cannot execute the directory.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_CANT_EXECUTE(
			-282,
			"{}: cannot execute {} directory <{}>, please check permissions",
			"The given input directory exists, is a directory, but the application cannot execute the directory."
	),

	/**
	 * Reading from an input directory resulted in an IO exception.
	 * Arguments: application name, directory type, directory name, exception message.
	 */
	IO_EXCEPTION_READING(
			-283,
			"{}: caught an IO exception reading from {} directory <{}>, message: {}",
			"Reading from an input directory resulted in an IO exception."
	),

	/**
	 * Reading from an input directory resulted in an exception.
	 * Arguments: application name, directory type, directory name, exception message.
	 */
	EXCEPTION_READING(
			-284,
			"{}: caught an exception reading from {} directory <{}>, message: {}",
			"Reading from an input directory resulted in an exception."
	),

	;

	/** The numeric error code. */
	private final int code;

	/** The error message using `{}` for argument substitution. */
	private final String message;

	/** A description for the error code. */
	private final String description;

	@Override
	public String getDescription() {
		return this.description;
	}

	/**
	 * Creates a new error.
	 * @param code the error code, must be smaller than 0, in the range of the used category, and unique in the enumerate
	 * @param message the message, must not be blank and contain at least one `{}` for the application name
	 * @param description a description for the error code, must not be blank
	 * @param category the error code category, will be used for testing code, must not be null
	 */
	Templates_InputDirectory(final int code, final String message, final String description){
		//only for app name, all other validations in test code
		Validate.validState(message.contains("{}"));

		this.code = code;
		this.message = message;
		this.description = description;
	}

	@Override
	public IsErrorCategory getCategory() {
		return StandardErrorCategories.INPUT_DIRECTORY;
	}

	@Override
	public int getCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public String getDisplayName() {
		return this.name();
	}

}
