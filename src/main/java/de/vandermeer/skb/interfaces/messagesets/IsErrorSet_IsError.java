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

package de.vandermeer.skb.interfaces.messagesets;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.helpers.FormattingTuple;

import de.vandermeer.skb.interfaces.messagesets.errors.IsError;

/**
 * Interface for objects that have a set of error messages of type {@link FormattingTuple}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface IsErrorSet_IsError extends IsErrorSet<IsError> {

	/**
	 * Creates a new error set.
	 * @return new error set
	 */
	static IsErrorSet_IsError create(){
		return new IsErrorSet_IsError() {
			final Set<IsError> errorSet = new LinkedHashSet<>();
			@Override
			public Set<IsError> getErrorMessages() {
				return this.errorSet;
			}
		};
	}

	/**
	 * Renders the error set.
	 * Each element in the error set is rendered in a single line.
	 * @return rendered object
	 */
	@Override
	default String render() {
		StrBuilder ret = new StrBuilder(100);
		for(IsError error : this.getErrorMessages()){
			ret.append(error.getErrorMessageString()).appendNewLine();
		}
		return ret.toString();
	}
}
