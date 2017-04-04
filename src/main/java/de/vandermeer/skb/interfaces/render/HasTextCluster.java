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

package de.vandermeer.skb.interfaces.render;

import java.util.Collection;
import java.util.Iterator;

import de.vandermeer.skb.interfaces.categories.CategoryHas;

/**
 * Interface for objects that do provide a cluster (iterable, iterator, and array) of text as textual representation.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public interface HasTextCluster extends CategoryHas {

	/**
	 * Returns text representation of an object.
	 * @return collection view of text, should not be null and have no blank elements
	 */
	Collection<String> getTextAsCollection();

	/**
	 * Returns text representation of an object.
	 * @return `iterable` view of text, should not be null and have no blank elements
	 */
	default Iterable<String> getTextAsIterable(){
		return this.getTextAsCollection();
	}

	/**
	 * Returns text representation of an object.
	 * @return `iterator` view of text, should not be null and have no blank elements
	 */
	default Iterator<String> getTextAsIterator(){
		return this.getTextAsCollection().iterator();
	}

	/**
	 * Returns text representation of an object.
	 * @return `array` view of text, should not be null and have no blank elements
	 */
	default String[] getTextAsArray(){
		return this.getTextAsCollection().toArray(new String[0]);
	}
}
