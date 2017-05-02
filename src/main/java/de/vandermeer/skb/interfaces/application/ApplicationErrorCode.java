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

package de.vandermeer.skb.interfaces.application;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.interfaces.categories.has.HasDescription;

/**
 * An application error code.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.2
 */
public interface ApplicationErrorCode extends HasDescription {

	/**
	 * Returns the category of the error code.
	 * @return error code category
	 */
	ErrorCodeCategory getCategory();

	/**
	 * Returns the error code.
	 * @return error code
	 */
	int getCode();

	/**
	 * Returns the number of expected arguments.
	 * @return number of expected arguments
	 */
	int getArgs();

	/**
	 * Returns the error message.
	 * @return error message
	 */
	String getMessage();

	/**
	 * Creates a new error message with all arguments substituted.
	 * The length of `arguments` must be equal to the number of expected arguments of this error code.
	 * If the number of expected arguments is 0, then `args` can be null or of length 0.
	 * @param args the arguments, must have same length as expected arguments
	 * @return new error message
	 */
	default String getMessage(final Object ... args){
		if(this.getArgs()>0){
			Validate.noNullElements(args);
			Validate.validState(args.length==this.getArgs());
		}
		else{
			Validate.validState(args==null || args.length==0);
			return this.getMessage();
		}

		final StrBuilder ret = new StrBuilder().append(this.getMessage());
		for(final Object arg : args){
			ret.replaceFirst("{}", arg.toString());
		}
		return ret.toString();
	}

}
