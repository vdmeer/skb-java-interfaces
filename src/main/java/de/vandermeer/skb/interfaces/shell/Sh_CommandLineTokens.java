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
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.text.StrMatcher;
import org.apache.commons.lang3.text.StrTokenizer;
import org.apache.commons.lang3.tuple.Pair;

/**
 * A tokenizer for a command line.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface Sh_CommandLineTokens {

	/**
	 * Returns the separators (characters) for complex arguments.
	 * @return array of separators, must not be null
	 */
	char[] complexSeparators();

	/**
	 * Returns the head of the tokens, list of strings in order of appearance
	 * @return list of strings
	 */
	List<String> head();

	/**
	 * Returns the tail of the tokens, key/value pairs.
	 * @return key/value pairs of present in command line
	 */
	List<Pair<String, String>> tail();

	/**
	 * Returns a beug representation of the object
	 * @return debug representation
	 */
	default String toDebug(){
		StrBuilder ret = new StrBuilder();
		ret
			.append("CL Tokenize, head=")
			.append(this.head().size())
			.append(", tail=")
			.append((this.tail()==null)?"<null>":this.tail().size())
			.appendNewLine()
		;
		if(this.head().size()>0){
			ret
				.append("    -> ")
				.appendWithSeparators(head(), "\n    -> ")
				.appendNewLine()
			;
		}
		if(this.tail().size()>0){
			for(Pair<String, String> pair : this.tail()){
				ret
					.append("    => ")
					.append(pair.getKey())
					.append(" := ")
					.append(pair.getValue())
				;
			}
		}
		ret.appendNewLine();
		return ret.build();
	}

	/**
	 * Tokenizes a command line.
	 * @param commandLine the command line, must not be blank
	 * @return tokenized command
	 * @throws IllegalStateException for all problems
	 */
	static Sh_CommandLineTokens create(String commandLine){
		return create(commandLine, new char[]{':', '='});
	}

	/**
	 * Tokenizes a command line.
	 * @param commandLine the command line, must not be blank
	 * @param separators array of separators for complex arguments, must not be null
	 * @return tokenized command
	 * @throws IllegalStateException for all problems
	 */
	static Sh_CommandLineTokens create(String commandLine, char[] separators){
		Validate.validState(StringUtils.isNotBlank(commandLine), "command line must not be blank");
		Validate.notNull(separators);

		StrTokenizer tokenizer = new StrTokenizer(commandLine, ' ');
		Validate.validState(tokenizer.size()>0, "no commands in command line");

		List<String> head = new ArrayList<>();
		List<Pair<String, String>> tail = new ArrayList<>();
		boolean inhead = true;
		for(String token : tokenizer.getTokenList()){
			if(!StringUtils.containsAny(token, separators)){
				if(inhead==true){
					head.add(token);
				}
				else{
					throw new IllegalStateException("found simple commands in complex argument list");
				}
			}
			else{
				inhead = false;
				StrTokenizer tk = new StrTokenizer(token);
				tk.setDelimiterMatcher(StrMatcher.charSetMatcher(':', '='));
				String[] arg = tk.getTokenArray();
				if(arg==null){
					throw new IllegalStateException("complex argument had no tokens");
				}
				else if(arg.length>2){
					throw new IllegalStateException("complex argument had more than 2 tokens");
				}
				else if(arg.length<2){
					throw new IllegalStateException("complex argument had less than 2 tokens");
				}
				else{
					if(StringUtils.isBlank(arg[0])){
						throw new IllegalStateException("complex argument, first token was blank");
					}
					if(StringUtils.isBlank(arg[1])){
						throw new IllegalStateException("complex argument, second token was blank");
					}
					tail.add(Pair.of(arg[0], arg[1]));
				}
			}
		}

		return new Sh_CommandLineTokens() {
			@Override
			public List<Pair<String, String>> tail() {
				return tail;
			}

			@Override
			public List<String> head() {
				return head;
			}

			@Override
			public char[] complexSeparators(){
				return separators;
			}
		};
	}
}
