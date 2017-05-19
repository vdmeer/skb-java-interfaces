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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Parser for a command line.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface Sh_CommandSetParser {

	/**
	 * Creates a new command set parser.
	 * @return new command set parser
	 */
	static Sh_CommandSetParser create(){
		return new Sh_CommandSetParser() {};
	}

	/**
	 * Parses a command line and sets values for a command in a command set.
	 * All errors are notified using {@link IllegalStateException}.
	 * @param clk tokenized command line to parse
	 * @param set the commend set to parse for
	 * @return the command found, all possible arguments in the command set
	 * @throws IllegalStateException for all problems
	 */
	default Sh_CmdBase parse(Sh_CommandLineTokens clk, Sh_CommandSet set){
		Validate.validState(clk!=null, "tokenized commandline was null");
		Validate.validState(set!=null, "command set for parsing was null");
		set.clearCmdValues();
		Validate.validState(clk.head().size()!=0, "no command found in command line");

		Sh_CmdBase ret = null;
		String command = clk.head().get(0);
		if(set.getSimpleMap().keySet().contains(command)){
			Validate.validState(clk.head().size()==1 || clk.tail().size()==0, "found simple command with arguments");
			ret = set.getSimpleMap().get(command);
		}
		else if(set.getTypedMap().keySet().contains(command)){
			Validate.validState(clk.head().size()<3, "found typed command with too many arguments");
			Validate.validState(clk.tail().size()==0, "found typed command with complex arguments");

			Sh_TypedCmd<?> tc = set.getTypedMap().get(command);
			if(tc.argIsRequired()){
				Validate.validState(clk.head().size()==2, "found typed command with required argument but no argument in command line");
			}
			if(clk.head().size()==2){
				tc.setCmdValue(clk.head().get(1));
			}
			ret = tc;
		}
		else if(set.getLongTypedMap().keySet().contains(command)){
			Validate.validState(clk.tail().size()==0, "found long typed command with complex arguments");
			Sh_LongTypedCmd ltc = set.getLongTypedMap().get(command);

			List<String> clArgs = new ArrayList<>(clk.head());
			clArgs.remove(0);
			Validate.validState(clArgs.size()==ltc.getArguments().length, "long typed command <" + command + "> expected <" + ltc.getArguments().length + "> arguments, found <" + clArgs.size() + ">");
			for(int i=0; i<clArgs.size(); i++){
				ltc.getArguments()[i].setCmdValue(clArgs.get(i));
			}
			ret = ltc;
		}
		else if(set.getComplexMap().keySet().contains(command)){
			Validate.validState(clk.head().size()==1, "found complex command with too many/few arguments");
			Validate.validState(clk.tail().size()>0, "found complex command with no arguments");

			Sh_ComplexCmd cc = set.getComplexMap().get(command);
			for(Pair<String, String> pair : clk.tail()){
				for(Sh_ComplexArgument<?> cmdArg : cc.getArguments()){
					if(cmdArg.getName().equals(pair.getKey())){
						cmdArg.setCmdValue(pair.getValue());
					}
					else{
						throw new IllegalStateException("unknown argument <" + pair.getKey() + "> for command <" + command + ">");
					}
				}
			}
			for(Sh_ComplexArgument<?> cmdArg : cc.getArguments()){
				if(cmdArg.argIsRequired() && cmdArg.getCmdValue()==null){
					throw new IllegalStateException("missing required argument <" + cmdArg.getName() + "> for command <" + command + ">");
				}
			}
			ret = cc;
		}
		else{
			throw new IllegalStateException("unknown command <" + command + ">");
		}
		return ret;
	}

	/**
	 * Parses a command line and sets values for a command in a command set.
	 * All errors are notified using {@link IllegalStateException}.
	 * @param commandline command line to parse
	 * @param set the commend set to parse for
	 * @return the command found, all possible arguments in the command set
	 * @throws IllegalStateException for all problems
	 */
	default Sh_CmdBase parse(String commandline, Sh_CommandSet set){
		Validate.validState(StringUtils.isNotBlank(commandline), "command line must not be blank");
		Validate.validState(set!=null, "set for parsing must not be null");
		Sh_CommandLineTokens clk = Sh_CommandLineTokens.create(commandline);
		return this.parse(clk, set);
	}

}
