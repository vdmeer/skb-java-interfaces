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

package de.vandermeer.skb.interfaces.fidibus.files;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.text.StrSubstitutor;

import de.vandermeer.skb.interfaces.application.ApplicationException;
import de.vandermeer.skb.interfaces.messages.errors.Templates_ExceptionRuntimeUnexpected;
import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;

/**
 * API for a file executor, an object that executes an external command.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface FileExecutor extends FileLoader {

	/**
	 * Creates a new file executor.
	 * @param source the source, must not be null
	 * @param simulate simulation flag, true for simulation, false otherwise
	 * @param arguments an optional string of arguments, ignored if blank
	 * @return the new file executor with validated source (errors are in error set)
	 */
	static FileExecutor create(final ExecutableSource source, final boolean simulate, final String arguments){
		Validate.notNull(source);

		FileExecutor ret = new FileExecutor() {
			protected final IsErrorSet errors = IsErrorSet.create();
			protected final StrBuilder command = new StrBuilder(arguments);

			@Override
			public String getDescription() {
				return "Executes an external command";
			}

			@Override
			public String getDisplayName() {
				return "File Executor";
			}

			@Override
			public String getName() {
				return "executor";
			}

			@Override
			public IsErrorSet getErrorSet() {
				return this.errors;
			}

			@Override
			public boolean simulate() {
				return simulate;
			}

			@Override
			public ExecutableSource getSource() {
				return source;
			}

			@Override
			public String buildCommand(StrSubstitutor subst) {
				String command = this.getSource().getFilename() + " " + this.command.build();
				if(subst!=null){
					return subst.replace(command);
				}
				return command;
			}

			@Override
			public void addCommand(final String argument) {
				if(!StringUtils.isBlank(argument)){
					this.command.append(' ').append(argument);
				}
			}

			@Override
			public void addCommand(String... arguments) {
				if(arguments!=null){
					this.command.append(' ').appendAll(arguments);
				}
			}
		};
		ret.validateSource();
		return ret;
	}

	/**
	 * Returns the simulation flag.
	 * @return if true, no external command will be executed (execution is only simulated), if false external execution will happen
	 */
	boolean simulate();

	/**
	 * Returns the command build from executable and commands.
	 * @param subst a string substituter for substitution that should be made on the arguments before execution
	 * @return the build command, null if not a valid executor
	 */
	String buildCommand(StrSubstitutor subst);

	/**
	 * Returns the command build from executable and commands.
	 * @return the build command, null if not a valid executor
	 */
	default String buildCommand(){
		return this.buildCommand(null);
	}

	/**
	 * Executes the given executables and manages exception.
	 * @return true if execution was successful (or simulated), false on error with errors in the local error set
	 */
	@Override
	default Boolean read(){
		try{
			return this.runCommand();
		}
		catch(ApplicationException ex){
			this.getErrorSet().add(ex.getMessage());
			this.getErrorSet().setErrNo(ex.getErrorCode());
			return false;
		}
	}

	/**
	 * Executes the external command.
	 * The execution will not happen if the executor is not valid (return false) or if it is in simulation mode (return true).
	 * @return `true` on success (or in validation mode), false if not valid
	 * @throws ApplicationException for any exception caught during execution, for instance IO or Interrupted
	 */
	default boolean runCommand() throws ApplicationException{
		return this.runCommand(null);
	}

	/**
	 * Executes the external command.
	 * The execution will not happen if the executor is not valid (return false) or if it is in simulation mode (return true).
	 * @param subst a string substituter for substitution that should be made on the arguments before execution
	 * @return `true` on success (or in validation mode), false if not valid
	 * @throws ApplicationException for any exception caught during execution, for instance IO or Interrupted
	 */
	default boolean runCommand(StrSubstitutor subst) throws ApplicationException{
		this.getErrorSet().clearMessages();;
		if(this.validateSource()==false){
			//no valid source
			return false;
		}
		if(this.simulate()){
			return true;
		}

		try{
			Process p = Runtime.getRuntime().exec(this.buildCommand(subst));
			p.waitFor();
		}
		catch (IOException e) {
			throw new ApplicationException(
					Templates_ExceptionRuntimeUnexpected.U_IO,
					"exec", "executing Inkscape", e.getMessage()
			);
		}
		catch (InterruptedException e) {
			throw new ApplicationException(
					Templates_ExceptionRuntimeUnexpected.U_INTERRUPTED,
					"exec", "executing Inkscape", e.getMessage()
			);
		}
		return true;
	}

	@Override
	ExecutableSource getSource();

	/**
	 * Adds an argument to the command.
	 * @param argument additional argument, ignored if blank, otherwise simply added to the command
	 */
	void addCommand(String argument);

	/**
	 * Adds an argument to the command which in turn is constructed ofa set of strings
	 * @param arguments additional argument constructed of a set of strings
	 */
	void addCommand(String ... arguments);

}
