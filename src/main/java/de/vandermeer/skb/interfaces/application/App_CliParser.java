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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.skb.interfaces.messagesets.HasErrorSet;
import de.vandermeer.skb.interfaces.messagesets.IsErrorSet_IsError;
import de.vandermeer.skb.interfaces.transformers.textformat.Text_To_FormattedText;

/**
 * Base for a CLI parser.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.2
 */
public interface App_CliParser extends HasErrorSet<IsErrorSet_IsError> {

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
	 * Statistic method: returns number of CLI arguments with short option.
	 * @return number of CLI arguments with short option
	 */
	int numberShort();

	/**
	 * Statistic method: returns number of CLI arguments with long option.
	 * @return number of CLI arguments with long option
	 */
	int numberLong();

	/**
	 * Adds all options to the parser.
	 * @param options the options to be added, ignored if null, any null element is ignored as well
	 * @return self to allow chaining
	 * @throws IllegalStateException if the option is already in use
	 */
	default App_CliParser addAllOptions(Object[] options) throws IllegalStateException{
		if(options!=null){
			for(Object opt : options){
				this.addOption(opt);
			}
		}
		return this;
	}

	/**
	 * Adds all options to the parser.
	 * @param options the options to be added, ignored if null, any null element is ignored as well
	 * @return self to allow chaining
	 * @throws IllegalStateException if the option is already in use
	 */
	default App_CliParser addAllOptions(Iterable<?> options) throws IllegalStateException{
		if(options!=null){
			for(Object opt : options){
				this.addOption(opt);
			}
		}
		return this;
	}

	/**
	 * Adds a new option to the parser.
	 * @param option the option to be added, ignored if `null`
	 * @return self to allow chaining
	 * @throws IllegalStateException if the option is already in use
	 */
	App_CliParser addOption(Object option) throws IllegalStateException;

	/**
	 * Returns the options already added, short or long.
	 * @return already added options, empty if none added
	 */
	Set<String> getAddedOptions();

	/**
	 * Returns all simple options added to the parser.
	 * @return all simple options, empty array if none added
	 */
	Set<Apo_SimpleC> getSimpleOptions();

	/**
	 * Returns all typed options added to the parser.
	 * @return all typed options, empty array if none added
	 */
	Set<Apo_TypedC<?>> getTypedOptions();

	/**
	 * Returns a set of all options.
	 * @return set of all options, empty if none set
	 */
	Set<ApoBaseC> getAllOptions();

	/**
	 * Tests if an option is already added to the parser.
	 * @param option the option to test for
	 * @return true if parser has the option (short or long), false otherwise (option was `null` or not an instance of {@link ApoBaseC}
	 */
	default boolean hasOption(ApoBase option){
		if(option==null){
			return false;
		}
		if(option.getClass().isInstance(ApoBaseC.class)){
			ApoBaseC opt = (ApoBaseC)option;
			if(opt.getCliShort()!=null && this.getAddedOptions().contains(opt.getCliShort().toString())){
				return true;
			}
			if(opt.getCliLong()!=null && this.getAddedOptions().contains(opt.getCliLong())){
				return true;
			}
		}
		return false;
	}

	/**
	 * Parses command line arguments and sets values for CLI options.
	 * Parsing errors are in the local error set.
	 * Latest parsing error code is in the local `errNo` member.
	 * @param args command line arguments
	 */
	void parse(String[] args);

	/**
	 * Prints usage information for the CLI parser including all CLI options.
	 * @param width the console columns or width of each output line
	 * @return list of lines with usage information
	 */
	default ArrayList<StrBuilder> usage(int width){
		TreeMap<String, ApoBaseC> map = CliOptionList.sortedMap(this.getAllOptions(), this.numberShort(), this.numberLong());
		Map<String, String> helpMap = new LinkedHashMap<>();
		int length = 0;
		STGroupFile stg = new STGroupFile("de/vandermeer/skb/interfaces/application/help.stg");
		for(Object option : map.values()){
			ST sto = stg.getInstanceOf("cliOption");
			String description = null;
			if(ClassUtils.isAssignable(option.getClass(), Apo_SimpleC.class)){
				sto.add("cliShort", ((Apo_SimpleC)option).getCliShort());
				sto.add("cliLong", ((Apo_SimpleC)option).getCliLong());
				description = ((Apo_SimpleC)option).getDescription();
			}
			if(ClassUtils.isAssignable(option.getClass(), Apo_TypedC.class)){
				sto.add("cliShort", ((Apo_TypedC<?>)option).getCliShort());
				sto.add("cliLong", ((Apo_TypedC<?>)option).getCliLong());
				sto.add("argName", ((Apo_TypedC<?>)option).getCliArgumentName());
				sto.add("argOptional", ((Apo_TypedC<?>)option).cliArgIsOptional());
				description = ((Apo_TypedC<?>)option).getDescription();
			}
			String line = sto.render();
			if(line.length()>length){
				length = line.length();
			}
			helpMap.put(line, description);
		}
		length += 4;

		ArrayList<StrBuilder> ret = new ArrayList<>();
		for(Entry<String, String> entry : helpMap.entrySet()){
			StrBuilder argLine = new StrBuilder();
			argLine.append(entry.getKey()).appendPadding(length-argLine.length(), ' ');
			StrBuilder padLine = new StrBuilder();
			padLine.appendPadding(length, ' ');

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

	/**
	 * Prints usage information for the CLI parser including all CLI options.
	 * @return list of lines with usage information
	 */
	default ArrayList<StrBuilder> usage(){
		return this.usage(80);
	}

}
