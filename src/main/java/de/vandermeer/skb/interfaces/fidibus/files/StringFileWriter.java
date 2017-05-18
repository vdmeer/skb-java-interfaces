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

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;

/**
 * String file writer.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface StringFileWriter extends FileWriter {

	/**
	 * Creates a new string file writer.
	 * @param filename the output filename, must not be blank
	 * @return a new file writer
	 */
	static StringFileWriter create(String filename){
		Validate.notBlank(filename);

		StringFileWriter ret = new StringFileWriter() {
			protected final FileTarget target = FileTarget.create(filename, new FileLocation[]{FileLocation.FILESYSTEM});
			protected final IsErrorSet errors = IsErrorSet.create();

			@Override
			public String getDescription() {
				return "Writes strings to a file.";
			}

			@Override
			public String getDisplayName() {
				return "String File Writer";
			}

			@Override
			public String getName() {
				return "string-writer";
			}

			@Override
			public IsErrorSet getErrorSet() {
				return this.errors;
			}

			@Override
			public FileTarget getTarget() {
				return this.target;
			}
		};
		ret.validateTarget();
		return ret;
	}

	@Override
	default boolean write(Object content){
		this.getErrorSet().clearMessages();;
		if(this.validateTarget()==false){
			//no valid target
			//TODO ERR
			return false;
		}

		if(content==null){
			throw new IllegalArgumentException("content was null");//TODO
		}
		if(!(content instanceof String)){
			throw new IllegalArgumentException("content was not of type String");//TODO
		}

		try {
			FileUtils.writeStringToFile(this.getTarget().getPath().toFile(), content.toString(), "UTF-8");
		}
		catch (IOException ex) {
			this.getErrorSet().add("{}: IO exception writing to file w/writeStringToFile()", ex);
			return false;
		}

		return true;
	}

}
