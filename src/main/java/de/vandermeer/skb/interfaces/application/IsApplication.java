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

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.categories.has.HasDescription;
import de.vandermeer.skb.interfaces.messagesets.HasErrorSet;
import de.vandermeer.skb.interfaces.messagesets.HasInfoSet;
import de.vandermeer.skb.interfaces.messagesets.HasWarningSet;
import de.vandermeer.skb.interfaces.messagesets.IsErrorSet_IsError;
import de.vandermeer.skb.interfaces.messagesets.IsInfoSet_FT;
import de.vandermeer.skb.interfaces.messagesets.IsWarningSet_FT;
import de.vandermeer.skb.interfaces.messagesets.errors.Templates_AppStart;
import de.vandermeer.skb.interfaces.transformers.textformat.Text_To_FormattedText;

/**
 * Base interface for an application with different option types and default argument parsing.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.2
 */
public interface IsApplication extends CategoryIs, HasDescription, HasErrorSet<IsErrorSet_IsError>, HasWarningSet<IsWarningSet_FT>, HasInfoSet<IsInfoSet_FT> {

	/**
	 * Simple utility to test if a CLI option (short or long) is in an array.
	 * @param array the string array to test against
	 * @param apo the option to search for
	 * @return true if the option, short or long, is in the array, false otherwise
	 */
	public static boolean IN_ARGUMENTS(String[] array, ApoBaseC apo){
		if(array==null || array.length==0 || apo==null){
			return false;
		}
		if(ArrayUtils.contains(array, "-" +  apo.getCliShort())){
			return true;
		}
		return ArrayUtils.contains(array, "--" + apo.getCliLong());
	}

	/**
	 * Adds options taken from an collection of objects.
	 * @param options object array, ignored if null, only application options will be taken
	 */
	default void addAllOptions(Iterable<?> options){
		this.getCliParser().addAllOptions(options);
		this.getEnvironmentParser().addAllOptions(options);
		this.getPropertyParser().addAllOptions(options);
	}

	/**
	 * Adds options taken from an array of objects.
	 * @param options object array, ignored if null, only application options will be taken
	 */
	default void addAllOptions(Object[] options){
		this.getCliParser().addAllOptions(options);
		this.getEnvironmentParser().addAllOptions(options);
		this.getPropertyParser().addAllOptions(options);
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
		this.getCliParser().addOption(option);
		this.getEnvironmentParser().addOption(option);
		this.getPropertyParser().addOption(option);
	}

	/**
	 * Prints a help screen for the application, to be used by an executing component.
	 */
	default void helpScreen(){
		STGroupFile stg = new STGroupFile("de/vandermeer/skb/interfaces/application/help.stg");
		ST st = stg.getInstanceOf("usage");
		st.add("appName", this.getAppName());
		st.add("appDisplayName", this.getAppDisplayName());
		st.add("appVersion", this.getAppVersion());
		st.add("appDescription", Text_To_FormattedText.left(this.getAppDescription(), this.getConsoleWidth()));
		st.add("requiredCliOptions", CliOptionList.getRequired(getCLiALlOptions()));

		for(StrBuilder sb : this.getCliParser().usage(this.getConsoleWidth())){
			st.add("cliOptions", sb);
		}
		for(StrBuilder sb : this.getEnvironmentParser().usage(this.getConsoleWidth())){
			st.add("envOptions", sb);
		}
		for(StrBuilder sb : this.getPropertyParser().usage(this.getConsoleWidth())){
			st.add("propOptions", sb);
		}

		System.out.println(st.render());
	}

