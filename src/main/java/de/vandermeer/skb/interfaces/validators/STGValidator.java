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

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.STGroupString;

import de.vandermeer.skb.interfaces.messages.errors.Templates_STG;
import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;

/**
 * Validates a String Template Group.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface STGValidator extends IsValidator {

	static STGValidator create(){
		return new STGValidator() {
			protected IsErrorSet errors = IsErrorSet.create();

			@Override
			public IsErrorSet getErrorSet() {
				return this.errors;
			}

			@Override
			public String getDescription() {
				return "Validates a string template STG for expected arguments";
			}
		};
	}

	/**
	 * Validates an STG, extra chunks are not treated as an error.
	 * @param stg the STG, should not be null.
	 * @param expectedChunks the expected chunks, should not be null nor have null members
	 * @return true on success, false on error
	 */
	default boolean validate(final STGroup stg, final Map<String, String[]> expectedChunks){
		return this.validate(stg, expectedChunks, false);
	}

	/**
	 * Validates an STG.
	 * @param stg the STG, should not be null and not be loaded.
	 * @param expectedChunks the expected chunks, should not be null nor have null members
	 * @param extraIsError true for extra (additional) templates being treated as an error, false otherwise
	 * @return true on success, false on error
	 */
	default boolean validate(final STGroup stg, final Map<String, String[]> expectedChunks, boolean extraIsError){
		this.getErrorSet().clearMessages();

		if(stg==null){
			this.getErrorSet().add(Templates_STG.STG_NULL.getError());
			return false;
		}

		Validate.notNull(expectedChunks);
		Validate.noNullElements(expectedChunks.keySet());
		Validate.noNullElements(expectedChunks.values());

		IsSTErrorListener errorListener = IsSTErrorListener.create();
		stg.setListener(errorListener);
		stg.load();
		if(errorListener.getErrorSet().hasMessages()){
			this.getErrorSet().addAll(errorListener.getErrorSet());
			return false;
		}

		for(Entry<String, String[]> entry : expectedChunks.entrySet()){
			if(!"".equals(entry.getKey())){
				if(stg.isDefined(entry.getKey())){
					ST st = stg.getInstanceOf(entry.getKey());
					if(st==null){
						this.getErrorSet().add(Templates_STG.ST_IS_NULL.getError(this.getGroupName(stg), entry.getValue()));
					}
					else{
						STValidator stval = STValidator.create();
						stval.validate(st, entry.getValue(), extraIsError);
						if(stval.getErrorSet().hasMessages()){
							this.getErrorSet().add(Templates_STG.MISSING_ST_ARG_ERRORS.getError(this.getGroupName(stg), entry.getKey()));
							this.getErrorSet().addAll(stval.getErrorSet());
						}
					}
				}
				else{
					this.getErrorSet().add(Templates_STG.MISSING_EXPECTED_ST.getError(this.getGroupName(stg), entry.getKey()));
				}
			}
		}
		if(extraIsError){
			for(String s : stg.getTemplateNames()){
				if(!expectedChunks.keySet().contains(s)){
					this.getErrorSet().add(Templates_STG.EXTRA_ST.getError(this.getGroupName(stg), s));
				}
			}
		}
		return !this.getErrorSet().hasMessages();
	}

	/**
	 * Returns the name of the STGroup object (file name, source name, or group directory name).
	 * @return STGroup name, null if {@link #getSTGroup()} was null or if no name was found
	 */
	default String getGroupName(STGroup stg){
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
}
