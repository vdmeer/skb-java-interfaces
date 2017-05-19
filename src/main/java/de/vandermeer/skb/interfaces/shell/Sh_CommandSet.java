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

package de.vandermeer.skb.interfaces.shell;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.categories.has.HasDescription;
import de.vandermeer.skb.interfaces.categories.has.HasDisplayName;
import de.vandermeer.skb.interfaces.categories.has.HasName;
import de.vandermeer.skb.interfaces.categories.has.HasVersion;

/**
 * Set of commands.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface Sh_CommandSet extends HasName, HasDisplayName, HasVersion, HasDescription {

	/**
	 * Creates a new command set.
	 * @param name the name of the set, must not be blank
	 * @param displayName the display name of the set, must not be blank
	 * @param version the version of the command set
	 * @param description the description of the set, must not be blank
	 * @return a new command set on success
	 */
	static Sh_CommandSet create(String name, String displayName, String version, String description){
		Validate.notBlank(name);
		Validate.notBlank(displayName);
		Validate.notBlank(version);
		Validate.notBlank(description);

		return new Sh_CommandSet() {
			protected final transient Map<String, Sh_SimpleCmd> simpleCommands = new HashMap<>();

			protected final transient Map<String, Sh_TypedCmd<?>> typedCommands = new HashMap<>();

			protected final transient Map<String, Sh_LongTypedCmd> longTypedCommands = new HashMap<>();

			protected final transient Map<String, Sh_ComplexCmd> complexCommands = new HashMap<>();

			@Override
			public Sh_CommandSet addCommand(Object command) throws IllegalStateException {
				if(command==null){
					return this;
				}
				if(ClassUtils.isAssignable(command.getClass(), Sh_SimpleCmd.class)){
					Sh_SimpleCmd cmd = (Sh_SimpleCmd)command;
					Validate.validState(
							!this.hasCommand(cmd.getName()),
							"CommandParser: simple command <" + cmd.getName() + "> already in use"
					);
					this.simpleCommands.put(cmd.getName(), cmd);
				}
				else if(ClassUtils.isAssignable(command.getClass(), Sh_TypedCmd.class)){
					Sh_TypedCmd<?> cmd = (Sh_TypedCmd<?>)command;
					Validate.validState(
							!this.hasCommand(cmd.getName()),
							"CommandParser: typed command <" + cmd.getName() + "> already in use"
					);
					this.typedCommands.put(cmd.getName(), cmd);
				}
				else if(ClassUtils.isAssignable(command.getClass(), Sh_LongTypedCmd.class)){
					Sh_LongTypedCmd cmd = (Sh_LongTypedCmd)command;
					Validate.validState(
							!this.hasCommand(cmd.getName()),
							"CommandParser: long typed command <" + cmd.getName() + "> already in use"
					);
					this.longTypedCommands.put(cmd.getName(), cmd);
				}
				else if(ClassUtils.isAssignable(command.getClass(), Sh_ComplexCmd.class)){
					Sh_ComplexCmd cmd = (Sh_ComplexCmd)command;
					Validate.validState(
							!this.hasCommand(cmd.getName()),
							"CommandParser: complex command <" + cmd.getName() + "> already in use"
					);
					this.complexCommands.put(cmd.getName(), cmd);
				}
				return this;
			}

			@Override
			public Map<String, Sh_ComplexCmd> getComplexMap() {
				return this.complexCommands;
			}

			@Override
			public String getDescription() {
				return description;
			}

			@Override
			public String getDisplayName() {
				return displayName;
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public Map<String, Sh_SimpleCmd> getSimpleMap() {
				return this.simpleCommands;
			}

			@Override
			public Map<String, Sh_TypedCmd<?>> getTypedMap() {
				return this.typedCommands;
			}

			@Override
			public String getVersion() {
				return version;
			}

			@Override
			public Map<String, Sh_LongTypedCmd> getLongTypedMap() {
				return this.longTypedCommands;
			}

		};
	}

	/**
	 * Adds all commands to the set.
	 * @param commands the commands to be added, ignored if null, any null element is ignored as well
	 * @return self to allow chaining
	 * @throws IllegalStateException if the command is already in use
	 */
	default Sh_CommandSet addAllCommands(Iterable<?> commands) throws IllegalStateException{
		if(commands!=null){
			for(Object opt : commands){
				this.addCommand(opt);
			}
		}
		return this;
	}

	/**
	 * Adds all commands to the set.
	 * @param commands the commands to be added, ignored if null, any null element is ignored as well
	 * @return self to allow chaining
	 * @throws IllegalStateException if the command is already in use
	 */
	default Sh_CommandSet addAllCommands(Object[] commands) throws IllegalStateException{
		if(commands!=null){
			for(Object opt : commands){
				this.addCommand(opt);
			}
		}
		return this;
	}

	/**
	 * Adds a command to the set.
	 * @param command the command to be added, ignored if null
	 * @return self to allow chaining
	 * @throws IllegalStateException if the command is already in use
	 */
	Sh_CommandSet addCommand(Object command) throws IllegalStateException;

	/**
	 * Clears all values of typed and complex commands in preparation for a new command line parse.
	 */
	default void clearCmdValues(){
		for(Sh_TypedCmd<?> command : this.getTypedMap().values()){
			command.resetCmdValue();
		}
		for(Sh_LongTypedCmd command : this.getLongTypedMap().values()){
			for(Sh_LongTypedArgument<?> argmument : command.getArguments()){
				argmument.resetCmdValue();
			}
		}
		for(Sh_ComplexCmd command : this.getComplexMap().values()){
			for(Sh_ComplexArgument<?> argmument : command.getArguments()){
				argmument.resetCmdValue();
			}
		}
	}

	/**
	 * Returns a command for a given command name.
	 * @param name the command name
	 * @return null on error, a command on success
	 */
	default Sh_CmdBase getCommand(String name){
		if(this.getSimpleMap().containsKey(name)){
			return this.getSimpleMap().get(name);
		}
		else if(this.getTypedMap().containsKey(name)){
			return this.getTypedMap().get(name);
		}
		else if(this.getLongTypedMap().containsKey(name)){
			return this.getLongTypedMap().get(name);
		}
		return this.getComplexMap().get(name);
	}

	/**
	 * Returns the complex commands.
	 * @return complex commands, must not be null, empty if no typed command added
	 */
	Map<String, Sh_ComplexCmd> getComplexMap();

	/**
	 * Returns the simple commands.
	 * @return simple commands, must not be null, empty if no simple commands added
	 */
	Map<String, Sh_SimpleCmd> getSimpleMap();

	/**
	 * Returns the typed commands.
	 * @return typed commands, must not be null, empty if no typed commands added
	 */
	Map<String, Sh_TypedCmd<?>> getTypedMap();

	/**
	 * Returns the long typed commands.
	 * @return long typed commands, must not be null, empty if no typed commands added
	 */
	Map<String, Sh_LongTypedCmd> getLongTypedMap();

	/**
	 * Tests if a command name is already in the set.
	 * @param name the command name to test
	 * @return true if key is in the set, false otherwise
	 */
	default boolean hasCommand(String name){
		return this.getSimpleMap().keySet().contains(name)
				|| this.getTypedMap().keySet().contains(name)
				|| this.getLongTypedMap().keySet().contains(name)
				|| this.getComplexMap().keySet().contains(name)
		;
	}

	/**
	 * Returns the number of commands.
	 * @return number of commands, 0 if none added
	 */
	default int size(){
		return this.getSimpleMap().size()
				+ this.getLongTypedMap().size()
				+ this.getTypedMap().size()
				+ this.getComplexMap().size()
		;
	}

	/**
	 * Returns a sorted collection of commands.
	 * @return sorted collection
	 */
	default Collection<Sh_CmdBase> sortedList(){
		return sortedMap().values();
	}

	/**
	 * Returns a sorted map of commands, the mapping is the sort string to the command.
	 * @return sorted map
	 */
	default TreeMap<String, Sh_CmdBase> sortedMap(){
		TreeMap<String, Sh_CmdBase> ret = new TreeMap<>();
		for(Sh_CmdBase opt : getSimpleMap().values()){
			ret.put(opt.getName(), opt);
		}
		for(Sh_CmdBase opt : getTypedMap().values()){
			ret.put(opt.getName(), opt);
		}
		for(Sh_CmdBase opt : getLongTypedMap().values()){
			ret.put(opt.getName(), opt);
		}
		for(Sh_CmdBase opt : getComplexMap().values()){
			ret.put(opt.getName(), opt);
		}
		return ret;
	}
}
