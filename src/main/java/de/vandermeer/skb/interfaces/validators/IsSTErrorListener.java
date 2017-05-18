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

import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.misc.STMessage;

import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.messages.errors.IsError;
import de.vandermeer.skb.interfaces.messages.errors.Templates_STG;
import de.vandermeer.skb.interfaces.messages.sets.HasErrorSet;
import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;

/**
 * An ST error listener using {@link IsError} errors.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface IsSTErrorListener extends CategoryIs, STErrorListener, HasErrorSet {

	/**
	 * Creates a new error listener.
	 * @return a new listener
	 */
	static IsSTErrorListener create(){
		return new IsSTErrorListener() {
			final IsErrorSet errors = IsErrorSet.create();

			@Override
			public IsErrorSet getErrorSet() {
				return errors;
			}
		};
	}

	@Override
	default void compileTimeError(STMessage msg){
		this.getErrorSet().add(Templates_STG.ST_COMPILE_TIME.getError(msg));
	}

	@Override
	default void internalError(STMessage msg){
		this.getErrorSet().add(Templates_STG.ST_INTERNAL.getError(msg));
	}

	@Override
	default void IOError(STMessage msg){
		this.getErrorSet().add(Templates_STG.ST_IO.getError(msg));
	}

	@Override
	default void runTimeError(STMessage msg){
		this.getErrorSet().add(Templates_STG.ST_RUNTIME.getError(msg));
	}
}
