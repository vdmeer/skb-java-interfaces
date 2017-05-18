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

import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.fidibus.FiDi_Target;
import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;
import de.vandermeer.skb.interfaces.validators.FiDiValidationOption;

/**
 * File target.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface FileTarget extends FileInfo, FiDi_Target {

	/**
	 * Validation options for a file target.
	 */
	static final FiDiValidationOption[] VALIDATION_OPTIONS = new FiDiValidationOption[]{
			FiDiValidationOption.EXISTS, FiDiValidationOption.NOT_HIDDEN, FiDiValidationOption.WRITE
	};

	/**
	 * Creates a new file target.
	 * @param file the file to target for, must not be blank
	 * @param locations the location option, must not be null
	 * @return a new information object
	 */
	static FileTarget create(final String file, final FileLocation[] locations){
		Validate.notBlank(file);
		Validate.noNullElements(locations);

		return new FileTarget() {
			protected IsErrorSet errors = IsErrorSet.create();
			protected Path path;
			protected boolean isValidated;
			protected URL url;

			@Override
			public URL getURL() {
				return this.url;
			}

			@Override
			public void setURL(URL url) {
				this.url = url;
			}

			@Override
			public String getDescription() {
				return "The target object for a file";
			}

			@Override
			public String getDisplayName() {
				return "File Target";
			}

			@Override
			public IsErrorSet getErrorSet() {
				return this.errors;
			}

			@Override
			public String getFilename() {
				return file;
			}

			@Override
			public Path getPath() {
				return this.path;
			}

			@Override
			public FileLocation[] getLocations() {
				return locations;
			}

			@Override
			public String getName() {
				return "file-target";
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
			public boolean validateTarget() {
				return this.isValidated = this.validateFilename();
			}

			@Override
			public boolean isValidated() {
				return this.isValidated;
			}
		};
	}

	@Override
	default Path getTarget(){
		return this.getPath();
	}
}

/*

	**
	 * Creates a file for a given absolute file name (absolute path and file name).
	 * @param fn absolute file name
	 * @return null on success, error string otherwise
	 *
	public static String createFile(String fn){
		File file = new File(fn);
		try {
			file.createNewFile();
		}
		catch (IOException e) {
			return e.getMessage();
		}
		return null;
	}

*/