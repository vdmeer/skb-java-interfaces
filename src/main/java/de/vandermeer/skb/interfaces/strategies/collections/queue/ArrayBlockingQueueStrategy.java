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

package de.vandermeer.skb.interfaces.strategies.collections.queue;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.commons.lang3.NotImplementedException;

import de.vandermeer.skb.interfaces.strategies.collections.IsQueueStrategy;

/**
 * Strategy for an array blocking queue.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface ArrayBlockingQueueStrategy<T> extends IsQueueStrategy<ArrayBlockingQueue<T>, T> {

	@Override
	default ArrayBlockingQueue<T> get(Collection<T> collection) {
		throw new NotImplementedException("cannot implement get(collection) on array blocking queue w/o capacity, see interface for alternative");
	}

	@Override
	default ArrayBlockingQueue<T> get() {
		throw new NotImplementedException("cannot implement get() on array blocking queue w/o capacity, see interface for alternative");
	}

	/**
	 * Returns the capacity of the strategy.
	 * @return capacity, all created array blocking queues will have this capacity set
	 */
	int getCapacity();

	/**
	 * Returns a new collection for the given collection.
	 * @param capacity the queue's capacity
	 * @param collection input collection
	 * @return new collection
	 */
	default ArrayBlockingQueue<T> get(int capacity, Collection<T> collection) {
		ArrayBlockingQueue<T> ret = new ArrayBlockingQueue<T>(capacity);
		if(collection!=null){
			ret.addAll(collection);
		}
		return ret;
	}

	/**
	 * Returns a new collection for the given collection.
	 * @param capacity the queue's capacity
	 * @return new collection
	 */
	default ArrayBlockingQueue<T> get(int capacity) {
		return new ArrayBlockingQueue<T>(capacity);
	}

	/**
	 * Creates a new array blocking queue strategy (as queue).
	 * @param <T> type for the objects in the queue
	 * @param capacity the queue's capacity
	 * @return new array blocking queue strategy
	 */
	static <T> ArrayBlockingQueueStrategy<T> create(final int capacity){
		return new ArrayBlockingQueueStrategy<T>(){
			@Override
			public int getCapacity(){
				return capacity;
			}
		};
	}
}
