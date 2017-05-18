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

package de.vandermeer.skb.interfaces.validators;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.compiler.FormalArgument;

import de.vandermeer.skb.interfaces.messages.errors.Templates_ST;
import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;

/**
 * Validates a String Template.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface STValidator extends IsValidator {

	/**
	 * Returns a new ST validator
	 * @return new validator
	 */
	static STValidator create(){
		return new STValidator() {
			protected IsErrorSet errors = IsErrorSet.create();

			@Override
			public IsErrorSet getErrorSet() {
				return this.errors;
			}

			@Override
			public String getDescription() {
				return "Validates a string template ST for expected arguments";
			}
		};
	}

	/**
	 * Validates a String Template.
	 * @param st the template, must not be null
	 * @param expectedArguments the expected arguments, must not contain null elements
	 * @return true on success, false on error
	 */
	default boolean validate(ST st, String[] expectedArguments){
		return this.validate(st, expectedArguments, false);
	}

	/**
	 * Validates a String Template.
	 * @param st the template, must not be null
	 * @param expectedArguments the expected arguments, must not contain null elements
	 * @param extraIsError true for extra (additional) arguments being treated as an error, false otherwise
	 * @return true on success, false on error
	 */
	default boolean validate(ST st, String[] expectedArguments, boolean extraIsError){
		this.getErrorSet().clearMessages();

		if(st==null){
			this.getErrorSet().add(Templates_ST.ST_NULL.getError());
			return false;
		}

		Validate.noNullElements(expectedArguments);

		Map<String, FormalArgument> formalArgs = st.impl.formalArguments;
		if(formalArgs==null){
			for(String s : expectedArguments){
				this.getErrorSet().add(Templates_ST.MISSING_EXPECTED_ARGUMENT.getError(st.getName(), s));
			}
		}
		else{
			for(String s : expectedArguments){
				if(!formalArgs.containsKey(s)){
					this.getErrorSet().add(Templates_ST.MISSING_EXPECTED_ARGUMENT.getError(st.getName(), s));
				}
			}
			if(extraIsError){
				for(String s : formalArgs.keySet()){
					if(ArrayUtils.contains(expectedArguments, s)){
						this.getErrorSet().add(Templates_ST.EXTRA_ARGUMENT.getError(st.getName(), s));
					}
				}
			}
		}
		return !this.getErrorSet().hasMessages();
	}
}
