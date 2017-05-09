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

package de.vandermeer.skb.interfaces.antlr;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.misc.STMessage;

import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.messagesets.errors.IsError;
import de.vandermeer.skb.interfaces.messagesets.errors.Templates_STG;

/**
 * An ST error listener using {@link IsError} errors.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface IsSTErrorListener extends CategoryIs, STErrorListener {

	/**
	 * Returns the errors the listener has collected.
	 * @return set of errors, ordered in time of arrival, must not be null
	 */
	Set<IsError> getErrors();

	/**
	 * Returns the application (or object/class) name the listener is running for.
	 * @return application name, must not be blank
	 */
	String getAppName();

	@Override
	default void compileTimeError(STMessage msg){
		this.getErrors().add(Templates_STG.ST_COMPILE_TIME.getError(this.getAppName(), msg));
	}

	@Override
	default void internalError(STMessage msg){
		this.getErrors().add(Templates_STG.ST_INTERNAL.getError(this.getAppName(), msg));
	}

	@Override
	default void IOError(STMessage msg){
		this.getErrors().add(Templates_STG.ST_IO.getError(this.getAppName(), msg));
	}

	@Override
	default void runTimeError(STMessage msg){
		this.getErrors().add(Templates_STG.ST_RUNTIME.getError(this.getAppName(), msg));
	}

	/**
	 * Creates a new error listener.
	 * @param appName the application (or object/class) name for the listener, must not be blank
	 * @return a new listener
	 */
	static IsSTErrorListener create(final String appName){
		Validate.notBlank(appName);

		return new IsSTErrorListener() {
			final Set<IsError> errors = new LinkedHashSet<>();

			@Override
			public Set<IsError> getErrors() {
				return errors;
			}

			@Override
			public String getAppName() {
				return appName;
			}
		};
	}
}
