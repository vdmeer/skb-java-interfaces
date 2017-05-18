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

package de.vandermeer.skb.interfaces.validators;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;

/**
 * Validator for a directory.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface DirectoryValidator extends IsValidator {

	/**
	 * Creates a new default directory validator.
	 * @return new validator
	 */
	static DirectoryValidator create(){
		return new DirectoryValidator() {
			protected IsErrorSet errors = IsErrorSet.create();

			@Override
			public String getDescription() {
				return "Validates a directory against a set of options.";
			}

			@Override
			public IsErrorSet getErrorSet() {
				return this.errors;
			}
		};
	}

	/**
	 * Validates a directory for given options.
	 * @param name the directory name
	 * @param options the validation options, must not have null members
	 * @return true on success, false on error
	 */
	default boolean validate(String name, final FiDiValidationOption[] options){
		Validate.noNullElements(options);

		this.getErrorSet().clearMessages();

		if(StringUtils.isBlank(name)){
			this.getErrorSet().add("directory name was blank");
			return false;
		}

		Path path = FileSystems.getDefault().getPath(name);
		File dir = path.toFile();

		if(dir.exists() && !dir.isDirectory()){
			this.getErrorSet().add("directory <{}> exists but is not a directory", path);
			return false;
		}

		for(FiDiValidationOption option : options){
			switch(option){
				case CREATE:
					if(dir.exists()){
						this.getErrorSet().add("cannot create directory <{}> does already exist", path);
					}
					else{
						Path parent = path.getParent();
						if(parent!=null){
							File parentFile = parent.toFile();
							if(!parentFile.exists()){
								this.getErrorSet().add("cannot create directory <{}>, parent does not exist", path);
							}
							else if(!parentFile.isDirectory()){
								this.getErrorSet().add("cannot create directory <{}>, parent is not a directory", path);
							}
							else if(!parent.toFile().canWrite()){
								this.getErrorSet().add("cannot create directory <{}>, parent is not writable", path);
							}
						}
					}
					break;
				case DELETE:
					if(!dir.exists()){
						this.getErrorSet().add("cannot delete directory <{}> does not exist", path);
					}
					else{
						Path parent = path.getParent();
						File parentFile = parent.toFile();
						if(!parentFile.exists()){
							this.getErrorSet().add("cannot delete directory <{}>, parent does not exist", path);
						}
						else if(!parentFile.isDirectory()){
							this.getErrorSet().add("cannot delete directory <{}>, parent is not a directory", path);
						}
						else if(!parent.toFile().canWrite()){
							this.getErrorSet().add("cannot delete directory <{}>, parent is not writable", path);
						}
					}
					break;
				case EXECUTE:
					if(!dir.canExecute()){
						this.getErrorSet().add("cannot execute directory <{}>", path);
					}
					break;
				case EXISTS:
					if(!dir.exists()){
						this.getErrorSet().add("directory <{}> does not exist", path);
					}
					break;
				case NOT_HIDDEN:
					if(dir.isHidden()){
						this.getErrorSet().add("directory <{}> is a hidden", path);
					}
					break;
				case READ:
					if(!dir.canRead()){
						this.getErrorSet().add("directory <{}> is not readable", path);
					}
					break;
				case WRITE:
					if(!dir.canWrite()){
						this.getErrorSet().add("directory <{}> is not writable", path);
					}
					break;
			}
		}
		return !this.getErrorSet().hasMessages();
	}

}
