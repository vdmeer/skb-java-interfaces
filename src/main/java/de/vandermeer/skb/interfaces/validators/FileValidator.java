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
 * Validator for a file.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface FileValidator extends IsValidator {

	/**
	 * Creates a new default file validator.
	 * @return new validator
	 */
	static FileValidator create(){
		return new FileValidator() {
			protected IsErrorSet errors = IsErrorSet.create();

			@Override
			public String getDescription() {
				return "Validates a file against a set of options.";
			}

			@Override
			public IsErrorSet getErrorSet() {
				return this.errors;
			}
		};
	}

	/**
	 * Validates a file for given options.
	 * @param name the file name
	 * @param options the validation options, must not have null members
	 * @return true on success, false on error
	 */
	default boolean validate(final String name, final FiDiValidationOption[] options){
		Validate.noNullElements(options);

		this.getErrorSet().clearMessages();

		if(StringUtils.isBlank(name)){
			this.getErrorSet().add("file name was blank");
			return false;
		}

		Path path = FileSystems.getDefault().getPath(name);
		File file = path.toFile();

		if(file.exists() && !file.isFile()){
			this.getErrorSet().add("file <{}> exists but is not a file", path);
			return false;
		}

		for(FiDiValidationOption option : options){
			switch(option){
				case CREATE:
					if(file.exists()){
						this.getErrorSet().add("cannot create file <{}> does already exist", path);
					}
					else{
						Path parent = path.getParent();
						if(parent!=null){
							File parentFile = parent.toFile();
							if(!parentFile.exists()){
								this.getErrorSet().add("cannot create file <{}>, parent does not exist", path);
							}
							else if(!parentFile.isDirectory()){
								this.getErrorSet().add("cannot create file <{}>, parent is not a directory", path);
							}
							else if(!parent.toFile().canWrite()){
								this.getErrorSet().add("cannot create file <{}>, parent is not writable", path);
							}
						}
					}
					break;
				case DELETE:
					if(!file.exists()){
						this.getErrorSet().add("cannot file directory <{}> does not exist", path);
					}
					else{
						Path parent = path.getParent();
						if(parent!=null){
							File parentFile = parent.toFile();
							if(!parentFile.exists()){
								this.getErrorSet().add("cannot delete file <{}>, parent does not exist", path);
							}
							else if(!parentFile.isDirectory()){
								this.getErrorSet().add("cannot delete file <{}>, parent is not a directory", path);
							}
							else if(!parent.toFile().canWrite()){
								this.getErrorSet().add("cannot delete file <{}>, parent is not writable", path);
							}
						}
					}
					break;
				case EXECUTE:
					if(!file.canExecute()){
						this.getErrorSet().add("cannot execute file <{}>", path);
					}
					break;
				case EXISTS:
					if(!file.exists()){
						this.getErrorSet().add("file <{}> does not exist", path);
					}
					break;
				case NOT_HIDDEN:
					if(file.isHidden()){
						this.getErrorSet().add("file <{}> is a hidden", path);
					}
					break;
				case READ:
					if(!file.canRead()){
						this.getErrorSet().add("file <{}> is not readable", path);
					}
					break;
				case WRITE:
					if(!file.canWrite()){
						this.getErrorSet().add("file <{}> is not writable", path);
					}
					break;
			}
		}
		return !this.getErrorSet().hasMessages();
	}

}