	/**
	 * Prints specific help for a command line option of the application.
	 * @param opt the option specific help is requested for
	 */
	default void helpScreen(ApoBase opt){
		if(opt==null){
			return;
		}

		ST st = opt.getHelp();
		st.add("longDescr", this.translateLongDescription(opt.getLongDescription()));
		st.add("lineTop", new StrBuilder().appendPadding(this.getConsoleWidth(), '=' ));
		st.add("lineBottom", new StrBuilder().appendPadding(this.getConsoleWidth(), '=' ));
		st.add("lineMid", new StrBuilder().appendPadding(this.getConsoleWidth(), '-' ));
		System.out.println(st.render());
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
	 * Error and information messages will be added to the respective sets.
	 * An error code will be set in case an error occurred.
	 * @param args arguments for execution
	 */
	default void executeApplication(String[] args){
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

		if(IN_ARGUMENTS(args, this.cliVersionOption())){
			System.out.println(this.getAppVersion());
			this.setErrno(1);
		}
		else if(IN_ARGUMENTS(args, this.cliSimpleHelpOption())){
			this.helpScreen();
			this.setErrno(1);
		}
		else if(IN_ARGUMENTS(args, this.cliTypedHelpOption())){
			if(args.length==1){
				this.helpScreen();
				this.setErrno(1);
			}
			else if(args.length==2){
				ApoBase opt = this.getOption(args[1]);
				if(opt==null){
					this.getErrorSet().addError(Templates_AppStart.HELP_UKNOWN_OPTION.getError(this.getAppName(), args[1]));
					this.setErrno(Templates_AppStart.HELP_UKNOWN_OPTION.getCode());
				}
				else{
					this.helpScreen(opt);
					this.setErrno(1);
				}
			}
			else{
				//TODO too many help options
			}
		}

		if(this.getErrNo()!=0){
			return;
		}

		this.setErrno(0);
		this.getCliParser().parse(args);
		if(this.getCliParser().getErrNo()!=0){
			this.getErrorSet().addAllErrors(this.getCliParser().getErrorSet().getErrorMessages());
			this.setErrno(this.getCliParser().getErrNo());
		}

		if(this.getErrNo()<0){
			if(this.cliSimpleHelpOption()!=null){
				this.getInfoSet().addInfo("{}: try '--help' for list of CLI options", this.getAppName());
			}
			if(this.cliTypedHelpOption()!=null){
				this.getInfoSet().addInfo("{}: try '--help' for list of CLI options or '--help <option>' for detailed help on a CLI option", this.getAppName());
			}

			System.err.println(this.getErrorSet().render());
			if(this.getWarningSet().hasWarnings()){
				System.out.println();
				System.out.println(this.getWarningSet().render());
			}
			if(this.getInfoSet().hasInformation()){
				System.out.println();
				System.out.println(this.getInfoSet().render());
			}
		}
		else{
			this.getEnvironmentParser().parse();
		}
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
		return this.getCliParser().getAllOptions();
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

	/**
	 * Returns the width of the console window, printable columns.
	 * @return width of the console window, default is 80
	 */
	default int getConsoleWidth(){
		return 80;
	}

	@Override
	default String getDescription(){
		return this.getAppDescription();
	}

	/**
	 * Returns the environment parser.
	 * @return the environment parser, must not be null
	 */
	App_EnvironmentParser getEnvironmentParser();

	/**
	 * Returns the number of the last error, 0 if none occurred.
	 * @return last error number
	 */
	int getErrNo();

	/**
	 * Finds and returns an option by name from CLI, property, and environment options.
	 * @param name the name, blank strings result in `null` return
	 * @return the option as base option if found, null if not found
	 */
	default ApoBase getOption(String name){
		ApoBase opt = null;
		if(!StringUtils.isBlank(name)){
			for(ApoBaseC cliOpt : this.getCLiALlOptions()){
				if(cliOpt.getCliShort()!=null){
					if(name.equals(cliOpt.getCliShort().toString())){
						opt = cliOpt;
						break;
					}
				}
				if(cliOpt.getCliLong()!=null){
					if(name.equals(cliOpt.getCliLong())){
						opt = cliOpt;
						break;
					}
				}
			}
			if(opt==null){
				for(Apo_TypedP<?> propOpt : this.getPropertyParser().getOptions().getValues()){
					if(name.equals(propOpt.getPropertyKey())){
						opt = propOpt;
						break;
					}
				}
			}

			if(opt==null){
				for(Apo_TypedE<?> envOpt : this.getEnvironmentParser().getOptions().getValues()){
					if(name.equals(envOpt.getEnvironmentKey())){
						opt = envOpt;
						break;
					}
				}
			}
		}
		return opt;
	}

	/**
	 * Returns the property parser.
	 * @return the property parser, must not be null
	 */
	App_PropertyParser getPropertyParser();

	/**
	 * Sets an error number.
	 * @param errorNumber the new number for the error
	 */
	void setErrno(int errorNumber);

	/**
	 * Translates an option's long help object into a string.
	 * @param longDescription the original long description of an option to translate
	 * @return the options long help, null and blank strings mean no long help available
	 */
	String translateLongDescription(Object longDescription);

}
