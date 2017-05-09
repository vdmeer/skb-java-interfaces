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
 * Standard Output Directory errors.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public enum Templates_OutputDirectory implements IsErrorTemplate {

	/**
	 * A required directory name for an output directory is missing.
	 * Arguments: application name, directory type.
	 */
	NODN(
			-370,
			"{}: no {} directory name given",
			"A required directory name for an output directory is missing."
	),

	/**
	 * A required directory name for an output directory was `null`.
	 * Arguments: application name, directory type.
	 */
	DN_NULL(
			-371,
			"{}: {} directory name was `null`",
			"A required directory name for an output directory was `null`."
	),

	/**
	 * A required directory name for an output directory was blank, contained only whitespaces.
	 * Arguments: application name, directory type.
	 */
	DN_BLANK(
			-372,
			"{}: {} directory name was blank",
			"A required directory name for an output directory was blank, contained only whitespaces."
	),

	/**
	 * A given output directory name has an extension, which is not expected by the application.
	 * Arguments: application name, directory type, directory name.
	 */
	DN_HAS_EXTENSION(
			-373,
			"{}: {} directory name <{}> has a file extension",
			"A given output directory name has an extension, which is not expected by the application."
	),

	/**
	 * A given output directory name is the same as the input directory name.
	 * Arguments: application name, directory type, input directory type, directory name.
	 */
	DN_SAMEAS_INDN(
			-374,
			"{}: {} directory name <{}> is the same as the {} directory name <{}>",
			"A given output directory name is the same as the input directory name."
	),

	/**
	 * There was no success creating a valid URL for a given output directory name (file system, resources), directory does probably not exist.
	 * Arguments: application name, directory type, directory name.
	 */
	URL_NULL(
			-375,
			"{}: {} directory with name <{}>: could not create valid URL from filesystem or resources, directory does probably not exist",
			"There was no success creating a valid URL for a given output directory name (file system, resources), directory does probably not exist."
	),

	/**
	 * A required output directory does already exist.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_EXIST(
			-376,
			"{}: {} directory with name <{}> does already exist",
			"A required output directory does already exist."
	),

	/**
	 * A required output directory does already exist and application does not have an option permitting to overwrite it.
	 * Arguments: application name, directory type, directory name, option activating overwrite.
	 */
	DIR_EXIST_NOOVERWRITE(
			-377,
			"{}: {} directory with name <{}> does already exist and no overwrite option given, try to use <{}>",
			"A required output directory does already exist and application does not have an option permitting to overwrite it."
	),

	/**
	 * A required output directory does not exist.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_NOTEXIST(
			-378,
			"{}: {} directory with name <{}> does not exist",
			"A required output directory does not exist."
	),

	/**
	 * The given output directory does not exist and the auto-create option in the application is not set.
	 * Arguments: application name, directory type, directory name, override option.
	 */
	DIR_NOTEXOIST_NOCREATE(
			-379,
			"{}: {} directory <{}> does not exist and application has no create option, use option <{}>",
			"The given output directory does not exist and the auto-create option in the application is not set."
	),


	/**
	 * The output directory name points to an existing artifact, but it is not a directory.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_NOTDIR(
			-380,
			"{}: {} directory with name <{}> is not a directory",
			"The output directory name points to an existing artifact, but it is not a directory."
	),

	/**
	 * The output directory name points to an existing artifact, which is a directory.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_IS_FILE(
			-381,
			"{}: {} directory with name <{}> is a file",
			"The output directory name points to an existing artifact, which is a file."
	),

	/**
	 * The output directory name points to a hidden directory, the application did not expect that.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_IS_HIDDEN(
			-382,
			"{}: {} directory with name <{}> is hidden",
			"The output directory name points to a hidden directory, the application did not expect that."
	),

	/**
	 * An application could not create an output directory.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_CANT_CREATE(
			-383,
			"{}: cannot create {} directory <{}>, please check permissions",
			"An application could not create an output directory."
	),

	/**
	 * The given output directory exists, is a directory, but the application cannot read the directory.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_CANT_READ(
			-384,
			"{}: cannot read {} directory <{}>, please check permissions",
			"The given output directory exists, is a directory, but the application cannot read the directory."
	),

	/**
	 * The given output directory exists, is a directory, but the application cannot write to directory.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_CANT_WRITE(
			-385,
			"{}: cannot write {} directory <{}>, please check permissions",
			"The given output directory exists, is a directory, but the application cannot write to directory."
	),

	/**
	 * The output directory may contain files used as output files, but the application override option is not used.
	 * Arguments: application name, directory type, directory name, pattern for files, override option.
	 */
	DIR_MAYCONTAIN_OUTFILES(
			-386,
			"{}: {} directory <{}> may contains potential output files ({}) and no override option given, use <{}>",
			"The output directory may contain files used as output files, but the application override option is not used."
	),

	/**
	 * The given output directory exists, is a directory, but the application cannot execute the directory.
	 * Arguments: application name, directory type, directory name.
	 */
	DIR_CANT_EXECUTE(
			-387,
			"{}: cannot execute {} directory <{}>, please check permissions",
			"The given output directory exists, is a directory, but the application cannot execute the directory."
	),

	/**
	 * Writing to an output directory resulted in an IO exception.
	 * Arguments: application name, directory type, directory name, exception message.
	 */
	IO_EXCEPTION_WRITING(
			-388,
			"{}: caught an IO exception writing to {} directory <{}>, message: {}",
			"Writing to an output directory resulted in an IO exception."
	),

	/**
	 * Writing to an output directory resulted in an exception.
	 * Arguments: application name, directory type, directory name, exception message.
	 */
	EXCEPTION_WRITING(
			-389,
			"{}: caught an exception writing to {} directory <{}>, message: {}",
			"Writing to an output directory resulted in an exception."
	),

	/**
	 * An output path contains a parent that exists but that is not a directory.
	 * Arguments: application name, directory type.
	 */
	PARENT_NOTDIR(
			-390,
			"{}: {} parent directory exists but is not a directory",
			"An output path contains a parent that exists but that is not a directory."
	),

	/**
	 * The given output directory parent exists, is a directory, but the application cannot write to directory.
	 * Arguments: application name, directory type, parent name.
	 */
	PARENT_CANT_WRITE(
			-391,
			"{}: cannot write {} directory parent <{}>, please check permissions",
			"The given output directory parent exists, is a directory, but the application cannot write to directory."
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
	Templates_OutputDirectory(final int code, final String message, final String description){
		//only for app name, all other validations in test code
		Validate.validState(message.contains("{}"));

		this.code = code;
		this.message = message;
		this.description = description;
	}

	@Override
	public IsErrorCategory getCategory() {
		return StandardErrorCategories.OUTPUT_DIRECTORY;
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
