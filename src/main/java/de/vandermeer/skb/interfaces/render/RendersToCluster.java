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

/**
 * Interface for objects that provide a render method for text rendering resulting in a text cluster.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface RendersToCluster {

	/**
	 * Returns a `collection` view of the rendered object.
	 * @return `collection` view, should not be null and have no blank elements
	 */
	Collection<String> renderAsCollection();

	/**
	 * Returns an `iterable` view of the rendered object.
	 * @return `iterable` view, should not be null and have no blank elements
	 */
	default Iterable<String> renderAsIterable(){
		return this.renderAsCollection();
	}

	/**
	 * Returns an `iterator` view of the rendered object.
	 * @return `iterator` view, should not be null and have no blank elements
	 */
	default Iterator<String> renderAsIterator(){
		return this.renderAsCollection().iterator();
	}

	/**
	 * Returns an `array` view of the rendered object.
	 * @return `array` view, should not be null and have no blank elements
	 */
	default String[] renderAsArray(){
		return this.renderAsCollection().toArray(new String[0]);
	}
}
