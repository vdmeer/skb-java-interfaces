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

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.interfaces.MessageType;
import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.categories.has.HasDescription;
import de.vandermeer.skb.interfaces.categories.has.HasDisplayName;
import de.vandermeer.skb.interfaces.categories.has.HasName;
import de.vandermeer.skb.interfaces.categories.has.HasPrompt;
import de.vandermeer.skb.interfaces.categories.has.HasVersion;
import de.vandermeer.skb.interfaces.console.MessageConsole;
import de.vandermeer.skb.interfaces.console.NonBlockingReader;

/**
 * The base API of a shell with principal flow.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface IsShell extends CategoryIs, HasName, HasDisplayName, HasVersion, HasDescription, HasPrompt {

	/**
	 * Runs the shell with a particular reader.
	 * @param reader the reader for the shell
	 */
	default void runShell(BufferedReader reader){
		BufferedReader sysin = reader;
		if(sysin==null){
			MessageConsole.con(MessageType.ERROR, "{}: could not load standard input device (stdin)", this.getName());
			return;
		}

		this.setIsRunning(true);
		String in = "";
		while(isRunning()){
			int errno = 0;
			try{
				if(in!=null || "".equals(in) || "\n".equals(in)){
					MessageConsole.con(MessageType.INFO, this.prompt(), false);
				}
				in = sysin.readLine();
				if(!StringUtils.isBlank(in) && !in.startsWith("//") && !in.startsWith("#")){
					try{
						errno = this.runCommand(in);
						if(errno>0){
							this.setIsRunning(false);
						}
					}
					catch(IllegalStateException isex){
						MessageConsole.con(MessageType.ERROR, isex.getMessage());
//						isex.printStackTrace();
					}
				}
			}
			catch(IOException ignore) {
				this.setIsRunning(false);
				//TODO
				ignore.printStackTrace();
			}
			catch(RuntimeException ex){
				this.setIsRunning(false);
				//	TODO
				ex.printStackTrace();
			}
		}
		this.cleanup();
	}

	/**
	 * Parse a command line and execute command if possible.
	 * @param commandline the command line
	 * @return the return of the command execution as per {@link Sh_CmdBase#executeCommand()}
	 */
	int runCommand(String commandline);

	/**
	 * Cleanup before exiting.
	 */
	void cleanup();

	/**
	 * Runs the shell using a non-blocking standard input reader.
	 */
	default void runShell(){
		BufferedReader sysin = NonBlockingReader.getNbReader(1, 500, this);
		this.runShell(sysin);
	}

	/**
	 * tests if the shell is running.
	 * @return true if the shell is running (started), false otherwise.
	 */
	boolean isRunning();

	/**
	 * Sets the flag for a shell that is running (for instance in a separate thread).
	 * @param running new flag, true if shell is running, false otherwise
	 */
	void setIsRunning(boolean running);

	@Override
	default StrBuilder prompt(){
		StrBuilder ret = new StrBuilder(30);
		ret.append('[').append(this.getName()).append("]> ");
		return ret;
	}

}
