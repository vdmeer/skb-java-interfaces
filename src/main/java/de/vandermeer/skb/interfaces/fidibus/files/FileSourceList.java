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

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.fidibus.FiDi_Source;
import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;

/**
 * File source list.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface FileSourceList extends FiDi_Source {

	/**
	 * Creates a new file source list with location file system.
	 * @param filenames the file names, must not have null elements
	 * @return a new information object
	 */
	static FileSourceList fromFilesystem(final String[] filenames){
		return create(filenames, new FileLocation[]{FileLocation.FILESYSTEM});
	}

	/**
	 * Creates a new file source list.
	 * @param filenames the file names, must not have null elements
	 * @param locations the required locations, must not have null elements
	 * @return a new file source list
	 */
	static FileSourceList create(final String[] filenames, final FileLocation[] locations){
		Validate.noNullElements(filenames);
		Validate.noNullElements(locations);

		Set<FileSource> fileSources = new HashSet<>();
		for(String filename : filenames){
			fileSources.add(FileSource.create(filename, locations));
		}

		return new FileSourceList() {
			protected IsErrorSet errors = IsErrorSet.create();
			protected final FileSource[] sources = fileSources.toArray(new FileSource[0]);
			protected boolean isValidated;

			@Override
			public String getDescription() {
				return "The source object for a list of files";
			}

			@Override
			public String getDisplayName() {
				return "File Source List";
			}

			@Override
			public String getName() {
				return "file-source-list";
			}

			@Override
			public IsErrorSet getErrorSet() {
				return this.errors;
			}

			@Override
			public boolean validateSource() {
				for(FileSource source : this.sources){
					source.validateFilename();
					this.errors.addAll(source.getErrorSet());
				}
				return !this.getErrorSet().hasMessages();
			}

			@Override
			public boolean isValidated() {
				return this.isValidated;
			}

			@Override
			public FileSource[] getSource() {
				return this.sources;
			}
		};
	}

	/**
	 * Returns the validated files.
	 * @return validated files, null if validation failed
	 */
	@Override
	FileSource[] getSource();

}
