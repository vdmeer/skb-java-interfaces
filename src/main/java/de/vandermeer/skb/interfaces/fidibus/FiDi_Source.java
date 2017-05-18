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

package de.vandermeer.skb.interfaces.fidibus;

/**
 * Source of information.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface FiDi_Source extends FiDi_Role {

	/**
	 * Returns the actual source.
	 * For instance, if the source is a path in the file system this method should return the path.
	 * @return object representation of the source
	 */
	Object getSource();

	/**
	 * Validates the source against various options.
	 * @return true if source is valid, false otherwise
	 */
	boolean validateSource();

	/**
	 * Flag to test for previous validations avoiding multiple validations
	 * @return true if target has been validated, false otherwise
	 */
	boolean isValidated();

	/**
	 * Flag for a valid source, has been validated without errors.
	 * @return true if valid, false otherwise
	 */
	default boolean isValid(){
		return this.isValidated() && !this.getErrorSet().hasMessages();
	}

}
