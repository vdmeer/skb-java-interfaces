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

package de.vandermeer.skb.interfaces.strategies.collections.sortedset;

import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListSet;

import de.vandermeer.skb.interfaces.strategies.collections.IsSortedSetStrategy;

/**
 * Strategy for a concurrent skip set.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface ConcurrentSkipSetStrategy<T extends Comparable<T>> extends IsSortedSetStrategy<ConcurrentSkipListSet<T>, T> {

	@Override
	default ConcurrentSkipListSet<T> get(Collection<T> collection) {
		return new ConcurrentSkipListSet<T>(collection);
	}

	@Override
	default ConcurrentSkipListSet<T> get(Collection<T> collection, Comparator<T> comparator) {
		ConcurrentSkipListSet<T> ret;
		ret = new ConcurrentSkipListSet<T>(comparator);
		ret.addAll(collection);
		return ret;
	}

	@Override
	default ConcurrentSkipListSet<T> get(){
		return new ConcurrentSkipListSet<T>();
	}

	@Override
	default ConcurrentSkipListSet<T> get(Comparator<T> comparator) {
		return new ConcurrentSkipListSet<T>(comparator);
	}

	/**
	 * Creates a new concurrent skip set strategy.
	 * @param <T> type for the objects in the sorted set
	 * @return new concurrent skip set strategy
	 */
	static <T extends Comparable<T>> ConcurrentSkipSetStrategy<T> create(){
		return new ConcurrentSkipSetStrategy<T>(){};
	}
}
