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
import java.util.TreeSet;

import de.vandermeer.skb.interfaces.strategies.collections.IsSortedSetStrategy;

/**
 * Strategy for a tree set.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public interface TreeSetStrategy<T extends Comparable<T>> extends IsSortedSetStrategy<TreeSet<T>, T> {

	@Override
	default TreeSet<T> get(Collection<T> collection) {
		if(collection==null){
			return new TreeSet<T>();
		}
		TreeSet<T> ret = new TreeSet<T>();
		ret.addAll(collection);
		return ret;
	}

	@Override
	default TreeSet<T> get(Collection<T> collection, Comparator<T> comparator) {
		TreeSet<T> ret;
		if(collection==null){
			return new TreeSet<T>(comparator);
		}
		ret = new TreeSet<T>();
		ret.addAll(collection);
		return ret;
	}

	@Override
	default TreeSet<T> get(){
		return new TreeSet<T>();
	}

	@Override
	default TreeSet<T> get(Comparator<T> comparator) {
		return new TreeSet<T>(comparator);
	}

	/**
	 * Creates a new tree set strategy.
	 * @param <T> type for the objects in the sorted set
	 * @return new tree set strategy
	 */
	static <T extends Comparable<T>> TreeSetStrategy<T> create(){
		return new TreeSetStrategy<T>(){};
	}
}
