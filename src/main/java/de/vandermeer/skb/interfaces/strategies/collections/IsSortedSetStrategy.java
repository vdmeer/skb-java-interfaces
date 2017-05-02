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

package de.vandermeer.skb.interfaces.strategies.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;

/**
 * Base for sorted set strategies.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface IsSortedSetStrategy<S extends SortedSet<T>, T extends Comparable<T>> extends IsSetStrategy<S, T> {

	/**
	 * Test if the collection is a list.
	 * @return true if list, false otherwise
	 */
	default boolean isList(){
		return false;
	}

	/**
	 * Test if the collection is a set.
	 * @return true if set, false otherwise
	 */
	default boolean isSet(){
		return true;
	}

	/**
	 * Test if the collection is a queue.
	 * @return true if queue, false otherwise
	 */
	default boolean isQueue(){
		return false;
	}

	/**
	 * Returns a new collection for the given collection.
	 * @param collection input collection
	 * @return new collection
	 */
	S get(Collection<T> collection);

	/**
	 * Returns a new collection for the given collection.
	 * @param collection input collection
	 * @param comparator comparator for objects
	 * @return new collection
	 */
	S get(Collection<T> collection, Comparator<T> comparator);

	/**
	 * Returns a new collection of requested type.
	 * @return new collection
	 */
	S get();

	/**
	 * Returns a new collection of requested type.
	 * @param comparator comparator for objects
	 * @return new collection
	 */
	S get(Comparator<T> comparator);
}
