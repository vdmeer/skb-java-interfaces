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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.categories.has.HasDescription;
import de.vandermeer.skb.interfaces.categories.has.HasDisplayName;

/**
 * An application error code.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface IsErrorTemplate extends HasDisplayName, HasDescription, CategoryIs {

	/**
	 * Validates an array of templates.
	 * @param templates the templates to validate, must not be null or have null elements
	 * @throws NullPointerException if parameter was null
	 * @throws IllegalArgumentException if parameter had null elements
	 * @throws IllegalStateException if any validation error was detected
	 */
	static void validate(IsErrorTemplate[] templates){
		Validate.noNullElements(templates, "The array contain null at position %d");
		Map<Integer, String> inuse = new HashMap<>();
		for(IsErrorTemplate tpl : templates){
			Validate.notNull(tpl.getCategory());
			Validate.inclusiveBetween(tpl.getCategory().getEnd(), tpl.getCategory().getStart(), tpl.getCode());
			Validate.notBlank(tpl.getMessage());
			Validate.notBlank(tpl.getDescription());
			Validate.notBlank(tpl.getDisplayName());
			Validate.isTrue(!inuse.containsKey(tpl.getCode()), tpl.getCode() + " == " + inuse.get(tpl.getCode()) + " ==> " + tpl.getDisplayName());
			inuse.put(tpl.getCode(), tpl.getDisplayName());
		}
	}

	/**
	 * Returns the number of expected arguments.
	 * @return number of expected arguments
	 */
	default int expectedArguments(){
		return StringUtils.countMatches(this.getMessage(), "{}");
	}

	/**
	 * Returns the category of the error.
	 * @return error category
	 */
	IsErrorCategory getCategory();

	/**
	 * Returns the error code.
	 * @return error code
	 */
	int getCode();

	/**
	 * Creates a new error message for errors without arguments.
	 * @return new error message
	 */
	default IsError getError(){
		return this.getError(new Object[0]);
	}

	/**
	 * Creates a new error message with arguments.
	 * The length of `arguments` must be equal to the number of expected arguments of this error code.
	 * If the number of expected arguments is 0, then `args` can be null or of length 0.
	 * @param args the arguments, must not be null and have no null elements, must have same length as expected arguments -1 for application name
	 * @return error message
	 */
	default IsError getError(final Object ... args){
		Validate.noNullElements(args);
		Validate.validState(args.length==this.expectedArguments());

		return new IsError() {
			@Override
			public IsErrorCategory getCategory() {
				return getCategory();
			}

			@Override
			public int getErrNo() {
				return getCode();
			}

			@Override
			public FormattingTuple getErrorMessage() {
				return MessageFormatter.arrayFormat(getMessage(), args);
			}
		};

	}

	/**
	 * Returns the error message.
	 * @return error message
	 */
	String getMessage();

	/**
	 * Tests if the error requires arguments for the error message.
	 * @return true if arguments are requires, false otherwise
	 */
	default boolean requiresArguments(){
		return this.expectedArguments()!=0;
	}

}
