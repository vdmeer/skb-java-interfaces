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
 * Interface for objects that have a set of error messages of type {@link FormattingTuple}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public interface IsErrorSetFT extends IsErrorSet<FormattingTuple> {

	/**
	 * Adds all errors from given error set.
	 * @param errors errors to add
	 */
	default void addAllErrors(IsErrorSetFT errors){
		this.getErrorMessages().addAll(errors.getErrorMessages());
	}

	/**
	 * Adds a new error.
	 * @param error the error message, should not be blank
	 * @throws NullPointerException if `error` was null
	 * @throws IllegalArgumentException if `error` was blank
	 */
	default void addError(String error){
		Validate.notBlank(error);
		this.addError(MessageFormatter.arrayFormat(error, new Object[0]));
	}

	/**
	 * Adds a new error.
	 * @param error the error message, should not be blank
	 * @param obj the elements for the error message, should not be null and have no null elements
	 * @throws NullPointerException if `error` or `obj` was null
	 * @throws IllegalArgumentException if `error` was blank or `obj` contained null elements
	 */
	default void addError(String error, Object ... obj){
		Validate.notBlank(error);
		Validate.notNull(obj);
		Validate.noNullElements(obj);
		this.addError(MessageFormatter.arrayFormat(error, obj));
	}

	/**
	 * Renders the error set.
	 * Each element in the error set is rendered in a single line.
	 * @return rendered object
	 */
	@Override
	default String render() {
		StrBuilder ret = new StrBuilder(100);
		for(FormattingTuple ft : this.getErrorMessages()){
			ret.append(ft.getMessage()).appendNewLine();
		}
		return ret.toString();
	}

	/**
	 * Creates a new error set.
	 * @return new error set
	 */
	static IsErrorSetFT create(){
		return new IsErrorSetFT() {
			final Set<FormattingTuple> errorSet = new LinkedHashSet<>();
			@Override
			public Set<FormattingTuple> getErrorMessages() {
				return this.errorSet;
			}
		};
	}
}
