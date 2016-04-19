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
import java.util.Iterator;
import java.util.function.Predicate;

import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.strategies.IsCollectionStrategy;

/**
 * A transformer for clusters (iterable, iterator, array) to collections with transformations on each element of the input group.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public interface ClusterElementTransformer {

	/**
	 * Converts the input `iterable` to a collection applying a predicate and a transformation for each input element.
	 * @param <T1> the from/source of the transformer (also the type for `iterable`)
	 * @param <T2> the to/target of the transformer and the type of objects in the return collection
	 * @param <T3> any type that extends T1 to no limit conversion to a single type
	 * @param <S> the type of collection that should be returned
	 * @param input `iterable` of input elements
	 * @param transformer a transformer to apply for each input element before copying to the output
	 * @param strategy a strategy determining the type of output collection
	 * @return an empty collection of type T2 or a collection of type T2 with transformed objects from the input collection
	 * @throws NullPointerException if `input`, `transformer`, or `strategy` was null
	 */
	default <T1, T2, T3 extends T1, S extends Collection<T2>> S transform(Iterable<T3> input, Transformer<T1, T2> transformer, IsCollectionStrategy<S, T2> strategy) {
		return this.transform(input, transformer, null, strategy);
	}

	/**
	 * Converts the input `iterable` to a collection applying a predicate and a transformation for each input element.
	 * @param <T1> the from/source of the transformer (also the type for `iterable`)
	 * @param <T2> the to/target of the transformer and the type of objects in the return collection
	 * @param <T3> any type that extends T1 to no limit conversion to a single type
	 * @param <S> the type of collection that should be returned
	 * @param input `iterable` of input elements
	 * @param transformer a transformer to apply for each input element before copying to the output
	 * @param predicate a predicate to apply before transformation and copy of each input element (ignored if null)
	 * @param strategy a strategy determining the type of output collection
	 * @return an empty collection of type T2 or a collection of type T2 with transformed objects from the input collection
	 * @throws NullPointerException if `input`, `transformer`, or `strategy` was null
	 */
	default <T1, T2, T3 extends T1, S extends Collection<T2>> S transform(Iterable<T3> input, Transformer<T1, T2> transformer, Predicate<T3> predicate, IsCollectionStrategy<S, T2> strategy) {
		Validate.notNull(input);
		Validate.notNull(transformer);
		Validate.notNull(strategy);

		S ret = strategy.get();
		for(T3 t3 : input){
			if(predicate!=null && predicate.test(t3)){
				ret.add(transformer.transform(t3));
			}
			else if(predicate==null){
				ret.add(transformer.transform(t3));
			}
		}
		return ret;
	}

	/**
	 * Converts the input `iterator` to a collection applying a predicate and a transformation for each input element.
	 * @param <T1> the from/source of the transformer (also the type for the `iterator`)
	 * @param <T2> the to/target of the transformer and the type of objects in the return collection
	 * @param <T3> any type that extends T1 to no limit conversion to a single type
	 * @param <S> the type of collection that should be returned
	 * @param input `iterator` of input elements
	 * @param transformer a transformer to apply for each input element before copying to the output
	 * @param strategy a strategy determining the type of output collection
	 * @return an empty collection of type T2 or a collection of type T2 with transformed objects from the input collection
	 * @throws NullPointerException if `input`, `transformer`, or `strategy` was null
	 */
	default <T1, T2, T3 extends T1, S extends Collection<T2>> S transform(Iterator<T3> input, Transformer<T1, T2> transformer, IsCollectionStrategy<S, T2> strategy) {
		return this.transform(input, transformer, null, strategy);
	}

	/**
	 * Converts the input `iterator` to a collection applying a predicate and a transformation for each input element.
	 * @param <T1> the from/source of the transformer (also the type for the `iterator`)
	 * @param <T2> the to/target of the transformer and the type of objects in the return collection
	 * @param <T3> any type that extends T1 to no limit conversion to a single type
	 * @param <S> the type of collection that should be returned
	 * @param input `iterator` of input elements
	 * @param transformer a transformer to apply for each input element before copying to the output
	 * @param predicate a predicate to apply before transformation and copy of each input element (ignored if null)
	 * @param strategy a strategy determining the type of output collection
	 * @return an empty collection of type T2 or a collection of type T2 with transformed objects from the input collection
	 * @throws NullPointerException if `input`, `transformer`, or `strategy` was null
	 */
	default <T1, T2, T3 extends T1, S extends Collection<T2>> S transform(Iterator<T3> input, Transformer<T1, T2> transformer, Predicate<T3> predicate, IsCollectionStrategy<S, T2> strategy) {
		Validate.notNull(input);
		Validate.notNull(transformer);
		Validate.notNull(strategy);

		S ret = strategy.get();
		while(input.hasNext()){
			T3 t3 = input.next();
			if(predicate!=null && predicate.test(t3)){
				ret.add(transformer.transform(t3));
			}
			else if(predicate==null){
				ret.add(transformer.transform(t3));
			}
		}
		return ret;
	}

	/**
	 * Converts the input `array` to a collection applying a predicate and a transformation for each input element.
	 * @param <T1> the from/source of the transformer (also the type for the `array`)
	 * @param <T2> the to/target of the transformer and the type of objects in the return collection
	 * @param <T3> any type that extends T1 to no limit conversion to a single type
	 * @param <S> the type of collection that should be returned
	 * @param input `array` of input elements
	 * @param transformer a transformer to apply for each input element before copying to the output
	 * @param strategy a strategy determining the type of output collection
	 * @return an empty collection of type T2 or a collection of type T2 with transformed objects from the input collection
	 * @throws NullPointerException if `input`, `transformer`, or `strategy` was null
	 */
	default <T1, T2, T3 extends T1, S extends Collection<T2>> S transform(T3[] input, Transformer<T1, T2> transformer, IsCollectionStrategy<S, T2> strategy) {
		return this.transform(input, transformer, null, strategy);
	}

	/**
	 * Converts the input `array` to a collection applying a predicate and a transformation for each input element.
	 * @param <T1> the from/source of the transformer (also the type for the `array`)
	 * @param <T2> the to/target of the transformer and the type of objects in the return collection
	 * @param <T3> any type that extends T1 to no limit conversion to a single type
	 * @param <S> the type of collection that should be returned
	 * @param input `array` of input elements
	 * @param transformer a transformer to apply for each input element before copying to the output
	 * @param predicate a predicate to apply before transformation and copy of each input element (ignored if null)
	 * @param strategy a strategy determining the type of output collection
	 * @return an empty collection of type T2 or a collection of type T2 with transformed objects from the input collection
	 * @throws NullPointerException if `input`, `transformer`, or `strategy` was null
	 */
	default <T1, T2, T3 extends T1, S extends Collection<T2>> S transform(T3[] input, Transformer<T1, T2> transformer, Predicate<T3> predicate, IsCollectionStrategy<S, T2> strategy) {
		Validate.notNull(input);
		Validate.notNull(transformer);
		Validate.notNull(strategy);

		S ret = strategy.get();
		for(T3 t3 : input){
			if(predicate!=null && predicate.test(t3)){
				ret.add(transformer.transform(t3));
			}
			else if(predicate==null){
				ret.add(transformer.transform(t3));
			}
		}
		return ret;
	}

	/**
	 * Creates a new transformer.
	 * @return new transformer
	 */
	static ClusterElementTransformer create(){
		return new ClusterElementTransformer() {};
	}
}
