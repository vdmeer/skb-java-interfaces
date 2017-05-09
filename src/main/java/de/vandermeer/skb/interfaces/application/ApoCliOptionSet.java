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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.cli.Option;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.Validate;

/**
 * CLI option set for simple and typed options.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface ApoCliOptionSet {

	/**
	 * Creates a new default option set using Apache CLI options.
	 * @return new default option set
	 */
	static ApoCliOptionSet setApacheOptions(){
		return new ApoCliOptionSet() {
			protected transient int shortOptions;

			protected transient int longOptions;

			protected final transient Map<Apo_SimpleC, Object> simpleOptions = new HashMap<>();

			protected final transient Map<Apo_TypedC<?>, Object> typedOptions = new HashMap<>();

			protected final transient Set<String> addOptions = new HashSet<>();

			@Override
			public ApoCliOptionSet addOption(Object option) throws IllegalStateException {
				if(option==null){
					return this;
				}
				if(ClassUtils.isAssignable(option.getClass(), Apo_SimpleC.class)){
					Apo_SimpleC opt = (Apo_SimpleC)option;
					if(opt.getCliShort()!=null){
						Validate.validState(
								!this.getSetString().contains(opt.getCliShort().toString()),
								"DefaultCliParser: short option <" + opt.getCliShort() + "> already in use"
						);
					}
					if(opt.getCliLong()!=null){
						Validate.validState(
								!this.getSetString().contains(opt.getCliLong()),
								"DefaultCliParser: long option <" + opt.getCliLong() + "> already in use"
						);
					}
					this.addOption(opt);
				}
				else if(ClassUtils.isAssignable(option.getClass(), Apo_TypedC.class)){
					Apo_TypedC<?> opt = (Apo_TypedC<?>)option;
					if(opt.getCliShort()!=null){
						Validate.validState(
								!this.getSetString().contains(opt.getCliShort().toString()),
								"DefaultCliParser: short option <" + opt.getCliShort() + "> already in use"
						);
					}
					if(opt.getCliLong()!=null){
						Validate.validState(
								!this.getSetString().contains(opt.getCliLong()),
								"DefaultCliParser: long option <" + opt.getCliLong() + "> already in use"
						);
					}
					this.addOption(opt);
				}
				return this;
			}

			protected <T extends Apo_SimpleC> ApoCliOptionSet addOption(final T option) throws IllegalStateException {
				final Option.Builder builder = (option.getCliShort()==null)?Option.builder():Option.builder(option.getCliShort().toString());
				builder.longOpt(option.getCliLong());
				builder.required(option.cliIsRequired());

				this.simpleOptions.put(option, builder.build());
				if(option.getCliShort()!=null){
					this.getSetString().add(option.getCliShort().toString());
					this.shortOptions++;
				}
				if(option.getCliLong()!=null){
					this.getSetString().add(option.getCliLong());
					this.longOptions++;
				}

				return this;
			}

			protected <T extends Apo_TypedC<?>> ApoCliOptionSet addOption(final T option) throws IllegalStateException {
				final Option.Builder builder = (option.getCliShort()==null)?Option.builder():Option.builder(option.getCliShort().toString());
				builder.longOpt(option.getCliLong());
				builder.hasArg().argName(option.getCliArgumentName());
				builder.optionalArg(option.cliArgIsOptional());
				builder.required(option.cliIsRequired());

				this.typedOptions.put(option, builder.build());
				if(option.getCliShort()!=null){
					this.getSetString().add(option.getCliShort().toString());
					this.shortOptions++;
				}
				if(option.getCliLong()!=null){
					this.getSetString().add(option.getCliLong());
					this.longOptions++;
				}

				return this;
			}

			@Override
			public Set<String> getSetString() {
				return this.addOptions;
			}

			@Override
			public Map<Apo_SimpleC, Object> getSimpleMap() {
				return this.simpleOptions;
			}

			@Override
			public Map<Apo_TypedC<?>, Object> getTypedMap() {
				return this.typedOptions;
			}

			@Override
			public int numberLong() {
				return this.longOptions;
			}

			@Override
			public int numberShort() {
				return this.shortOptions;
			}
		};
	}

	/**
	 * Adds all options to the set.
	 * @param options the options to be added, ignored if null, any null element is ignored as well
	 * @return self to allow chaining
	 * @throws IllegalStateException if the option is already in use
	 */
	default ApoCliOptionSet addAllOptions(Iterable<?> options) throws IllegalStateException{
		if(options!=null){
			for(Object opt : options){
				this.addOption(opt);
			}
		}
		return this;
	}

	/**
	 * Adds all options to the set.
	 * @param options the options to be added, ignored if null, any null element is ignored as well
	 * @return self to allow chaining
	 * @throws IllegalStateException if the option is already in use
	 */
	default ApoCliOptionSet addAllOptions(Object[] options) throws IllegalStateException{
		if(options!=null){
			for(Object opt : options){
				this.addOption(opt);
			}
		}
		return this;
	}

	/**
	 * Adds an option to the set.
	 * @param option the option to be added, ignored if null
	 * @return self to allow chaining
	 * @throws IllegalStateException if the option is already in use
	 */
	ApoCliOptionSet addOption(Object option) throws IllegalStateException;

	/**
	 * Returns all required options, taking short or long CLI argument.
	 * @param list input option list
	 * @return all required options
	 */
	default Set<String> getRequired(){
		Set<String> ret = new TreeSet<>();
		for(ApoBaseC opt : this.getSet()){
			if(opt.cliIsRequired()){
				String required = (opt.getCliShort()==null)?"--"+opt.getCliLong():"-"+opt.getCliShort();
				if(ClassUtils.isAssignable(opt.getClass(), Apo_TypedC.class)){
					required += " <" + ((Apo_TypedC<?>)opt).getCliArgumentName() + ">";
				}
				ret.add(required);
			}
		}
		return ret;
	}

	/**
	 * Returns the set of added options.
	 * @return set of added options, must not be null
	 */
	default Set<ApoBaseC> getSet(){
		Set<ApoBaseC> ret = new HashSet<ApoBaseC>();
		ret.addAll(getSimpleMap().keySet());
		ret.addAll(getTypedMap().keySet());
		return ret;
	}

	/**
	 * Returns the added options as string set, all short and long options.
	 * @return added options
	 */
	Set<String> getSetString();

	/**
	 * Returns the simple options.
	 * @return simple options, must not be null, empty if no simple options added
	 */
	Map<Apo_SimpleC, Object> getSimpleMap();

	/**
	 * Returns the set of simple CLI options.
	 * @return set of simple options, empty if none set
	 */
	default Set<Apo_SimpleC> getSimpleSet(){
		return this.getSimpleMap().keySet();
	}

	/**
	 * Returns the typed options.
	 * @return typed options, must not be null, empty if no typed option added
	 */
	Map<Apo_TypedC<?>, Object> getTypedMap();

	/**
	 * Returns the set of typed CLI options.
	 * @return set of typed CLI options, empty if none set
	 */
	default Set<Apo_TypedC<?>> getTypedSet(){
		return this.getTypedMap().keySet();
	}

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
			if(opt.getCliShort()!=null && this.getSetString().contains(opt.getCliShort().toString())){
				return true;
			}
			if(opt.getCliLong()!=null && this.getSetString().contains(opt.getCliLong())){
				return true;
			}
		}
		return false;
	}

	/**
	 * Tests if an option key is already in the set.
	 * @param key the key to test
	 * @return true if key is in the set, false otherwise
	 */
	default boolean hasOption(String key){
		return this.getSetString().contains(key);
	}

	/**
	 * Statistic method: returns number of CLI arguments with long option.
	 * @return number of CLI arguments with long option
	 */
	int numberLong();

	/**
	 * Statistic method: returns number of CLI arguments with short option.
	 * @return number of CLI arguments with short option
	 */
	int numberShort();

	/**
	 * Returns the number of options.
	 * @return number of options, 0 if none added
	 */
	default int size(){
		return this.getSimpleMap().size() + this.getTypedMap().size();
	}

	/**
	 * Returns a sorted collection of CLI options.
	 * @param list option list to sort
	 * @param numberShort number of arguments with short command
	 * @param numberLong number of arguments with long command
	 * @return sorted collection
	 */
	default Collection<ApoBaseC> sortedList(){
		return sortedMap().values();
	}

	/**
	 * Returns a sorted map of CLI options, the mapping is the sort string to the CLI option.
	 * @param list option list to sort
	 * @param numberShort number of arguments with short command
	 * @param numberLong number of arguments with long command
	 * @return sorted map
	 */
	default TreeMap<String, ApoBaseC> sortedMap(){
		TreeMap<String, ApoBaseC> ret = new TreeMap<>();
		for(ApoBaseC opt : this.getSet()){
			String key;
			if(this.numberShort()==0){
				key = opt.getCliLong();
			}
			if(this.numberLong()==0){
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
}
