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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.fidibus.files.FileSource;
import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;

/**
 * A directory reader implementing using an Apache Directory Walker.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface APIOWalker extends DirectoryLoader {

	/**
	 * Creates a new directory walker.
	 * @param directoryName the directory name, must not be blank
	 * @param directoryFilter a filter for directory, null if none required
	 * @param fileFilter a filter for files, null if none required
	 * @return new walker
	 */
	static APIOWalker create(final String directoryName, final IOFileFilter directoryFilter, final IOFileFilter fileFilter){
		Validate.notBlank(directoryName);

		APIOWalker ret = new APIOWalker() {
			protected final DirectorySource source = DirectorySource.create(directoryName, new DirectoryLocation[]{DirectoryLocation.FILESYSTEM});
			protected final IsErrorSet errors = IsErrorSet.create();

			@Override
			public String getDescription() {
				return "loads a directory, recursivley if required, using an Apache walker and IO filters for files and directories";
			}

			@Override
			public String getDisplayName() {
				return "Directory Loader (Apache IO Walker)";
			}

			@Override
			public String getName() {
				return "apio-dir-walker";
			}

			@Override
			public IsErrorSet getErrorSet() {
				return this.errors;
			}

			@Override
			public DirectorySource getSource() {
				return this.source;
			}

			@Override
			public IOFileFilter getFileFilter() {
				return fileFilter;
			}

			@Override
			public IOFileFilter getDirectoryFilter() {
				return directoryFilter;
			}
		};
		ret.validateSource();
		return ret;
	}

	/**
	 * Returns a filter for directories.
	 * @return directory filter, null if none required
	 */
	IOFileFilter getDirectoryFilter();

	/**
	 * Returns a filter for files.
	 * @return file filter, null if none required
	 */
	IOFileFilter getFileFilter();

	@Override
	default FileSource[] read(){
		if(!this.getErrorSet().hasMessages()){
			List<FileSource> ret = new ArrayList<>();
			try {
				CommonsWalker walker = new CommonsWalker(this.getDirectoryFilter(), this.getFileFilter());
				ret = walker.load(this.getSource().getDirectoryName());
			}
			catch (IOException ex) {
				this.getErrorSet().add("{}, cause: {}", new Object[]{ex.getMessage(), ex.getCause().getMessage()});
				return null;
			}
			return ret.toArray(new FileSource[0]);
		}
		return null;
	}

}
