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

package de.vandermeer.skb.interfaces.coin;

/**
 * A tails (error) coin with a value.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public interface TailsError<R> extends TailsCoin<R> {

	/**
	 * Creates a new error coin with given value.
	 * @param <R> type of the return value
	 * @param value the actual return value
	 * @return new error coin
	 */
	static <R> TailsError<R> create(final R value){
		return new TailsError<R>() {
			@Override
			public R getReturn() {
				return value;
			}
		};
	}
}
