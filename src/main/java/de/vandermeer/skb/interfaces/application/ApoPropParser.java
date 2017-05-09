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
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.interfaces.messagesets.IsErrorSet_IsError;
import de.vandermeer.skb.interfaces.messagesets.errors.IsError;
import de.vandermeer.skb.interfaces.messagesets.errors.Templates_PropertiesGeneral;
import de.vandermeer.skb.interfaces.transformers.textformat.Text_To_FormattedText;

/**
 * API for a property parser.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface ApoPropParser extends ApoParser<Apo_TypedP<?>, ApoPropOptions> {

	static ApoPropParser create(final String appName, final boolean unknownKeyIsError){
		return new ApoPropParser() {
			protected final transient ApoPropOptions options = ApoPropOptions.create();

			protected final transient IsErrorSet_IsError errorSet = IsErrorSet_IsError.create();

			protected transient int errNo;

			@Override
			public String getAppName() {
				return appName;
			}
			
			@Override
			public int getErrNo() {
				return this.errNo;
			}
			
			@Override
			public IsErrorSet_IsError getErrorSet() {
				return this.errorSet;
			}
			
			@Override
			public ApoPropOptions getOptions() {
				return this.options;
			}
			
			@Override
			public void setErrno(int errorNumber) {
				this.errNo = errorNumber;
			}

			@Override
			public boolean unknownKeyIsError(){
				return unknownKeyIsError;
			}
		};
	}

	/**
	 * Returns the application name.
	 * @return application name, must not be null
	 */
	String getAppName();


	/**
	 * Returns the number of the last error, 0 if none occurred.
	 * @return last error number
	 */
	int getErrNo();

	/**
	 * Returns all options added to the parser.
	 * @return all options, empty if none added
	 */
	ApoPropOptions getOptions();

	/**
	 * Parses properties and sets values for property options.
	 * Parsing errors are in the local error set.
	 * Latest parsing error code is in the local `errNo` member.
	 * @param properties the properties to parse, ignored (no parsing happens) if null
	 */
	default void parse(Properties properties){
		if(properties!=null){
			this.parse(new Properties[]{properties});
		}
	}

	/**
	 * Parses a set of properties and sets values for property options.
	 * Parsing errors are in the local error set.
	 * Latest parsing error code is in the local `errNo` member.
	 * @param properties array of properties to parse, ignored (no parsing happens) if null
	 */
	default void parse(Properties[] properties){
		if(properties==null){
			return;
		}

		if(this.unknownKeyIsError()){
			for(Properties prop : properties){
				for (Enumeration<?> names = prop.propertyNames(); names.hasMoreElements();){
					String key = names.nextElement().toString();
					if(!this.getOptions().getMap().keySet().contains(key)){
						this.getErrorSet().addError(Templates_PropertiesGeneral.UNRECOGNIZED_KEY.getError(this.getAppName(), key));
						this.setErrno(Templates_PropertiesGeneral.UNRECOGNIZED_KEY.getCode());
					}
				}
			}
		}

		Set<String> setOptions = new HashSet<>();
		for(Entry<String, Apo_TypedP<?>> entry : this.getOptions().getMap().entrySet()){
			String key = entry.getKey();
			Apo_TypedP<?> value = entry.getValue();
			for(Properties prop : properties){
				String propValue = prop.getProperty(key);
				if(propValue!=null){
					if(setOptions.contains(key)){
						this.getErrorSet().addError(Templates_PropertiesGeneral.ALREADY_SELECTED.getError(this.getAppName(), key));
						this.setErrno(Templates_PropertiesGeneral.ALREADY_SELECTED.getCode());
					}
					else{
						IsError error = value.setPropertyValue(propValue);
						if(error!=null){
							this.getErrorSet().addError(error);
							this.setErrno(error.getErrorCode());
						}
						value.setInProperties(true);
						setOptions.add(key);
					}
				}
			}
			if(!setOptions.contains(key) && value.propertyIsRequired()){
				this.getErrorSet().addError(Templates_PropertiesGeneral.MISSING_KEY.getError(this.getAppName(), key));
				this.setErrno(Templates_PropertiesGeneral.MISSING_KEY.getCode());
			}
		}

	}

	/**
	 * Treat unknown (unrecognized) keys as error.
	 * @return true if unrecognized keys are an error, false otherwise (default implementation is false)
	 */
	default boolean unknownKeyIsError(){
		return false;
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
		TreeMap<String, Apo_TypedP<?>> map = this.getOptions().sortedMap();
		Map<String, String> envMap = new LinkedHashMap<>();
		int length = 0;
		for(Apo_TypedP<?> te : map.values()){
			if(te.getPropertyKey().length()>length){
				length = te.getPropertyKey().length();
			}
			envMap.put(te.getPropertyKey(), te.getDescription());
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
