/* Copyright 2014 Sven van der Meer <vdmeer.sven@mykolab.com>
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

package de.vandermeer.skb.interfaces;

import org.apache.commons.lang3.Validate;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * Wraps a formatting tuple object from SLF4J simplifying messages.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public interface FormattingTupleWrapper {

	/**
	 * Returns the set formatting tuple.
	 * @return formatting tuple
	 */
	FormattingTuple getTuple();

	/**
	 * Returns the formatted (rendered) message using the set formatting tuple.
	 * @return formatted (rendered) message
	 */
	default String getMessage(){
		return this.getTuple().getMessage();
	}

	/**
	 * Creates a new wrapper.
	 * @param msg the message for the wrapper, should not be blank
	 * @return new wrapper with given message
	 * @throws NullPointerException if `msg` was null
	 * @throws IllegalArgumentException if `msg` was blank
	 */
	static FormattingTupleWrapper create(final String msg){
		Validate.notBlank(msg);

		return new FormattingTupleWrapper() {
			@Override
			public FormattingTuple getTuple() {
				return MessageFormatter.arrayFormat(msg, new Object[0]);
			}
		};
	}

	/**
	 * Creates a new wrapper.
	 * @param msg the message for the wrapper, should not be blank
	 * @param obj the elements for the message, should not be null and have no null elements
	 * @return new wrapper with given message
	 * @throws NullPointerException if `msg` or `obj` was null
	 * @throws IllegalArgumentException if `msg` was blank or `obj` contained null elements
	 */
	static FormattingTupleWrapper create(final String msg, final Object ... obj){
		Validate.notBlank(msg);
		Validate.notNull(obj);
		Validate.noNullElements(obj);

		return new FormattingTupleWrapper() {
			@Override
			public FormattingTuple getTuple() {
				return MessageFormatter.arrayFormat(msg, obj);
			}
		};
	}

}
