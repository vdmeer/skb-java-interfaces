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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.Validate;

/**
 * Environment option set.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface ApoEnvOptions extends ApoParserOptionSet<Apo_TypedE<?>> {

	/**
	 * Creates a new set for environment options.
	 * @return new environment option set
	 */
	static ApoEnvOptions create(){
		return new ApoEnvOptions() {
			protected final transient Map<String, Apo_TypedE<?>> options = new HashMap<>();

			@Override
			public Map<String, Apo_TypedE<?>> getMap() {
				return options;
			}

			@Override
			public ApoEnvOptions addOption(Object option) throws IllegalStateException {
				if(option==null){
					return this;
				}
				if(ClassUtils.isAssignable(option.getClass(), Apo_TypedE.class)){
					Apo_TypedE<?> eo = (Apo_TypedE<?>)option;
					Validate.validState(
							!this.getKeys().contains(eo.getEnvironmentKey()),
							this.getClass().getSimpleName() + ": environment option <" + eo.getEnvironmentKey() + "> already in use"
					);
					this.getMap().put(eo.getEnvironmentKey(), eo);
				}
				return this;
			}
		};
	}

}
