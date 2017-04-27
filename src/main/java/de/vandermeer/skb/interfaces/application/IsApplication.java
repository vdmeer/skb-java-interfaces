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

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.categories.has.HasDescription;

/**
 * Base interface for an application with different option types and default argument parsing.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.2
 */
public interface IsApplication extends CategoryIs, HasDescription {

	/**
	 * Simple utility to test if a CLI option (short or long) is in an array.
	 * @param array the string array to test against
	 * @param apo the option to search for
	 * @return true if the option, short or long, is in the array, false otherwise
	 */
	public static boolean IN_ARRAY(String[] array, ApoBaseC apo){
		if(array==null || array.length==0 || apo==null){
			return false;
		}
		if(ArrayUtils.contains(array, "-" +  apo.getCliShort())){
			return true;
		}
		return ArrayUtils.contains(array, "--" + apo.getCliLong());
	}

	/**
	 * Adds options taken from an array of objects.
	 * @param options object array, ignored if null, only application options will be taken
	 */
	default void addAllOptions(Object[] options){
		if(options!=null){
			for(Object opt : options){
				this.addOption(opt);
			}
		}
	}

	/**
	 * Adds a new option to the application.
	 * Depending on its class, the option will be added to the environment options, the property options, and/or the CLI parser.
	 * If the class of the option is not supported, it will be ignored.
	 * Null values are ignored.
	 * @param option the option to be added, ignored if `null`
	 * @throws IllegalStateException if the option is already in use
	 */
	default void addOption(Object option){
		if(option==null){
			return;
		}
		if(ClassUtils.isAssignable(option.getClass(), Apo_SimpleC.class)){
			this.getCliParser().addOption((Apo_SimpleC)option);
		}
		if(ClassUtils.isAssignable(option.getClass(), Apo_TypedC.class)){
			this.getCliParser().addOption((Apo_TypedC<?>)option);
		}
		if(ClassUtils.isAssignable(option.getClass(), Apo_TypedE.class)){
			this.addOption((Apo_TypedE<?>)option);
		}
		if(ClassUtils.isAssignable(option.getClass(), Apo_TypedP.class)){
			this.addOption((Apo_TypedP<?>)option);
		}
	}

	/**
	 * Adds a new option to the application.
	 * @param option the option to be added, ignored if `null`
	 * @param <T> the option type, here a typed environment option
	 * @throws IllegalStateException if the option is already in use
	 */
	default <T extends Apo_TypedE<?>> void addOption(T option) throws IllegalStateException {
		if(option==null){
			return;
		}
		for(Apo_TypedE<?> op : this.getEnvironmentOptions()){
			Validate.validState(
					!op.getEnvironmentKey().equals(option.getEnvironmentKey()),
					this.getAppName() + ": environment option <" + option.getEnvironmentKey() + "> already in use"
			);
		}
		this.getEnvironmentOptions().add(option);
	}

	/**
	 * Adds a new option to the application.
	 * @param option the option to be added, ignored if `null`
	 * @param <T> the option type, here a typed property option
	 * @throws IllegalStateException if the option is already in use
	 */
	default <T extends Apo_TypedP<?>> void addOption(T option) throws IllegalStateException {
		if(option==null){
			return;
		}
		for(Apo_TypedP<?> op : this.getPropertyOptions()){
			Validate.validState(
					!op.getPropertyKey().equals(option.getPropertyKey()),
					this.getAppName() + ": property option <" + option.getPropertyKey() + "> already in use"
			);
		}
		this.getPropertyOptions().add(option);
	}

	/**
	 * Prints a help screen for the application, to be used by an executing component.
	 */
	default void appHelpScreen(){
//		System.out.println(this.getAppDisplayName() + " - " + this.getAppVersion());
//		if(this.getAppDescription()!=null){
//			System.out.println();
//			System.out.println(this.getAppDescription());
//		}
//		if(this.getCli()!=null){
//			System.out.println();
//			this.getCli().usage(this.getAppName());
//		}
//		System.out.println();
	}

	/**
	 * Prints specific help for a command line option of the application.
	 * @param arg the command line argument specific help is requested for
	 */
	default void appHelpScreen(String arg){
//		if(arg==null){
//			return;
//		}
//
//		ApplicationOption<?>[] options = this.getAppOptions();
//		if(options!=null){
//			boolean found = false;
//			for(ApplicationOption<?> opt : options){
//				if(opt.getCliOption()!=null){
//					if(arg.equals(opt.getCliOption().getOpt()) || arg.equals(opt.getCliOption().getLongOpt())){
//						System.out.println(opt.getCliLongHelp());
//						found = true;
//					}
//				}
//				else if(opt.getOptionKey()!=null){
//					if(arg.equals(opt.getOptionKey())){
//						System.out.println(opt.getKeyLongHelp());
//						found = true;
//					}
//				}
//			}
//			if(found==false){
//				System.err.println(this.getAppName() + ": unknown CLI argument / option key -> " + arg);
//			}
//		}
	}

