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

import de.vandermeer.skb.interfaces.categories.CategoryIs;

/**
 * Category of objects that represent a transformer array.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface IsTransformerArray<FROM, TO> extends TransformerArray<FROM, TO>, CategoryIs {

	/**
	 * Creates a new transformer array with {@link IsTransformer} transformers.
	 * @param transformers the transformers
	 * @param <FROM> type of the source (from)
	 * @param <TO> type of the target (to)
	 * @return new transformer array
	 */
	@SafeVarargs
	static <FROM, TO> IsTransformerArray<FROM, TO> create(final IsTransformer<FROM, TO> ... transformers){
		return new IsTransformerArray<FROM, TO>() {
			@Override
			public Transformer<FROM, TO>[] getTransformers() {
				return transformers;
			}
		};
	}
}
