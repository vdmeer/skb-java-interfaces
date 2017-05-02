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
 * Interface for objects that have a set of warning messages.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface IsWarningSetFT extends IsWarningSet<FormattingTuple> {

	/**
	 * Adds all warnings from given warning set.
	 * @param warnings warnings to add
	 */
	default void addAllWarnings(IsWarningSetFT warnings){
		this.getWarningMessages().addAll(warnings.getWarningMessages());
	}

	/**
	 * Adds a new warning.
	 * @param warning the warning message, should not be blank
	 * @throws NullPointerException if `warning` was null
	 * @throws IllegalArgumentException if `warning` was blank
	 */
	default void addError(String warning){
		Validate.notBlank(warning);
		this.addWarning(MessageFormatter.arrayFormat(warning, new Object[0]));
	}

	/**
	 * Adds a new warning.
	 * @param warning the warning message, should not be blank
	 * @param obj the elements for the warning message, should not be null and have no null elements
	 * @throws NullPointerException if `warning` or `obj` was null
	 * @throws IllegalArgumentException if `warning` was blank or `obj` contained null elements
	 */
	default void addError(String warning, Object ... obj){
		Validate.notBlank(warning);
		Validate.notNull(obj);
		Validate.noNullElements(obj);
		this.addWarning(MessageFormatter.arrayFormat(warning, obj));
	}

	/**
	 * Renders the warning set.
	 * Each element in the warning set is rendered in a single line.
	 * @return rendered object
	 */
	@Override
	default String render() {
		StrBuilder ret = new StrBuilder(100);
		for(FormattingTuple ft : this.getWarningMessages()){
			ret.append(ft.getMessage()).appendNewLine();
		}
		return ret.toString();
	}

	/**
	 * Creates a new warning set.
	 * @return new warning set
	 */
	static IsWarningSetFT create(){
		return new IsWarningSetFT() {
			final Set<FormattingTuple> warningSet = new LinkedHashSet<>();
			@Override
			public Set<FormattingTuple> getWarningMessages() {
				return this.warningSet;
			}
		};
	}
}
