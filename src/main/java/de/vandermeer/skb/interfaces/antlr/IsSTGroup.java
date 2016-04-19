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

package de.vandermeer.skb.interfaces.antlr;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.STGroupString;

import de.vandermeer.skb.interfaces.categories.CategoryIs;

/**
 * Interface for objects that represent String Template Group (STG) objects
 * (for which one can check chunks, get name, etc).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public interface IsSTGroup extends CategoryIs {

	/**
	 * Returns the STGroup object.
	 * @return STGroup object
	 */
	STGroup getSTGroup();

	/**
	 * Returns the name of the STGroup object (file name, source name, or group directory name).
	 * @return STGroup name, null if {@link #getSTGroup()} was null or if no name was found
	 */
	default String getGroupName(){
		STGroup stg = this.getSTGroup();
		String ret = null;

		if(stg instanceof STGroupFile){
			ret = ((STGroupFile)stg).fileName;
		}
		else if(stg instanceof STGroupString){
			ret = ((STGroupString)stg).sourceName;
		}
		else if(stg instanceof STGroupDir){
			ret = ((STGroupDir)stg).groupDirName;
		}
		return (ret==null)?ret:StringUtils.substringBeforeLast(ret, ".");
	}

	/**
	 * Returns the chunks expected to be defined in the STGroup object.
	 * @return expected chunks as mapping from method name to set of method arguments
	 */
	Map<String, Set<String>> getExpectedChunks();

	/**
	 * Validates the STGroup for expected chunks.
	 * @return a set of error messages, empty if no errors found, null if no expected chunks where set or no STGroup was set
	 */
	default Set<String> validate(){
		if(this.getExpectedChunks()==null){
			return null;
		}
		if(this.getSTGroup()==null){
			return null;
		}

		STGroup stg = this.getSTGroup();
		Set<String> ret = new LinkedHashSet<>();
		for(Entry<String, Set<String>> entry : this.getExpectedChunks().entrySet()){
			if(entry.getKey()!=null && !"".equals(entry.getKey())){
				if(stg.isDefined(entry.getKey())){
					IsST isst = IsST.create(stg.getInstanceOf(entry.getKey()), entry.getValue());
					for(String s : isst.validate()){
						ret.add("STGroup <" + this.getGroupName() + ">: " + s);
					}
				}
				else{
					ret.add("STGroup <" + this.getGroupName() + "> does not define expected template <" + entry.getKey() + ">");
				}
			}
		}
		return ret;
	}
}
