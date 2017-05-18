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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.messages.errors.Templates_InputFile;
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

	@Override
	default List<String> read() {
		this.getErrorSet().clearMessages();;
		if(this.validateSource()==false){
			//no valid source
			return null;
		}

		ArrayList<String> ret = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(this.getSource().getFilename()));
			String str;
			while((str=in.readLine()) != null){
				ret.add(str);
			}
			in.close();
		}
		catch(FileNotFoundException e){
			this.getErrorSet().add(Templates_InputFile.FILE_NOT_FOUND.getError("plain SVG input", this.getSource().getFilename(), e.getMessage()));
			return null;
		}
		catch (IOException exio) {
			this.getErrorSet().add("{}: unexpected IO not found exception - {}", "string file loader", exio.getMessage());
			return null;
		}

		return ret;
	}

}
