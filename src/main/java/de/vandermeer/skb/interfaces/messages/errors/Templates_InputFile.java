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

import org.apache.commons.lang3.Validate;

/**
 * Standard Input File errors.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public enum Templates_InputFile implements IsErrorTemplate {

	/**
	 * A required filename for an input file is missing.
	 * Arguments: file type.
	 */
	NOFN(
			-210,
			"no {} filename given",
			"A required filename for an input file is missing."
	),

	/**
	 * A required filename for an input file was `null`.
	 * Arguments: file type.
	 */
	FN_NULL(
			-211,
			"{} filename was `null`",
			"A required filename for an input file was `null`."
	),

	/**
	 * A required filename for an input file was blank, contained only whitespaces.
	 * Arguments: file type.
	 */
	FN_BLANK(
			-212,
			"{} filename was blank",
			"A required filename for an input file was blank, contained only whitespaces."
	),

	/**
	 * A given input filename has an extension, which is not expected by the application.
	 * Arguments: file type, filename.
	 */
	FN_HAS_EXTENSION(
			-213,
			"{} filename <{}> has a file extension",
			"A given input filename has an extension, which is not expected by the application."
	),

	/**
	 * A given input filename has no extension, which is expected by the application.
	 * Arguments: file type, filename.
	 */
	FN_NO_EXTENSION(
			-214,
			"{} filename <{}> has no file extension",
			"A given input filename has no extension, which is expected by the application."
	),

	/**
	 * A given input filename has the wrong extension, the application expected a different extension.
	 * Arguments: file type, filename, expected extension(s).
	 */
	FN_WRONG_EXTENSION(
			-215,
			"{} filename <{}> has wrong file extension, expected was {}",
			"A given input filename has the wrong extension, the application expected a different extension."
	),

	/**
	 * There was no success creating a valid URL for a given output filename (file system, resources), file does probably not exist.
	 * Arguments: file type, filename.
	 */
	URL_NULL(
			-216,
			"{} file with name <{}>: could not create valid URL from filesystem or resources, file does probably not exist",
			"There was no success creating a valid URL for a given output filename (file system, resources), file does probably not exist."
	),

	/**
	 * A required input file was not found, the message is probably a file not found exception.
	 * Arguments: file type, filename, error message.
	 */
	FILE_NOT_FOUND(
			-217,
			"{} file with name <{}> was not found: {}",
			"A required input file was not found, the message is probably a file not found exception."
	),

	/**
	 * A required input file does not exist.
	 * Arguments: file type, filename.
	 */
	FILE_NOTEXIST(
			-218,
			"{} file with name <{}> does not exist",
			"A required input file does not exist."
	),

	/**
	 * A required input file does not exist and there is no option in the application to create one.
	 * Arguments: file type, filename.
	 */
	FILE_NOTEXIST_NOCREATE(
			-219,
			"{} file with name <{}> does not exist and no option to create one",
			"A required input file does not exist and there is no option in the application to create one."
	),

	/**
	 * The input filename points to an existing artifact, but it is not a file.
	 * Arguments: file type, filename.
	 */
	FILE_NOTFILE(
			-220,
			"{} file with name <{}> is not a file",
			"The input filename points to an existing artifact, but it is not a file."
	),

	/**
	 * The input filename points to an existing artifact, which is a directory.
	 * Arguments: file type, filename.
	 */
	FILE_IS_DIRECTORY(
			-221,
			"{} file with name <{}> is a directory",
			"The input filename points to an existing artifact, which is a directory."
	),

	/**
	 * The input filename points to a hidden file, the application did not expect that.
	 * Arguments: file type, filename.
	 */
	FILE_IS_HIDDEN(
			-222,
			"{} file with name <{}> is hidden",
			"The input filename points to a hidden file, the application did not expect that."
	),

	/**
	 * The given input file exists, is a file, but the application cannot read the file.
	 * Arguments: file type, filename.
	 */
	FILE_CANT_READ(
			-223,
			"cannot read {} file <{}>, please check permissions",
			"The given input file exists, is a file, but the application cannot read the file."
	),

	/**
	 * The given input file exists, is a file, but the application cannot write to file.
	 * Arguments: file type, filename.
	 */
	FILE_CANT_WRITE(
			-224,
			"cannot write {} file <{}>, please check permissions",
			"The given input file exists, is a file, but the application cannot write to file."
	),

	/**
	 * The given input file exists, is a file, but the application cannot execute the file.
	 * Arguments: file type, filename.
	 */
	FILE_CANT_EXECUTE(
			-225,
			"cannot execute {} file <{}>, please check permissions",
			"The given input file exists, is a file, but the application cannot execute the file."
	),

	/**
	 * The given input file exists, is a file, but is compressed and the application did not expect that.
	 * Arguments: file type, filename.
	 */
	FILE_IS_COMPRESSED(
			-226,
			"{} file <{}> is compressed",
			"The given input file exists, is a file, but is compressed and the application did not expect that."
	),

	/**
	 * The given input file exists, is a file, but is not compressed and the application did not expect that.
	 * Arguments: file type, filename.
	 */
	FILE_NOT_COMPRESSED(
			-227,
			"{} file <{}> is not compressed",
			"The given input file exists, is a file, but is not compressed and the application did not expect that."
	),

	/**
	 * Reading from an input file resulted in an IO exception.
	 * Arguments: file type, filename, exception message.
	 */
	IO_EXCEPTION_READING(
			-228,
			"caught an IO exception reading from {} file <{}>, message: {}",
			"Reading from an input file resulted in an IO exception."
	),

	/**
	 * Reading from an input file resulted in a ZIP exception.
	 * Arguments: file type, filename, exception message.
	 */
	ZIP_EXCEPTION_READING(
			-229,
			"caught a ZIP exception reading from {} file <{}>, message: {}",
			"Reading from an input file resulted in a ZIP exception."
	),

	/**
	 * Reading from an input file resulted in an exception.
	 * Arguments: file type, filename, exception message.
	 */
	EXCEPTION_READING(
			-230,
			"caught an exception reading from {} file <{}>, message: {}",
			"Reading from an input file resulted in an exception."
	),

	/**
	 * Loading an input file into an application resulted in an exception.
	 * Arguments: file type, filename, exception message.
	 */
	EXCEPTION_LOADING(
			-231,
			"caught an exception loading {} file <{}>, message: {}",
			"Loading an input file into an application resulted in an exception."
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
	Templates_InputFile(final int code, final String message, final String description){
		//only for app name, all other validations in test code
		Validate.validState(message.contains("{}"));

		this.code = code;
		this.message = message;
		this.description = description;
	}

	@Override
	public IsErrorCategory getCategory() {
		return StandardErrorCategories.INPUT_FILE;
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
