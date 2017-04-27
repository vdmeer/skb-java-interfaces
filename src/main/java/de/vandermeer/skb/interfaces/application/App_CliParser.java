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

import java.util.Set;

/**
 * Base for a CLI parser.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.2
 */
public interface App_CliParser {

	/**
	 * Adds all options to the parser.
	 * @param options the options to be added, ignored if null, any null element is ignored as well
	 * @param <T> the option type, here a simple CLI option
	 * @return self to allow chaining
	 * @throws IllegalStateException if the option is already in use
	 */
	default <T extends Apo_SimpleC> App_CliParser addAllOptions(T[] options) throws IllegalStateException{
		if(options!=null){
			for(T opt : options){
				this.addOption(opt);
			}
		}
		return this;
	}

	/**
	 * Adds all options to the parser.
	 * @param options the options to be added, ignored if null, any null element is ignored as well
	 * @param <T> the option type, here a typed CLI option
	 * @return self to allow chaining
	 * @throws IllegalStateException if the option is already in use
	 */
	default <T extends Apo_TypedC<?>> App_CliParser addAllOptions(T[] options) throws IllegalStateException{
		if(options!=null){
			for(T opt : options){
				this.addOption(opt);
			}
		}
		return this;
	}

	/**
	 * Adds all options to the parser.
	 * @param options the options to be added, ignored if null, any null element is ignored as well
	 * @param <T> the option type, here a simple CLI option
	 * @return self to allow chaining
	 * @throws IllegalStateException if the option is already in use
	 */
	default <T extends Apo_SimpleC> App_CliParser addAllSimpleOptions(Iterable<T> options) throws IllegalStateException{
		if(options!=null){
			for(T opt : options){
				this.addOption(opt);
			}
		}
		return this;
	}

	/**
	 * Adds all options to the parser.
	 * @param options the options to be added, ignored if null, any null element is ignored as well
	 * @param <T> the option type, here a typed CLI option
	 * @return self to allow chaining
	 * @throws IllegalStateException if the option is already in use
	 */
	default <T extends Apo_TypedC<?>> App_CliParser addAllTypedOptions(Iterable<T> options) throws IllegalStateException{
		if(options!=null){
			for(T opt : options){
				this.addOption(opt);
			}
		}
		return this;
	}

	/**
	 * Adds a new option to the parser.
	 * @param option the option to be added, ignored if `null`
	 * @param <T> the option type, here a simple CLI option
	 * @return self to allow chaining
	 * @throws IllegalStateException if the option is already in use
	 */
	<T extends Apo_SimpleC> App_CliParser addOption(T option) throws IllegalStateException;

	/**
	 * Adds a new option to the parser.
	 * @param option the option to be added, ignored if `null`
	 * @param <T> the option type, here a typed CLI option
	 * @return self to allow chaining
	 * @throws IllegalStateException if the option is already in use
	 */
	<T extends Apo_TypedC<?>> App_CliParser addOption(T option) throws IllegalStateException;

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
	 * Tests if an option is already added to the command line parser.
	 * @param option the option to test for
	 * @return true if parser has the option (short or long), false otherwise (option was `null` or not an instance of {@link ApoBaseC}
	 */
	default boolean hasOption(ApoBase option){
		if(option==null){
			return false;
		}
		if(option.getClass().isInstance(ApoBaseC.class)){
			ApoBaseC simple = (Apo_SimpleC)option;
			if(this.getAddedOptions().contains(simple.getCliShort())){
				return true;
			}
			if(this.getAddedOptions().contains(simple.getCliLong())){
				return true;
			}
		}
		return false;
	}

	/**
	 * Parses command line arguments set values for CLI options.
	 * @param args command line arguments
	 * @throws IllegalStateException if a parsing error happened, for instance a required option was not present in the arguments
	 */
	void parse(String[] args) throws IllegalStateException;

	/**
	 * Prints usage information for the CLI parser including all CLI options.
	 * @param appName the name of the application for usage, must not be blank
	 */
	void usage(String appName);
}
