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

import de.vandermeer.skb.interfaces.messages.errors.IsErrorTemplate;

/**
 * General application exception.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.2
 */
public class ApplicationException extends Exception {

	/** Serial version UID. */
	private static final long serialVersionUID = 8635326284951337092L;

	/** The exception error code. */
	private final int errorCode;

	/**
	 * Creates a new exception with message and error code.
	 * @param errorCode the error code as return value for an application
	 * @param message a message for the exception
	 */
	public ApplicationException(final int errorCode, final String message){
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * Creates a new exception.
	 * @param template the exception error template
	 * @param appName the application name for the error message
	 * @param objects the objects required to create the error message for the template
	 */
	public ApplicationException(IsErrorTemplate template, String appName, Object ... objects){
		super(template.getError(appName, objects).getErrorMessage().getMessage());
		this.errorCode = template.getCode();
	}

	/**
	 * Returns the error code.
	 * @return error code
	 */
	public int getErrorCode(){
		return this.errorCode;
	}

}
