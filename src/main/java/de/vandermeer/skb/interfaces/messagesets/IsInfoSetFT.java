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

package de.vandermeer.skb.interfaces.messagesets;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * Interface for objects that have a set of info messages.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public interface IsInfoSetFT extends IsInfoSet<FormattingTuple> {

	/**
	 * Adds all infos from given info set.
	 * @param infos infos to add
	 */
	default void addAllInfos(IsInfoSetFT infos){
		this.getInfoMessages().addAll(infos.getInfoMessages());
	}

	/**
	 * Adds a new info.
	 * @param info the info message, should not be blank
	 * @throws NullPointerException if `info` was null
	 * @throws IllegalArgumentException if `info` was blank
	 */
	default void addInfo(String info){
		Validate.notBlank(info);
		this.addInfo(MessageFormatter.arrayFormat(info, new Object[0]));
	}

	/**
	 * Adds a new info.
	 * @param info the info message, should not be blank
	 * @param obj the elements for the info message, should not be null and have no null elements
	 * @throws NullPointerException if `info` or `obj` was null
	 * @throws IllegalArgumentException if `info` was blank or `obj` contained null elements
	 */
	default void addInfo(String info, Object ... obj){
		Validate.notBlank(info);
		Validate.notNull(obj);
		Validate.noNullElements(obj);
		this.addInfo(MessageFormatter.arrayFormat(info, obj));
	}

	/**
	 * Renders the info set.
	 * Each element in the info set is rendered in a single line.
	 * @return rendered object
	 */
	@Override
	default String render() {
		StrBuilder ret = new StrBuilder(100);
		for(FormattingTuple ft : this.getInfoMessages()){
			ret.append(ft.getMessage()).appendNewLine();
		}
		return ret.toString();
	}

	/**
	 * Creates a new info set.
	 * @return new info set
	 */
	static IsInfoSetFT create(){
		return new IsInfoSetFT() {
			final Set<FormattingTuple> infoSet = new LinkedHashSet<>();
			@Override
			public Set<FormattingTuple> getInfoMessages() {
				return this.infoSet;
			}
		};
	}
}
