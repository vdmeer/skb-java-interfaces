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

import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;

/**
 * A list of environment options.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.2
 */
public interface EnvironmentOptionsList {

	/**
	 * Returns a mapping of comparison strings to typed environment options, a sorted map
	 * @param set environment options
	 * @return sorted map
	 */
	static TreeMap<String, Apo_TypedE<?>> sortedMap(Set<Apo_TypedE<?>> set){
		TreeMap<String, Apo_TypedE<?>> ret = new TreeMap<>();
		if(set!=null){
			for(Apo_TypedE<?> te : set){
				ret.put(te.getEnvironmentKey().toLowerCase(), te);
			}
		}
		return ret;
	}

	/**
	 * Returns a sorted collection of environment options.
	 * @param set option set to sort
	 * @return sorted collection
	 */
	static Collection<Apo_TypedE<?>> sortedList(Set<Apo_TypedE<?>> set){
		return sortedMap(set).values();
	}

}
