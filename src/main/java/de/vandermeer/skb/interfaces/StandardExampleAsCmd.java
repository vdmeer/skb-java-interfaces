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

/**
 * A standard example with show (example output) and source (example code as quine) and provides a command.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public interface StandardExampleAsCmd extends StandardExample {

	/**
	 * Returns an identifier for the example.
	 * This is useful when a set of examples exist, and an application wants to build maps of them.
	 * @return example identifier, unique within the set of examples defined for a purpose, e.g. a package
	 */
	String getID();

	/**
	 * Returns a string that can be used as a command to execute the example.
	 * The default implementation simply uses the identifier as command string
	 * @return example command string
	 */
	default String getCmd(){
		return this.getID();
	}
}
