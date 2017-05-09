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

package de.vandermeer.skb.interfaces.application;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * Set of options for an application parser.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface ApoParserOptionSet<O extends ApoBase> {

	/**
	 * Adds all options to the set.
	 * @param options the options to be added, ignored if null, any null element is ignored as well
	 * @return self to allow chaining
	 * @throws IllegalStateException if the option is already in use
	 */
	default ApoParserOptionSet<O> addAllOptions(Iterable<?> options) throws IllegalStateException{
		if(options!=null){
			for(Object opt : options){
				this.addOption(opt);
			}
		}
		return this;
	}

	/**
	 * Adds all options to the set.
	 * @param options the options to be added, ignored if null, any null element is ignored as well
	 * @return self to allow chaining
	 * @throws IllegalStateException if the option is already in use
	 */
	default ApoParserOptionSet<O> addAllOptions(Object[] options) throws IllegalStateException{
		if(options!=null){
			for(Object opt : options){
				this.addOption(opt);
			}
		}
		return this;
	}

	/**
	 * Adds an option to the set.
	 * @param option the option to be added, ignored if null
	 * @return self to allow chaining
	 * @throws IllegalStateException if the option is already in use
	 */
	ApoParserOptionSet<O> addOption(Object option) throws IllegalStateException;

	/**
	 * Returns the key set of the options.
	 * @return key set, empty if no options set
	 */
	default Set<String> getKeys(){
		return this.getMap().keySet();
	}

	/**
	 * Returns the options of the set as a mapping of key to option object.
	 * @return mapping of key to options object, empty if no options in the set
	 */
	Map<String, O> getMap();

	/**
	 * Returns the option object.
	 * @return option objects, empty if no option set
	 */
	default Collection<O> getValues(){
		return this.getMap().values();
	}

	/**
	 * Tests if an option key is already in the set.
	 * @param key the key to test
	 * @return true if key is in the set, false otherwise
	 */
	default boolean hasOption(String key){
		return this.getMap().keySet().contains(key);
	}

	/**
	 * Returns the number of options.
	 * @return number of options, 0 if none added
	 */
	default int size(){
		return this.getMap().size();
	}

	/**
	 * Returns a sorted collection of options.
	 * @return sorted collection
	 */
	default Collection<O> sortedList(){
		return this.sortedMap().values();
	}

	/**
	 * Returns a mapping of comparison strings to options, a sorted map.
	 * @return sorted map
	 */
	default TreeMap<String, O> sortedMap(){
		TreeMap<String, O> ret = new TreeMap<>();
		for(Entry<String, O> entry : this.getMap().entrySet()){
			ret.put(entry.getKey().toLowerCase(), entry.getValue());
		}
		return ret;
	}

}
