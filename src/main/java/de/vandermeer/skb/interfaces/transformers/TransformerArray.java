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

import org.apache.commons.lang3.Validate;

/**
 * A transformer that uses an array of transformers for transformation,
 * first transformer that does something matches.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface TransformerArray<FROM, TO> extends Transformer <FROM, TO> {

	/**
	 * Creates a new transformer array.
	 * @param <FROM> type of the source (from)
	 * @param <TO> type of the target (to)
	 * @param transformers the array of transformers
	 * @return new transformer
	 * @throws NullPointerException if the argument was null or had any null element
	 */
	static <FROM, TO> TransformerArray<FROM, TO> create(final Transformer<FROM, TO>[] transformers){
		return new TransformerArray<FROM, TO>() {
			@Override
			public Transformer<FROM, TO>[] getTransformers() {
				Validate.notNull(transformers);
				Validate.noNullElements(transformers);
				return transformers;
			}
		};
	}

	@Override
	default TO apply(FROM f){
		return transform(f);
	}

	/**
	 * Returns the array of transformers used for transformations.
	 * @return array of transformers
	 */
	Transformer<FROM, TO>[] getTransformers();

	/**
	 * Transforms from one representation to another using an array of potential transformers.
	 * The first transformer that does not throw an exception and that does not return null will be used.
	 * Last resort, if no transformer provides the new transformation, is to throw an exception.
	 * @param from input representation
	 * @return output representation or null if input was null or unexpected class
	 * @throws NullPointerException if an argument was null
	 * @throws IllegalArgumentException if an argument had null elements or if no transformer could perform a non-null transformation
	 */
	default TO transform(FROM from){
		Validate.notNull(from);
		Validate.notNull(this.getTransformers());
		Validate.noNullElements(this.getTransformers());

		TO ret = null;
		for(Transformer<FROM, TO> tf : this.getTransformers()){
			try{
				ret = tf.transform(from);
			}
			catch(Exception ignore){}
			if(ret!=null){
				return ret;
			}
		}
		throw new IllegalArgumentException("none of the transformers in the array could do a non-null transformation");
	}
}