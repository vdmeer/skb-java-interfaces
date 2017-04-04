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

import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang3.Validate;

/**
 * Standard transformer transforming FROM to TO with or with a predicate.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public interface Transformer <FROM, TO> extends Function<FROM, TO> {

	/**
	 * Transforms from one representation to another.
	 * @param from input representation
	 * @return output representation or null if input was null or unexpected class
	 * @throws NullPointerException if the argument was null
	 */
	default TO transform(FROM from){
		Validate.notNull(from);
		return null;
	}

	@Override
	default TO apply(FROM from){
		return transform(from);
	}

	/**
	 * Transforms from one representation to another if a given predicate tests true.
	 * @param from input representation
	 * @param predicate predicate to apply to input before transformation
	 * @return output representation or null if input was null or unexpected class
	 * @throws NullPointerException if any argument was null
	 */
	default TO transform(FROM from, Predicate<FROM> predicate){
		Validate.notNull(from);
		Validate.notNull(predicate);

		if(predicate.test(from)){
			return this.transform(from);
		}
		return null;
	}
}
