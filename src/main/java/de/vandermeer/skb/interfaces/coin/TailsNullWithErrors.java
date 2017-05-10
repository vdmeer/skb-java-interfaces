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
 * A tails (error) coin without a value but some errors.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface TailsNullWithErrors extends TailsNull, IsErrorSet {

	/**
	 * Creates a new null coin with given value and errors.
	 * @param <M> the message type for the set
	 * @return new error coin
	 */
	static <M> TailsNullWithErrors create(){
		return new TailsNullWithErrors() {
			final Set<DoesRender> errorSet = new LinkedHashSet<>();

			@Override
			public Set<DoesRender> getMessages() {
				return this.errorSet;
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
