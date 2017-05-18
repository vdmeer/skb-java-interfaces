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

package de.vandermeer.skb.interfaces.shell;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.categories.has.HasDescription;
import de.vandermeer.skb.interfaces.categories.has.HasDisplayName;
import de.vandermeer.skb.interfaces.categories.has.HasName;
import de.vandermeer.skb.interfaces.categories.has.HasVersion;

/**
 * Command multi-set: a set of sets.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface CommandMultiSet extends HasName, HasDisplayName, HasVersion, HasDescription {

	/**
	 * Creates a new command multi-set.
	 * @param name the name of the set, must not be blank
	 * @param displayName the display name of the set, must not be blank
	 * @param version the version of the command set
	 * @param description the description of the set, must not be blank
	 * @return a new command multi-set on success
	 */
	static CommandMultiSet create(String name, String displayName, String version, String description){
		Validate.notBlank(name);
		Validate.notBlank(displayName);
		Validate.notBlank(version);
		Validate.notBlank(description);

		return new CommandMultiSet() {
			protected Map<String, CommandSet> map = new HashMap<>();

			@Override
			public String getDescription() {
				return description;
			}
			
			@Override
			public String getVersion() {
				return version;
			}
			
			@Override
			public String getDisplayName() {
				return displayName;
			}
			
			@Override
			public String getName() {
				return name;
			}
			
			@Override
			public Map<String, CommandSet> getMultiMap() {
				return this.map;
			}
		};
	}

	/**
	 * Returns the collection of added command sets.
	 * @return collection of added command sets, must not be null
	 */
	default Collection<CommandSet> getMultiCollection(){
		return this.getMultiMap().values();
	}

	/**
	 * Tests if a command set is already in the set.
	 * @param key the name to test
	 * @return true if key is in the set, false otherwise
	 */
	default boolean hasSet(String key){
		return this.getMultiMap().containsKey(key);
	}

	/**
	 * Returns a command set for a given name.
	 * @param name the command set name
	 * @return null on error, a command set on success
	 */
	default CommandSet getSet(String name){
		return this.getMultiMap().get(name);
	}

	/**
	 * Returns the command sets as mapping of command set to name.
	 * @return command sets, must not be null, empty if no command set was added
	 */
	Map<String, CommandSet> getMultiMap();

	/**
	 * Adds all command sets to the multi-set.
	 * @param sets the command set to be added, ignored if null, any null element is ignored as well
	 * @return self to allow chaining
	 * @throws IllegalStateException if the command set is already in use
	 */
	default CommandMultiSet addAllSets(Iterable<CommandSet> sets) throws IllegalStateException{
		if(sets!=null){
			for(CommandSet cs : sets){
				this.addSet(cs);
			}
		}
		return this;
	}

	/**
	 * Adds all command sets to the multi-set.
	 * @param sets the command set to be added, ignored if null, any null element is ignored as well
	 * @return self to allow chaining
	 * @throws IllegalStateException if the command set is already in use
	 */
	default CommandMultiSet addAllSets(CommandSet[] sets) throws IllegalStateException{
		if(sets!=null){
			for(CommandSet cs : sets){
				this.addSet(cs);
			}
		}
		return this;
	}

	/**
	 * Adds a command set to the set.
	 * @param set the command set to be added, ignored if null
	 * @return self to allow chaining
	 * @throws IllegalStateException if the command is already in use
	 */
	default CommandMultiSet addSet(CommandSet set) throws IllegalStateException {
		if(set!=null){
			Validate.validState(!this.getMultiMap().values().contains(set.getName()), "set <" + set.getName() +"> allready in use");
			this.getMultiMap().put(set.getName(), set);
		}
		return this;
	}

	/**
	 * Returns the number of command sets.
	 * @return number of command sets, 0 if none added
	 */
	default int size(){
		return this.getMultiMap().size();
	}

	/**
	 * Returns a sorted collection of command sets.
	 * @return sorted collection
	 */
	default Collection<CommandSet> sortedList(){
		return sortedMap().values();
	}

	/**
	 * Returns a sorted map of command sets, the mapping is the sort string to the command set.
	 * @return sorted map
	 */
	default TreeMap<String, CommandSet> sortedMap(){
		TreeMap<String, CommandSet> ret = new TreeMap<>();
		for(CommandSet set : this.getMultiCollection()){
			ret.put(set.getName(), set);
		}
		return ret;
	}

}
