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
 * Interface for objects that provide a render method for text rendering resulting in a text cluster with a width argument.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface RendersToClusterWidth {

	/**
	 * Returns an `array` view of the rendered object.
	 * @param width the maximum line width
	 * @return `array` view, should not be null and have no blank elements
	 */
	default String[] renderAsArray(int width){
		return this.renderAsCollection(width).toArray(new String[0]);
	}

	/**
	 * Returns a `collection` view of the rendered object.
	 * @param width the maximum line width
	 * @return `collection` view, should not be null and have no blank elements
	 */
	Collection<String> renderAsCollection(int width);

	/**
	 * Returns an `iterable` view of the rendered object.
	 * @param width the maximum line width
	 * @return `iterable` view, should not be null and have no blank elements
	 */
	default Iterable<String> renderAsIterable(int width){
		return this.renderAsCollection(width);
	}

	/**
	 * Returns an `iterator` view of the rendered object.
	 * @param width the maximum line width
	 * @return `iterator` view, should not be null and have no blank elements
	 */
	default Iterator<String> renderAsIterator(int width){
		return this.renderAsCollection(width).iterator();
	}
}