	/**
	 * Returns the required help option.
	 * @return required help option, no help used if null, no typed option used if not null
	 */
	default Apo_SimpleC cliSimpleHelpOption(){
		return null;
	}

	/**
	 * Returns the required help option.
	 * @return required help option, no help used if null, ignored if simple help is used
	 */
	default Apo_TypedC<String> cliTypedHelpOption(){
		return null;
	}

	/**
	 * Returns the required version option.
	 * @return required version option, no version used if null
	 */
	default Apo_SimpleC cliVersionOption(){
		return null;
	}

	/**
	 * Executes the application.
	 * The default implementation will try to parse the command line with the application's CLI object and if that does not return success (0), call the help screen automatically.
	 * @param args arguments for execution
	 * @return 0 on success, negative integer on error, positive integer on no-error but exit application
	 */
	default int executeApplication(String[] args){
		//add help and version options if required
		if(this.cliSimpleHelpOption()!=null && !this.getCliParser().hasOption(this.cliSimpleHelpOption())){
			this.getCliParser().addOption(this.cliSimpleHelpOption());
		}
		else if(this.cliTypedHelpOption()!=null && !this.getCliParser().hasOption(this.cliTypedHelpOption())){
			this.getCliParser().addOption(this.cliTypedHelpOption());
		}
		if(this.cliVersionOption()!=null && !this.getCliParser().hasOption(this.cliVersionOption())){
			this.getCliParser().addOption(this.cliVersionOption());
		}

		if(IN_ARRAY(args, this.cliVersionOption())){
			System.out.println(this.getAppVersion());
			return 1;
		}
		if(IN_ARRAY(args, this.cliSimpleHelpOption())){
			this.appHelpScreen();
			return 1;
		}
		if(IN_ARRAY(args, this.cliTypedHelpOption())){
			if(args.length==1){
				this.appHelpScreen();
				return 1;
			}
			else if(args.length==2){
				this.appHelpScreen(args[1]);
				return 1;
			}
			System.err.println(this.getAppName() + ": help requested but too many arguments given");
			return -1;
		}

		IllegalStateException parserException = null;
		try{
			this.getCliParser().parse(args);
		}
		catch(IllegalStateException ex){
			parserException = ex;
		}

		if(parserException!=null){
			System.err.println(this.getAppName() + ": error parsing command line -> " + parserException.getMessage());
			System.err.println(this.getAppName() + ": try '--help' for list of CLI options or '--help <option>' for detailed help on a CLI option");
			return -1; 
		}

		return 0;
	}

	/**
	 * Returns a 1 line description of the application, should not be null.
	 * @return 1-line application description, mainly used in default help screen implementation
	 */
	String getAppDescription();

	/**
	 * Returns the display name of the application.
	 * This display name will be used for documentation and general user interaction.
	 * The default is the original application name.
	 * @return the application's display name, default is the application name returned by {@link #getAppName()}, must not be blank
	 */
	default String getAppDisplayName(){
		return this.getAppName();
	}

	/**
	 * Returns the name of the application, which is the name of the executable object for instance a script.
	 * This application name will be used for information and error messages and user interactions.
	 * @return application name, must not be blank
	 */
	String getAppName();

	/**
	 * Returns version information of the application for command line processing of the version option.
	 * @return application version, should not be null
	 */
	String getAppVersion();

	/**
	 * Returns all CLI options as base implementation.
	 * @return all CLI options, empty if none set
	 */
	default Set<ApoBaseC> getCLiALlOptions(){
		Set<ApoBaseC> ret = new HashSet<>();
		ret.addAll(this.getCliSimpleOptions());
		ret.addAll(this.getCliTypedOptions());
		return ret;
	}

	/**
	 * Returns the CLI parser.
	 * @return the CLI parser, must not be null
	 */
	App_CliParser getCliParser();

	/**
	 * Returns all CLI simple options.
	 * @return CLI simple options, empty if none set
	 */
	default Set<Apo_SimpleC> getCliSimpleOptions(){
		return this.getCliParser().getSimpleOptions();
	}

	/**
	 * Returns all CLI typed options.
	 * @return CLI typed options, empty if none set
	 */
	default Set<Apo_TypedC<?>> getCliTypedOptions(){
		return this.getCliParser().getTypedOptions();
	}

	@Override
	default String getDescription(){
		return this.getAppDescription();
	}

	/**
	 * Returns all environment options.
	 * @return all environment options, empty array if none added
	 */
	Set<Apo_TypedE<?>> getEnvironmentOptions();

	/**
	 * Returns all property options.
	 * @return all property options, empty array if none added
	 */
	Set<Apo_TypedP<?>> getPropertyOptions();
}
