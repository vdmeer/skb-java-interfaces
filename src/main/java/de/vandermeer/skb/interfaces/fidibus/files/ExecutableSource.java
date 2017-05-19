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

package de.vandermeer.skb.interfaces.fidibus.files;

import java.net.URL;
import java.nio.file.Path;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;
import de.vandermeer.skb.interfaces.validators.FiDiValidationOption;

/**
 * API for an object describing an externally executable command.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface ExecutableSource extends FileInfo, FileSource {

	/**
	 * Creates a new object describing an executable.
	 * @param filename the file name, including path, must not be blank
	 * @param name a name for the object, must not be blank
	 * @param displayName a display name for the object, must not be blank
	 * @param description a description, must not be blank
	 * @return the new object
	 */
	static ExecutableSource create(final String filename, final String name, final String displayName, final String description){
		Validate.notBlank(filename);
		Validate.notBlank(name);
		Validate.notBlank(displayName);
		Validate.notBlank(description);

		return new ExecutableSource() {
			protected final IsErrorSet errors = IsErrorSet.create();
			protected Path path;
			protected boolean isValidated;

			@Override
			public String getDescription() {
				return description;
			}

			@Override
			public String getDisplayName() {
				return displayName;
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public boolean validateSource() {
				return this.isValidated = this.validateFilename();
			}

			@Override
			public boolean isValidated() {
				return this.isValidated;
			}

			@Override
			public IsErrorSet getErrorSet() {
				return this.errors;
			}

			@Override
			public void setPath(Path path) {
				this.path = path;
			}

			@Override
			public Path getPath() {
				return this.path;
			}

			@Override
			public String getFilename() {
				String execName = StringUtils.removeStart(filename, "\"");
				execName = StringUtils.removeEnd(execName, "\"");
				return execName;
			}
		};
	}

	/**
	 * Cannot execute a URL, so this method is not implement
	 * @return nothing
	 * @throws NotImplementedException
	 */
	@Override
	default URL getURL() {
		throw new NotImplementedException("executable is not a URL");
	}

	/**
	 * Cannot execute a URL, so this method is not implement
	 * @param url ignored
	 * @throws NotImplementedException
	 */
	@Override
	default void setURL(URL url) {
		throw new NotImplementedException("executable is not a URL");
	}

	/**
	 * The standard options returned are: file exists, is not hidden, can execute
	 * @return standard options
	 */
	@Override
	default FiDiValidationOption[] getOptions() {
		return new FiDiValidationOption[]{
				FiDiValidationOption.EXISTS,
				FiDiValidationOption.NOT_HIDDEN,
				FiDiValidationOption.EXECUTE
		};
	}

	/**
	 * An executable can only be found in a file system.
	 * @return location being file system
	 */
	@Override
	default FileLocation[] getLocations() {
		return new FileLocation[]{FileLocation.FILESYSTEM};
	}
}
