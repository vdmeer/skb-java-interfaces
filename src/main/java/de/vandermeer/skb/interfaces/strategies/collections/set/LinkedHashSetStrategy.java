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

package de.vandermeer.skb.interfaces.strategies.collections.set;

import java.util.Collection;
import java.util.LinkedHashSet;

import de.vandermeer.skb.interfaces.strategies.collections.IsSetStrategy;

/**
 * Strategy for a linked hash set.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface LinkedHashSetStrategy<T> extends IsSetStrategy<LinkedHashSet<T>, T> {

	@Override
	default LinkedHashSet<T> get(Collection<T> collection) {
		if(collection==null){
			return new LinkedHashSet<T>();
		}
		return new LinkedHashSet<T>(collection);
	}

	@Override
	default LinkedHashSet<T> get(){
		return new LinkedHashSet<T>();
	}

	/**
	 * Creates a new linked hash set strategy.
	 * @param <T> type for the objects in the set
	 * @return new linked hash set strategy
	 */
	static <T> LinkedHashSetStrategy<T> create(){
		return new LinkedHashSetStrategy<T>(){};
	}
}
