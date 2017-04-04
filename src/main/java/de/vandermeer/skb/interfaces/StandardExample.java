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

package de.vandermeer.skb.interfaces;

import org.apache.commons.lang3.text.StrBuilder;

/**
 * A standard example with show (example output) and source (example code as quine).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public interface StandardExample {

	/**
	 * Returns a short description for the example
	 * @return short example description
	 */
	String getDescription();

	/**
	 * Shows the output of the example.
	 */
	void showOutput();

	/**
	 * Returns the original source code of the example.
	 * @return source code
	 */
	StrBuilder getSource();
}
