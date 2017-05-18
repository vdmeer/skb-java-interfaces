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

import java.util.LinkedHashSet;
import java.util.Set;

import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;
import de.vandermeer.skb.interfaces.render.DoesRender;

/**
 * A tails (error) coin with a value and errors.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface TailsErrorWithErrors<R> extends TailsError<R>, IsErrorSet {

	/**
	 * Creates a new error coin with given value and errors.
	 * @param <R> type of the return value
	 * @param value the actual return value
	 * @return new error coin
	 */
	static <R> TailsErrorWithErrors<R> create(final R value){
		return new TailsErrorWithErrors<R>() {
			final protected Set<DoesRender> errorSet = new LinkedHashSet<>();
			protected int errno;

			@Override
			public Set<DoesRender> getMessages() {
				return this.errorSet;
			}

			@Override
			public R getReturn() {
				return value;
			}

			@Override
			public void setErrNo(int number) {
				this.errno = number;
			}

			@Override
			public int getErrNo() {
				return this.errno;
			}
		};
	}

	@Override
	default boolean hasErrorReports(){
		return this.hasMessages();
	}

	@Override
	default boolean reportsErrors(){
		return true;
	}
}
