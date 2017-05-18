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

import de.vandermeer.skb.interfaces.fidibus.FiDi_Source;
import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;
import de.vandermeer.skb.interfaces.validators.FiDiValidationOption;

/**
 * Source for a String Template Group STG file.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface STG_FileSource extends FileSource, FiDi_Source {

	/**
	 * Creates a new file source with location file system.
	 * @param file the file to source for, must not be blank
	 * @return a new information object
	 */
	static STG_FileSource fromFilesystem(final String file){
		return create(file, new FileLocation[]{FileLocation.FILESYSTEM});
	}

	/**
	 * Creates a new file source with location resource.
	 * @param file the file to source for, must not be blank
	 * @return a new information object
	 */
	static STG_FileSource fromResource(final String file){
		return create(file, new FileLocation[]{FileLocation.RESOURCE});
	}

	/**
	 * Creates a new file source.
	 * @param file the file to source for, must not be blank
	 * @param locations the locations option, must not be null
	 * @return a new information object
	 */
	static STG_FileSource create(final String file, final FileLocation[] locations){
		Validate.notBlank(file);
		Validate.noNullElements(locations);

		return new STG_FileSource() {
			protected IsErrorSet errors = IsErrorSet.create();
			protected Path path;
			protected boolean isValidated;
			protected URL url;

			@Override
			public String getDescription() {
				return "The source object for an STG file";
			}

			@Override
			public String getDisplayName() {
				return "STG File Source";
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
				return "stg-file-source";
			}

			@Override
			public FiDiValidationOption[] getOptions() {
				return FileSource.VALIDATION_OPTIONS;
			}

			@Override
			public void setPath(Path path) {
				this.path = path;
			}

			@Override
			public URL getURL() {
				return this.url;
			}

			@Override
			public void setURL(URL url) {
				this.url = url;
			}

			@Override
			public boolean validateSource() {
				this.isValidated = false;
				if(!this.getFilename().endsWith(".stg")){
					this.errors.add("stg file: file name must have '.stg' extension");
					return false;
				}
				return this.isValidated = this.validateFilename();
			}

			@Override
			public boolean isValidated() {
				return this.isValidated;
			}
		};
	}

}
