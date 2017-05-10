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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.interfaces.messages.errors.IsError;
import de.vandermeer.skb.interfaces.messages.errors.Templates_EnvironmentGeneral;
import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;
import de.vandermeer.skb.interfaces.transformers.textformat.Text_To_FormattedText;

/**
 * API for an environment parser.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.2
 */
public interface ApoEnvParser extends ApoParser<Apo_TypedE<?>, ApoEnvOptions> {

	/**
	 * Creates a new default parser.
	 * @return new default parser
	 */
	static ApoEnvParser create(){
		return new ApoEnvParser() {
			protected final transient ApoEnvOptions options = ApoEnvOptions.create();

			protected final transient IsErrorSet errorSet = IsErrorSet.create();

			protected transient int errNo;

			@Override
			public int getErrNo() {
				return this.errNo;
			}

			@Override
			public IsErrorSet getErrorSet() {
				return this.errorSet;
			}

			@Override
			public ApoParserOptionSet<Apo_TypedE<?>> getOptions() {
				return this.options;
			}

			@Override
			public void setErrno(int errorNumber) {
				this.errNo = errorNumber;
			}
		};
	}

	/**
	 * Parses environment and sets values for environment options.
	 * Parsing errors are in the local error set.
	 * Latest parsing error code is in the local `errNo` member.
	 */
	default void parse(){
		Map<String, String> envSettings = null;
		try{
			envSettings = System.getenv();
		}
		catch(SecurityException se){
			this.getErrorSet().add(Templates_EnvironmentGeneral.SECURITY_EXCEPTION.getError(se.getMessage()));
			this.setErrno(Templates_EnvironmentGeneral.SECURITY_EXCEPTION.getCode());
			return;
		}
		if(envSettings==null){
			this.getErrorSet().add(Templates_EnvironmentGeneral.SYSTEM_NO_ENV.getError());
			this.setErrno(Templates_EnvironmentGeneral.SYSTEM_NO_ENV.getCode());
			return;
		}

		Set<String> setOptions = new HashSet<>();
		for(Entry<String, Apo_TypedE<?>> entry : this.getOptions().getMap().entrySet()){
			String key = entry.getKey();
			Apo_TypedE<?> value = entry.getValue();
			if(!envSettings.keySet().contains(key) && value.environmentIsRequired()){
				this.getErrorSet().add(Templates_EnvironmentGeneral.MISSING_KEY.getError(key));
				this.setErrno(Templates_EnvironmentGeneral.MISSING_KEY.getCode());
			}
			else if(StringUtils.isBlank(envSettings.get(key))){
				if(value.environmentIsRequired()){
					this.getErrorSet().add(Templates_EnvironmentGeneral.MISSING_ARGUMENT.getError(key));
					this.setErrno(Templates_EnvironmentGeneral.MISSING_ARGUMENT.getCode());
				}
			}
			else if(setOptions.contains(key)){
				this.getErrorSet().add(Templates_EnvironmentGeneral.ALREADY_SELECTED.getError(key));
				this.setErrno(Templates_EnvironmentGeneral.ALREADY_SELECTED.getCode());
			}
			else{
				IsError error = value.setEnvironmentValue(envSettings.get(key));
				if(error!=null){
					this.getErrorSet().add(error);
					this.setErrno(error.getErrorCode());
				}
				value.setInEnvironment(true);
				setOptions.add(key);
			}
		}
	}

	/**
	 * Prints usage information for the CLI parser including all CLI options.
	 * @return list of lines with usage information
	 */
	default ArrayList<StrBuilder> usage(){
		return this.usage(80);
	}

	/**
	 * Prints usage information for the parser including all options.
	 * @param width the console columns or width of each output line
	 * @return list of lines with usage information
	 */
	default ArrayList<StrBuilder> usage(int width){
		TreeMap<String, Apo_TypedE<?>> map = this.getOptions().sortedMap();
		Map<String, String> envMap = new LinkedHashMap<>();
		int length = 0;
		for(Apo_TypedE<?> te : map.values()){
			if(te.getEnvironmentKey().length()>length){
				length = te.getEnvironmentKey().length();
			}
			envMap.put(te.getEnvironmentKey(), te.getDescription());
		}
		length += 2;

		ArrayList<StrBuilder> ret = new ArrayList<>();
		for(Entry<String, String> entry : envMap.entrySet()){
			StrBuilder argLine = new StrBuilder();
			argLine.append(entry.getKey()).appendPadding(length-argLine.length(), ' ').append("  - ");
			StrBuilder padLine = new StrBuilder();
			padLine.appendPadding(length+4, ' ');

			Collection<StrBuilder> text = Text_To_FormattedText.left(entry.getValue(), width-length);
			int i = 0;
			for(StrBuilder b : text){
				if(i==0){
					b.insert(0, argLine);
				}
				else{
					b.insert(0, padLine);
				}
				ret.add(b);
				i++;
			}
		}
		return ret;
	}
}
