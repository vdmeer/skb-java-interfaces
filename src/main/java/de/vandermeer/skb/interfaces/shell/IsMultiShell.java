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

import org.apache.commons.lang3.Validate;

/**
 * API of a shell for command multi-sets.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface IsMultiShell extends IsShell {

	/**
	 * Returns the commands the shell provides.
	 * @return shell commands, must not be null
	 */
	Sh_CommandMultiSet getCommands();

	@Override
	default int runCommand(String commandline){
		Sh_CommandLineTokens clk = Sh_CommandLineTokens.create(commandline);
		Validate.validState(clk.head().size()>1, "found less heads than expected");
		String setName = clk.head().remove(0);
		if(!this.getCommands().hasSet(setName)){
			throw new IllegalStateException("unknown command set <" + setName + ">");
		}

		Sh_CommandSet set = this.getCommands().getSet(setName);
		Sh_CommandSetParser parser = Sh_CommandSetParser.create();
		Sh_CmdBase command = parser.parse(clk, set);
		if(command==null){
			//just in case, parser should have handled this already
			throw new IllegalStateException("no command found");
		}
		return command.executeCommand();
	}

	@Override
	default String getName(){
		return this.getCommands().getName();
	}

	@Override
	default String getDescription(){
		return this.getCommands().getDescription();
	}

	@Override
	default String getDisplayName(){
		return this.getCommands().getDisplayName();
	}

	@Override
	default String getVersion(){
		return this.getCommands().getVersion();
	}

}
