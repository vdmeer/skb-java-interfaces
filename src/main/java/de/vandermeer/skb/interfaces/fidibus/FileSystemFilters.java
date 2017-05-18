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

package de.vandermeer.skb.interfaces.fidibus;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.Validate;

/**
 * Filters for file system operations.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface FileSystemFilters {

	/**
	 * Returns a new IO filter for wildecards on file extensions.
	 * @param extension the file extension, no leading wildecard and dot, must not be blank
	 * @return new IO filter
	 */
	static IOFileFilter WILDECARD(String extension){
		Validate.notBlank(extension);
		return new WildcardFileFilter(new String[]{"*." + extension});
	}

	/**
	 * Returns a new IO filter for wildecards.
	 * @param extensions the wildecards, full regular expressions as required, must not be null members
	 * @return new IO filter
	 */
	static IOFileFilter WILDECARD(String[] extensions){
		Validate.noNullElements(extensions);
		return new WildcardFileFilter(extensions);
	}

	/**
	 * A simple filter for directories.
	 */
	final static FileFilter DIRECTORIES_ONLY = new FileFilter(){
		public boolean accept(File f){
			if(f.exists() && f.isDirectory()){
				return true;
			}
			return false;
		}
	};

	/**
	 * A simple filter for files.
	 */
	final static FileFilter FILES_ONLY = new FileFilter(){
		public boolean accept(File f){
			if(f.exists() && f.isFile()){
				return true;
			}
			return false;
		}
	};

}
