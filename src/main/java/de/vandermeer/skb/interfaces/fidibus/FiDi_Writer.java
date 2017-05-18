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
 * An information writer, writing information to a target.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface FiDi_Writer extends FiDi_Role {

	/**
	 * Returns the target set for the writer, that is where the writer writes information to.
	 * @return target set for the writer
	 */
	FiDi_Target getTarget();

	/**
	 * Writes information to a target.
	 * @param content the content to be written
	 * @return true on success, false on error
	 */
	boolean write(Object content);

	/**
	 * Validates the writers target.
	 * @return true if the source is valid (not null and getSource not null), false otherwise
	 */
	default boolean validateTarget(){
		if(this.getTarget()==null){
			this.getErrorSet().add("FiDi writer: target is null");
			return false;
		}
		if(!this.getTarget().isValidated()){
			this.getTarget().validateTarget();
		}
		if(!this.getTarget().isValid()){
			this.getErrorSet().add("FiDi writer: error validating target <{}>", this.getTarget().getDisplayName());
			this.getErrorSet().addAll(this.getTarget().getErrorSet());
			return false;
		}
		return true;
	}

	/**
	 * Tests the writer for errors.
	 * @return true if it has errors, false otherwise
	 */
	default boolean hasErrors(){
		return this.getErrorSet().hasMessages();
	}
}
