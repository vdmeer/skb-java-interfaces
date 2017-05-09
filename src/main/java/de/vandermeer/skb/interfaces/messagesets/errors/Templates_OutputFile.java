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
 * Standard Output File errors.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public enum Templates_OutputFile implements IsErrorTemplate {

	/**
	 * A required filename for an output file is missing.
	 * Arguments: application name, file type.
	 */
	NOFN(
			-310,
			"{}: no {} filename given",
			"A required filename for an output file is missing."
	),

	/**
	 * A required filename for an output file was `null`.
	 * Arguments: application name, file type.
	 */
	FN_NULL(
			-311,
			"{}: {} filename was `null`",
			"A required filename for an output file was `null`."
	),

	/**
	 * A required filename for an output file was blank, contained only whitespaces.
	 * Arguments: application name, file type.
	 */
	FN_BLANK(
			-312,
			"{}: {} filename was blank",
			"A required filename for an output file was blank, contained only whitespaces."
	),

	/**
	 * A given output filename has an extension, which is not expected by the application.
	 * Arguments: application name, file type, filename.
	 */
	FN_HAS_EXTENSION(
			-313,
			"{}: {} filename <{}> has a file extension",
			"A given output filename has an extension, which is not expected by the application."
	),

	/**
	 * A given output filename has no extension, which is expected by the application.
	 * Arguments: application name, file type, filename.
	 */
	FN_NO_EXTENSION(
			-314,
			"{}: {} filename <{}> has no file extension",
			"A given output filename has no extension, which is expected by the application."
	),

	/**
	 * A given output filename has the wrong extension, the application expected a different extension.
	 * Arguments: application name, file type, filename, expected extension(s).
	 */
	FN_WRONG_EXTENSION(
			-315,
			"{}: {} filename <{}> has wrong file extension, expected was {}",
			"A given output filename has the wrong extension, the application expected a different extension."
	),

	/**
	 * A given output filename is the same as the input filename.
	 * Arguments: application name, file type, filename, input file type, filename.
	 */
	FN_SAMEAS_INFN(
			-316,
			"{}: {} filename <{}> is the same as the {} filename <{}>",
			"A given output filename is the same as the input filename."
	),

	/**
	 * There was no success creating a valid URL for a given output filename (file system, resources), file does probably not exist.
	 * Arguments: application name, file type, filename.
	 */
	URL_NULL(
			-317,
			"{}: {} file with name <{}>: could not create valid URL from filesystem or resources, file does probably not exist",
			"There was no success creating a valid URL for a given output filename (file system, resources), file does probably not exist."
	),

	/**
	 * A required output file does already exist.
	 * Arguments: application name, file type, filename.
	 */
	FILE_EXIST(
			-318,
			"{}: {} file with name <{}> does already exist",
			"A required output file does already exist."
	),

	/**
	 * A required output file does already exist and application does not have an option permitting to overwrite it.
	 * Arguments: application name, file type, filename, overwrite option.
	 */
	FILE_EXIST_NOOVERWRITE(
			-319,
			"{}: {} file with name <{}> does already exist and no overwrite option given, try option <{}>",
			"A required output file does already exist and application does not have an option permitting to overwrite it."
	),

	/**
	 * A required output file does not exist.
	 * Arguments: application name, file type, filename.
	 */
	FILE_NOTEXIST(
			-320,
			"{}: {} file with name <{}> does not exist",
			"A required output file does not exist."
	),

//TODO 321
//	/**
//	 * A required output file does not exist and there is no option in the application to create one.
//	 * Arguments: application name, file type filename.
//	 */
//	FILE_NOTEXIST_NOCREATE(
//			-321,
//			"{}: {} file with name <{}> does not exist and no option to create one",
//			"A required output file does not exist and there is no option in the application to create one."
//	),

	/**
	 * The output filename points to an existing artifact, but it is not a file.
	 * Arguments: application name, file type, filename.
	 */
	FILE_NOTFILE(
			-322,
			"{}: {} file with name <{}> is not a file",
			"The output filename points to an existing artifact, but it is not a file."
	),

	/**
	 * The output filename points to an existing artifact, which is a directory.
	 * Arguments: application name, file type, filename.
	 */
	FILE_IS_DIRECTORY(
			-323,
			"{}: {} file with name <{}> is a directory",
			"The output filename points to an existing artifact, which is a directory."
	),

	/**
	 * The output filename points to a hidden file, the application did not expect that.
	 * Arguments: application name, file type, filename.
	 */
	FILE_IS_HIDDEN(
			-324,
			"{}: {} file with name <{}> is hidden",
			"The output filename points to a hidden file, the application did not expect that."
	),

	/**
	 * An output file could not be created.
	 * Arguments: application name, file type, filename.
	 */
	FILE_CANT_CREATE(
			-325,
			"{}: cannot create {} file <{}>, please check permissions",
			"An output file could not be created."
	),

	/**
	 * The given output file exists, is a file, but the application cannot read the file.
	 * Arguments: application name, file type, filename.
	 */
	FILE_CANT_READ(
			-326,
			"{}: cannot read {} file <{}>, please check permissions",
			"The given output file exists, is a file, but the application cannot read the file."
	),

	/**
	 * The given output file exists, is a file, but the application cannot write to file.
	 * Arguments: application name, file type, filename.
	 */
	FILE_CANT_WRITE(
			-327,
			"{}: cannot write {} file <{}>, please check permissions",
			"The given output file exists, is a file, but the application cannot write to file."
	),

	/**
	 * The given output file exists but the override option in the application is not set.
	 * Arguments: application name, file type, filename, override option.
	 */
	FILE_NOOVERRIDE(
			-328,
			"{}: cannot write {} file <{}>, no override option in application, use option <{}>",
			"The given output file exists but the override option in the application is not set."
	),

	/**
	 * The given output file exists, is a file, but the application cannot execute the file.
	 * Arguments: application name, file type, filename.
	 */
	FILE_CANT_EXECUTE(
			-329,
			"{}: cannot execute {} file <{}>, please check permissions",
			"The given output file exists, is a file, but the application cannot execute the file."
	),

	/**
	 * Writing to an output file resulted in an IO exception.
	 * Arguments: application name, file type, filename, exception message.
	 */
	IO_EXCEPTION_WRITING(
			-330,
			"{}: caught an IO exception writing to {} file <{}>, message: {}",
			"Writing to an output file resulted in an IO exception."
	),

	/**
	 * Writing to an output file resulted in an exception.
	 * Arguments: application name, file type, filename, exception message.
	 */
	EXCEPTION_WRITING(
			-331,
			"{}: caught an exception writing to {} file <{}>, message: {}",
			"Writing to an output file resulted in an exception."
	),

	/**
	 * A generated pattern for an output filename only contains a file extension. This can happen for instance when not using a basename.
	 * Arguments: application name, pattern.
	 */
	FN_PATTERN_ONLY_EXT(
			-332,
			"{}: output file pattern <{}> has only file extension, , check options for generating output file pattern",
			"A generated pattern for an output filename only contains a file extension. This can happen for instance when not using a basename."
	),

	/**
	 * A generated pattern for an output filename only contains directory and a file extension. This can happen for instance when not using a basename.
	 * Arguments: application name, pattern, cli options.
	 */
	FN_PATTERN_ONLY_DIREXT(
			-333,
			"{}: output file pattern <{}> has only file directory and extension, , check options for generating output file pattern",
			"A generated pattern for an output filename only contains directory and a file extension. This can happen for instance when not using a basename."
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
	Templates_OutputFile(final int code, final String message, final String description){
		//only for app name, all other validations in test code
		Validate.validState(message.contains("{}"));

		this.code = code;
		this.message = message;
		this.description = description;
	}

	@Override
	public IsErrorCategory getCategory() {
		return StandardErrorCategories.OUTPUT_FILE;
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
