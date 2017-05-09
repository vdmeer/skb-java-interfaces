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

import de.vandermeer.skb.interfaces.messagesets.IsErrorSet_IsError;

/**
 * An application parser.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface ApoParser<O extends ApoBase, S extends ApoParserOptionSet<O>> {

	/**
	 * Returns the application name.
	 * @return application name, must not be null
	 */
	String getAppName();

	/**
	 * Returns the number of the last error, 0 if none occurred.
	 * @return last error number
	 */
	int getErrNo();

	/**
	 * Returns the parser's error set.
	 * @return parser's error set
	 */
	IsErrorSet_IsError getErrorSet();

	/**
	 * Returns the option set of the parser.
	 * @return parser option set, must not be null, empty if no options added
	 */
	ApoParserOptionSet<O> getOptions();

	/**
	 * Sets an error number.
	 * @param errorNumber the new number for the error
	 */
	void setErrno(int errorNumber);

}
