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
 * An information loader / reader, loading information from source.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface FiDi_Loader extends FiDi_Role {

	/**
	 * Returns the source set for the reader, that is where the reader loads information from.
	 * @return source set for the reader
	 */
	FiDi_Source getSource();

	/**
	 * Reads information from a source and returns a specific object.
	 * @return an object with the information loaded
	 */
	Object read();

	/**
	 * Validates the readers source.
	 * @return true if the source is valid (not null and getSource not null), false otherwise
	 */
	default boolean validateSource(){
		if(this.getSource()==null){
			this.getErrorSet().add("FiDi loader - source is null");
			return false;
		}
		if(!this.getSource().isValidated()){
			this.getSource().validateSource();
		}
		if(!this.getSource().isValid()){
			this.getErrorSet().add("FiDi loader: error validating source <{}>", this.getSource().getDisplayName());
			this.getErrorSet().addAll(this.getSource().getErrorSet());
			return false;
		}
		return true;
	}

	/**
	 * Tests the loader for errors.
	 * @return true if it has errors, false otherwise
	 */
	default boolean hasErrors(){
		return this.getErrorSet().hasMessages();
	}
}
