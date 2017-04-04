/* Copyright 2016 Sven van der Meer <vdmeer.sven@mykolab.com>
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

package de.vandermeer.skb.interfaces.strategies;

import java.util.AbstractMap;
import java.util.Map;

import de.vandermeer.skb.interfaces.categories.CategoryIs;

/**
 * Base for Map strategies.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public interface IsMapStrategy<K, V> extends CategoryIs {

	/**
	 * Test if the map is an abstract map (an implementation of an {@link AbstractMap} hat does not require special keys.
	 * @return true if abstract map, false otherwise
	 */
	boolean isAbstractMap();

	/**
	 * Test if the map is a sorted map.
	 * @return true if sorted map, false otherwise
	 */
	boolean isSortedMap();

	/**
	 * Test if the map is a navigable map.
	 * @return true if navigable map, false otherwise
	 */
	boolean isNavigableMap();

	/**
	 * Test if the map is a concurrent map.
	 * @return true if concurrent map, false otherwise
	 */
	boolean isConcurrentMap();

	/**
	 * Test if the map is a hash table map.
	 * @return true if hash table map, false otherwise
	 */
	boolean isHashtable();

	/**
	 * Returns a new map for the used strategy.
	 * @return new map for the given class
	 */
	Map<K, V> get();

	/**
	 * Returns a new map for the used strategy initialized with the elements of another map.
	 * @param map input map
	 * @return new map view
	 */
	Map<K, V> get(Map<K, V> map);

}
