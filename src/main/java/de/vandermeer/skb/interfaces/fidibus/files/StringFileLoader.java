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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;

/**
 * String file loader / reader.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface StringFileLoader extends FileLoader {

	/**
	 * Creates a new string file loader reading from file system first and as resource next.
	 * @param filename the filename, must not be blank
	 * @return a new file loader
	 */
	static StringFileLoader create(String filename){
		return create(filename, new FileLocation[]{FileLocation.FILESYSTEM, FileLocation.RESOURCE});
	}

	/**
	 * Creates a new string file loader.
	 * @param filename the filename, must not be blank
	 * @param locations the file locations, must not have null members
	 * @return a new string file loader
	 */
	static StringFileLoader create(String filename, FileLocation[] locations){
		Validate.notBlank(filename);
		Validate.noNullElements(locations);

		StringFileLoader ret = new StringFileLoader() {
			protected final FileSource source = FileSource.create(filename, locations);
			protected final IsErrorSet errors = IsErrorSet.create();

			@Override
			public String getDescription() {
				return "Loads strings from a file.";
			}

			@Override
			public String getDisplayName() {
				return "String File Loader";
			}

			@Override
			public String getName() {
				return "string-loader";
			}

			@Override
			public IsErrorSet getErrorSet() {
				return this.errors;
			}

			@Override
			public FileSource getSource() {
				return this.source;
			}
		};
		ret.validateSource();
		return ret;
	}

	@SuppressWarnings("resource")
	@Override
	default String read() {
		String ret = null;
		this.getErrorSet().clearMessages();;
		if(this.validateSource()==false){
			//no valid source
			return ret;
		}

		Scanner s = null;
		try{
			s = new Scanner(this.getSource().getPath().toUri().toURL().openStream()).useDelimiter("\\Z");
			ret = s.next();
			s.close();
		}
		catch(FileNotFoundException ex){
			this.getErrorSet().add("{}: unexpected file not found exception - {}", "string file loader", ex.getMessage());
			return ret;
		} catch (IOException exio) {
			this.getErrorSet().add("{}: unexpected IO not found exception - {}", "string file loader", exio.getMessage());
			return ret;
		}
		return ret;
	}

}
