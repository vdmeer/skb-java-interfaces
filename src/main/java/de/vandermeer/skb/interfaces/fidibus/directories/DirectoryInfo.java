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
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.interfaces.messages.sets.HasErrorSet;
import de.vandermeer.skb.interfaces.validators.DirectoryValidator;
import de.vandermeer.skb.interfaces.validators.FiDiValidationOption;

/**
 * Directory information that may be used as source or target.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface DirectoryInfo extends HasErrorSet {

	/**
	 * Returns the locations the directory should be searched for
	 * @return directory locations, must not have null members
	 */
	DirectoryLocation[] getLocations();

	/**
	 * Returns the validation options for the directory info.
	 * @return validation options, not validation can be done if null
	 */
	FiDiValidationOption[] getOptions();

	/**
	 * Returns the original directory the object is created for.
	 * @return the original directory, must not be blank
	 */
	String getDirectoryName();

	/**
	 * Validates the directory using the set options.
	 * @return true on success, false on error
	 */
	default boolean validateDirectory(){
		this.getErrorSet().clearMessages();
		this.setPath(null);

		if(StringUtils.isBlank(this.getDirectoryName())){
			this.getErrorSet().add("directory: given directory name was blank");
			return false;
		}
		if(this.getLocations()==null || ArrayUtils.contains(this.getLocations(), null)){
			this.getErrorSet().add("directory: given locations was null or had a null member");
			return false;
		}

		String directory = this.getDirectoryName();
		StrBuilder tried = new StrBuilder();
		for(DirectoryLocation location : this.getLocations()){
			try{
				switch(location){
					case CLASSPATH:
						String[] cp = StringUtils.split(System.getProperty("java.class.path"), File.pathSeparatorChar);
						for(String s : cp){
							if(!StringUtils.endsWith(s, ".jar") && !StringUtils.startsWith(s, File.separator)){
								DirectoryValidator validator = DirectoryValidator.create();
								validator.validate(s + File.separator + directory, this.getOptions());
								this.getErrorSet().addAll(validator.getErrorSet());
								if(!validator.getErrorSet().hasMessages()){
									this.setPath(FileSystems.getDefault().getPath(s + File.separator + directory));
									this.getErrorSet().clearMessages();
									break;
								}
							}
						}
						tried.appendSeparator(", ").append(location.getDisplayName());
						break;
					case FILESYSTEM:
						DirectoryValidator validator = DirectoryValidator.create();
						validator.validate(directory, this.getOptions());
						this.getErrorSet().addAll(validator.getErrorSet());
						tried.appendSeparator(", ").append(location.getDisplayName());
						if(!validator.getErrorSet().hasMessages()){
							this.setPath(FileSystems.getDefault().getPath(directory));
							this.getErrorSet().clearMessages();
						}
						break;
				}
			}
			catch(Exception ex){
				this.getErrorSet().add("directory: catched unpredicted exception: " + ex.getMessage());
				break;
			}
			if(this.getPath()!=null){
				break;
			}
		}
		if(this.getPath()==null){
			this.getErrorSet().add("directory: could not find directory <{}>, tried {}", directory, tried.build());
			return false;
		}
		return true;
	}

	/**
	 * Sets the directory path.
	 * @param path new path, null to reset
	 */
	void setPath(Path path);

	/**
	 * Returns the directory path.
	 * @return path, null if none set
	 */
	Path getPath();

}
