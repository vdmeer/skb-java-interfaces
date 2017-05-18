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
import java.util.Properties;

import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;

/**
 * Property file loader / reader.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface PropertyFileLoader extends FileLoader {

	/**
	 * Creates a new property file loader reading from file system first and as resource next.
	 * @param filename the property filename, must not be blank
	 * @return a new file loader
	 */
	static PropertyFileLoader create(String filename){
		return create(filename, new FileLocation[]{FileLocation.FILESYSTEM, FileLocation.RESOURCE});
	}

	/**
	 * Creates a new property file loader.
	 * @param filename the property filename, must not be blank
	 * @param locations the file locations, must not have null members
	 * @return a new string file loader
	 */
	static PropertyFileLoader create(String filename, FileLocation[] locations){
		Validate.notBlank(filename);
		Validate.noNullElements(locations);

		PropertyFileLoader ret = new PropertyFileLoader() {
			protected final FileSource source = FileSource.create(filename, locations);
			protected final IsErrorSet errors = IsErrorSet.create();

			@Override
			public String getDescription() {
				return "Loads properties from a file.";
			}

			@Override
			public String getDisplayName() {
				return "Property File Loader";
			}

			@Override
			public String getName() {
				return "property-loader";
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
	default Properties read() {
		this.getErrorSet().clearMessages();
		if(this.validateSource()==false){
			//no valid source
			//TODO ERROR
			return null;
		}

		Properties ret = new Properties();
		try{
			if(this.getSource().getURL()!=null){
				ret.load(this.getSource().getURL().openStream());
			}
			else{
				ret.load(this.getSource().getPath().toFile().toURI().toURL().openStream());
			}
		}
		catch (IOException e){
			this.getErrorSet().add("{}: cannot load property file {}, IO exception\n-->{}", "pfl", this.getSource().getFilename(), e.getMessage());
			return null;
		}
		catch (Exception e){
			this.getErrorSet().add("{}: cannot load property file {}, general exception\n-->{}", "pfl", this.getSource().getFilename(), e);
			return null;
		}
		return ret;
	}

}
