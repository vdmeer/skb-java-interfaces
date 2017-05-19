/* Copyright 2017 Sven van der Meer <vdmeer.sven@mykolab.com>
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

package de.vandermeer.skb.interfaces.messages.errors;

import org.apache.commons.lang3.Validate;
import org.slf4j.helpers.FormattingTuple;

import de.vandermeer.skb.interfaces.categories.has.HasErrNo;
import de.vandermeer.skb.interfaces.render.DoesRender;

/**
 * An error object with error number and message.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface IsError extends HasErrNo, DoesRender {

	/**
	 * Creates a new error object.
	 * @param errNo the error number
	 * @param category the error category, must not null
	 * @param message the error message, must not be null
	 * @return
	 */
	static IsError create(final int errNo, final IsErrorCategory category, final FormattingTuple message){
		Validate.notNull(category);
		Validate.notNull(message);

		return new IsError() {
			@Override
			public int getErrNo() {
				return errNo;
			}

			@Override
			public FormattingTuple getErrorMessage() {
				return message;
			}

			@Override
			public IsErrorCategory getCategory() {
				return category;
			}
		};
	}

	/**
	 * Returns the category of the error.
	 * @return error category
	 */
	IsErrorCategory getCategory();

	/**
	 * Returns the error message as a formatting tuple, not yet rendered.
	 * @return error message as a formatting tuple, must not be null
	 */
	FormattingTuple getErrorMessage();

	@Override
	default String render(){
		return this.getErrorMessage().getMessage();
	}

}
