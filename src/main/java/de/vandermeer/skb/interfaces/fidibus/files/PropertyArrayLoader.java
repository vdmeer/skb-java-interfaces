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

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;

/**
 * Loader for a set of properties from several property files.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface PropertyArrayLoader extends FileListLoader {

	/**
	 * Creates a new property file loader reading from file system first and as resource next.
	 * @param sources the property sources, must not have null members
	 * @return a new file loader
	 */
	static PropertyArrayLoader create(FileSourceList sources){
		return create(sources, new FileLocation[]{FileLocation.FILESYSTEM, FileLocation.RESOURCE});
	}

	/**
	 * Creates a new property file loader.
	 * @param sources the property sources, must not have null members
	 * @param locations the file locations, must not have null members
	 * @return a new string file loader
	 */
	static PropertyArrayLoader create(FileSourceList sources, FileLocation[] locations){
		Validate.notNull(sources);
		Validate.noNullElements(sources.getSource());
		Validate.noNullElements(locations);

		PropertyArrayLoader ret = new PropertyArrayLoader() {
			protected final IsErrorSet errors = IsErrorSet.create();

			@Override
			public String getDescription() {
				return "Loads multiple properties from a multiple files.";
			}

			@Override
			public String getDisplayName() {
				return "Property Array Loader";
			}

			@Override
			public String getName() {
				return "property-array-loader";
			}

			@Override
			public IsErrorSet getErrorSet() {
				return this.errors;
			}

			@Override
			public FileSourceList getSource() {
				return sources;
			}

		};
		ret.validateSource();
		return ret;
	}

	@Override
	default Properties[] read() {
		this.getErrorSet().clearMessages();
		if(this.validateSource()==false){
			//no valid source
			//TODO ERROR
			return null;
		}

		Set<Properties> properties = new HashSet<>();
		for(FileSource source : this.getSource().getSource()){
			PropertyFileLoader loader = PropertyFileLoader.create(source.getFilename());
			if(loader.getErrorSet().hasMessages()){
				this.getErrorSet().addAll(loader.getErrorSet());
			}
			else{
				properties.add(loader.read());
			}
		}
		return properties.toArray(new Properties[0]);
	}

}
