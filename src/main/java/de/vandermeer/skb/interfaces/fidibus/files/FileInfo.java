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
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.interfaces.messages.sets.HasErrorSet;
import de.vandermeer.skb.interfaces.validators.FiDiValidationOption;
import de.vandermeer.skb.interfaces.validators.FileValidator;

/**
 * File information that may be used as source or target.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface FileInfo extends HasErrorSet {

	/**
	 * Returns the locations the file should be searched for
	 * @return file locations, must not have null members
	 */
	FileLocation[] getLocations();

	/**
	 * Returns the validation options for the file info.
	 * @return validation options, not validation can be done if null
	 */
	FiDiValidationOption[] getOptions();

	/**
	 * Returns the original file the object is created for, as an FQPN.
	 * @return the original file as FQPN, must not be blank
	 */
	String getFilename();

	/**
	 * Validates the file using the set options.
	 * @return true on success, false on error
	 */
	default boolean validateFilename(){
		this.getErrorSet().clearMessages();
		this.setPath(null);

		if(StringUtils.isBlank(this.getFilename())){
			this.getErrorSet().add("file: given file name was blank");
			return false;
		}
		if(this.getLocations()==null || ArrayUtils.contains(this.getLocations(), null)){
			this.getErrorSet().add("file: given locations was null or had a null member");
			return false;
		}

		String filename = this.getFilename();
		StrBuilder tried = new StrBuilder();
		for(FileLocation location : this.getLocations()){
			try{
				switch(location){
					case CLASSPATH:
						//TODO
						tried.appendSeparator(", ").append(location.getDisplayName());
						break;
					case FILESYSTEM:
						FileValidator validator = FileValidator.create();
						validator.validate(filename, this.getOptions());
						this.getErrorSet().addAll(validator.getErrorSet());
						tried.appendSeparator(", ").append(location.getDisplayName());
						if(!validator.getErrorSet().hasMessages()){
							this.setPath(FileSystems.getDefault().getPath(filename));
							this.getErrorSet().clearMessages();
						}
						break;
					case RESOURCE:
						tried.appendSeparator(", ").append(location.getDisplayName());
						ClassLoader loader = Thread.currentThread().getContextClassLoader();
						URL url = loader.getResource(filename);
						if(url==null){
							url = this.getClass().getClassLoader().getResource(filename);
						}
						if(url==null){
							this.getErrorSet().add("file: could not get resource URL");
						}
						else{
							this.setPath(FileSystems.getDefault().getPath(filename));
							this.getErrorSet().clearMessages();
							this.setURL(url);
						}
						break;
				}
			}
			catch(Exception ex){
				this.getErrorSet().add("file: catched unpredicted exception: " + ex.getMessage());
				break;
			}
			if(this.getPath()!=null){
				break;
			}
		}
		if(this.getPath()==null){
			this.getErrorSet().add("file: could not find file <{}>, tried {}", filename, tried.build());
			return false;
		}
		return true;
	}

	/**
	 * Sets the file path.
	 * @param path new path, null to reset
	 */
	void setPath(Path path);

	/**
	 * Returns the file path.
	 * @return path, null if none set
	 */
	Path getPath();

	/**
	 * Returns the found URL if a file was found using a URL (for instance as resource).
	 * @return URL if found, null if not found via URL
	 */
	URL getURL();

	/**
	 * Sets the URL.
	 * @param url new URL, null if not required
	 */
	void setURL(URL url);

	/**
	 * Returns the extension of a validated file.
	 * @return file extension, null if none there
	 */
	default String fnExtension(){
		if(this.getPath()!=null){
			return StringUtils.substringAfterLast(this.getPath().toString(), ".");
		}
		return null;
	}

	/**
	 * Returns the basename of a validated file.
	 * @return basename, null if none there
	 */
	default String fnBasename(){
		if(this.getPath()!=null){
			String name = this.getPath().getName(this.getPath().getNameCount()-1).toString();
			if(name.contains(".")){
				return StringUtils.substringBeforeLast(name, ".");
			}
			else{
				return name;
			}
		}
		return null;
	}

	/**
	 * Returns the file name of a validated file.
	 * @return file name, null if none there
	 */
	default String fnName(){
		if(this.getPath()!=null){
			return this.getPath().getFileName().toString();
		}
		return null;
	}

	/**
	 * Returns the path of a validated file.
	 * @return path, null if none there
	 */
	default String fnPath(){
		if(this.getPath()!=null && this.getPath().getNameCount()>1){
			return this.getPath().getParent().toString();
		}
		return null;
	}

	/**
	 * Returns the absolute path of a validated file.
	 * @return absolute path, null if none there
	 */
	default String fnAbsolutePath(){
		if(this.getPath()!=null && this.getPath().getNameCount()>1){
			return this.getPath().toAbsolutePath().toString();
		}
		return null;
	}

}

/*
As string returns the root path of the file source.
AS_STRING_ROOT_PATH,

As string returns the set-root path of the file source.
AS_STRING_SET_ROOT_PATH,

As string returns the set-root name of the file source.
AS_STRING_SET_ROOT_NAME,
*/