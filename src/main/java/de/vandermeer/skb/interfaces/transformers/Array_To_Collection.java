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

package de.vandermeer.skb.interfaces.transformers;

import java.util.Collection;

import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.strategies.IsCollectionStrategy;

/**
 * Transforms input provided by an `array` into a collection of given type.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface Array_To_Collection extends IsTransformer<Iterable<?>, Collection<?>> {

	/**
	 * Converts an array into a collection of given type.
	 * @param <T> type of the objects in the returned collection
	 * @param <S> type of the returned collection
	 * @param input the array to convert
	 * @param strategy the strategy determining the output collection type
	 * @return an empty collection or a collection of with all input elements
	 * @throws NullPointerException if any argument was null
	 */
	default <T, S extends Collection<T>> S transform(T[] input, IsCollectionStrategy<S, T> strategy) {
		Validate.notNull(input);
		Validate.notNull(strategy);

		S ret = strategy.get();
		for(T t : input){
			ret.add(t);
		}
		return ret;
	}

	/**
	 * Creates a new transformer.
	 * @return new transformer
	 */
	static Array_To_Collection create(){
		return new Array_To_Collection() {};
	}
}
