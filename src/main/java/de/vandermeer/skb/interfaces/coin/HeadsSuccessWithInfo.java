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

import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.interfaces.messagesets.IsInfoSet;
import de.vandermeer.skb.interfaces.render.DoesRender;

/**
 * A heads (success) coin with a value and further information.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public interface HeadsSuccessWithInfo<R, M> extends HeadsSuccess<R>, IsInfoSet<M> {

	@Override
	default boolean reportsInfo(){
		return true;
	}

	@Override
	default boolean hasInfoReports(){
		return this.hasInformation();
	}

	/**
	 * Renders the info set.
	 * The method uses {@link DoesRender} or simple toString to render infos.
	 * Each element in the information set is rendered in a single line, preceded by the type (info).
	 * @return rendered object
	 */
	@Override
	default String render() {
		StrBuilder ret = new StrBuilder(100);
		for(M m : this.getInfoMessages()){
			ret.append("info: ");
			if(m instanceof DoesRender){
				ret.append(((DoesRender)m).render());
			}
			else{
				ret.append(m);
			}
			ret.appendNewLine();
		}
		return ret.toString();
	}

	/**
	 * Creates a new success coin with given value and information.
	 * @param <R> type of the return value
	 * @param <M> the message type for the set
	 * @param value the actual return value
	 * @return new success coin
	 */
	static <R, M> HeadsSuccessWithInfo<R, M> create(final R value){
		return new HeadsSuccessWithInfo<R, M>() {
			final Set<M> infoSet = new LinkedHashSet<>();

			@Override
			public R getReturn() {
				return value;
			}

			@Override
			public Set<M> getInfoMessages() {
				return this.infoSet;
			}
		};
	}
}
