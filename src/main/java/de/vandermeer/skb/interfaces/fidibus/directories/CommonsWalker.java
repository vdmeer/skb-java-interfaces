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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.fidibus.files.FileLocation;
import de.vandermeer.skb.interfaces.fidibus.files.FileSource;

/**
 * Apache commons IO Directory Walker.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public class CommonsWalker extends DirectoryWalker<FileSource> {

	/**
	 * Returns a new loader that uses a directory walker with directory and file filters.
	 * @param dirFilter directory filters the walker should use
	 * @param fileFilter file filters the walker should use
	 */
	public CommonsWalker(IOFileFilter dirFilter, IOFileFilter fileFilter){
		super(dirFilter, fileFilter, -1);
	}

	@Override
	protected void handleFile(File file, int depth, Collection<FileSource> results) throws IOException {
		FileSource fileSource = FileSource.create(
				file.getAbsolutePath(),
				new FileLocation[]{FileLocation.FILESYSTEM}
		);
		results.add(fileSource);
	}

	/**
	 * Walks a directory and collects sources according to set filters.
	 * @param directory the start directory, must not be blank
	 * @return found sources, empty if none found
	 * @throws IOException if any IO problem occurred, with message and original cause
	 */
	public List<FileSource> load(String directory) throws IOException{
		Validate.notBlank(directory);
		List<FileSource> ret = new ArrayList<>();
		try {
			File file = new File(directory);
			walk(file, ret);
		}
		catch (IOException ex) {
			throw new IOException("IOException while walking dir <" + directory + ">, se cause for details", ex);
		}
		return ret;
	}
}
