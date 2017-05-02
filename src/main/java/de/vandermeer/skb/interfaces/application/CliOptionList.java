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
import java.util.TreeSet;

import org.apache.commons.lang3.ClassUtils;

/**
 * A list of CLI options.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.4.0 build 170413 (13-Apr-17) for Java 1.8
 * @since      v0.0.2
 */
public interface CliOptionList {

	/**
	 * Returns all required options, taking short or long CLI argument.
	 * @param list input option list
	 * @return all required options
	 */
	static Set<String> getRequired(Set<ApoBaseC> list){
		Set<String> ret = new TreeSet<>();
		if(list!=null){
			for(ApoBaseC opt : list){
				if(opt.cliIsRequired()){
					String required = (opt.getCliShortLong()==null)?"--"+opt.getCliLong():"-"+opt.getCliShort();
					if(ClassUtils.isAssignable(opt.getClass(), Apo_TypedC.class)){
						required += " <" + ((Apo_TypedC<?>)opt).getCliArgumentName() + ">";
					}
					ret.add(required);
				}
			}
		}
		return ret;
	}

	/**
	 * Returns a sorted map of CLI options, the mapping is the sort string to the CLI option.
	 * @param list option list to sort
	 * @param numberShort number of arguments with short command
	 * @param numberLong number of arguments with long command
	 * @return sorted map
	 */
	static TreeMap<String, ApoBaseC> sortedMap(Set<ApoBaseC> list, int numberShort, int numberLong){
		TreeMap<String, ApoBaseC> ret = new TreeMap<>();
		if(list==null){
			return ret;
		}

		for(ApoBaseC opt :list){
			String key;
			if(numberShort==0){
				key = opt.getCliLong();
			}
			if(numberLong==0){
				key = opt.getCliShort().toString();
			}
			else{
				if(opt.getCliLong()!=null){
					if(opt.getCliShort()!=null){
						key = opt.getCliShort().toString() + "," + opt.getCliLong();
					}
					else{
						key = opt.getCliLong().charAt(0) +"," + opt.getCliLong();
					}
				}
				else{
					key = opt.getCliShort().toString();
				}
			}
			ret.put(key.toLowerCase(), opt);
		}
		return ret;
	}

	/**
	 * Returns a sorted collection of CLI options.
	 * @param list option list to sort
	 * @param numberShort number of arguments with short command
	 * @param numberLong number of arguments with long command
	 * @return sorted collection
	 */
	static Collection<ApoBaseC> sortedList(Set<ApoBaseC> list, int numberShort, int numberLong){
		return sortedMap(list, numberShort, numberLong).values();
	}
}
