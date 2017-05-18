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

package de.vandermeer.skb.interfaces.fidibus.directories;

import java.nio.file.Path;

import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.fidibus.FiDi_Source;
import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;
import de.vandermeer.skb.interfaces.validators.FiDiValidationOption;

/**
 * Directory source.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface DirectorySource extends DirectoryInfo, FiDi_Source {

	/**
	 * Validation options for a directory source.
	 */
	static final FiDiValidationOption[] VALIDATION_OPTIONS = new FiDiValidationOption[]{
			FiDiValidationOption.EXISTS, FiDiValidationOption.NOT_HIDDEN, FiDiValidationOption.READ
	};

	/**
	 * Creates a new file directory with location file system.
	 * @param directory the directory to source for, must not be blank
	 * @return a new information object
	 */
	static DirectorySource fromFilesystem(final String directory){
		return create(directory, new DirectoryLocation[]{DirectoryLocation.FILESYSTEM});
	}

	/**
	 * Creates a new file directory with location resource.
	 * @param directory the directory to source for, must not be blank
	 * @return a new information object
	 */
	static DirectorySource fromResource(final String directory){
		return create(directory, new DirectoryLocation[]{DirectoryLocation.CLASSPATH});
	}

	/**
	 * Creates a new directory source.
	 * @param directory the directory to source for, must not be blank
	 * @param locations the locations option, must not be null
	 * @return a new information object
	 */
	static DirectorySource create(final String directory, final DirectoryLocation[] locations){
		Validate.notBlank(directory);
		Validate.noNullElements(locations);

		return new DirectorySource() {
			protected IsErrorSet errors = IsErrorSet.create();
			protected Path path;
			protected boolean isValidated;

			@Override
			public String getDescription() {
				return "The source object for a directory";
			}

			@Override
			public String getDirectoryName() {
				return directory;
			}

			@Override
			public String getDisplayName() {
				return "Directory Source";
			}

			@Override
			public IsErrorSet getErrorSet() {
				return this.errors;
			}

			@Override
			public Path getPath() {
				return this.path;
			}

			@Override
			public DirectoryLocation[] getLocations() {
				return locations;
			}

			@Override
			public String getName() {
				return "directory-source";
			}

			@Override
			public FiDiValidationOption[] getOptions() {
				return VALIDATION_OPTIONS;
			}

			@Override
			public void setPath(Path path) {
				this.path = path;
			}

			@Override
			public boolean validateSource() {
				return this.isValidated = this.validateDirectory();
			}

			@Override
			public boolean isValidated() {
				return this.isValidated;
			}
		};
	}

	@Override
	default Path getSource(){
		return this.getPath();
	}
}
