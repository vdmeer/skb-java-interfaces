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

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrBuilder;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.skb.interfaces.MessageType;
import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.categories.has.HasDescription;
import de.vandermeer.skb.interfaces.categories.has.HasDisplayName;
import de.vandermeer.skb.interfaces.categories.has.HasErrNo;
import de.vandermeer.skb.interfaces.categories.has.HasName;
import de.vandermeer.skb.interfaces.categories.has.HasVersion;
import de.vandermeer.skb.interfaces.messages.HasMessageManager;
import de.vandermeer.skb.interfaces.messages.errors.Templates_AppStart;
import de.vandermeer.skb.interfaces.transformers.textformat.Text_To_FormattedText;

/**
 * Base interface for an application with different option types and default argument parsing.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.2
 */
public interface IsApplication extends CategoryIs, HasName, HasDisplayName, HasVersion, HasDescription, HasMessageManager, HasErrNo {

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
		this.getCliParser().getOptions().addAllOptions(options);
		this.getEnvironmentParser().getOptions().addAllOptions(options);
		this.getPropertyParser().getOptions().addAllOptions(options);
	}

	/**
	 * Adds options taken from an array of objects.
	 * @param options object array, ignored if null, only application options will be taken
	 */
	default void addAllOptions(Object[] options){
		this.getCliParser().getOptions().addAllOptions(options);
		this.getEnvironmentParser().getOptions().addAllOptions(options);
		this.getPropertyParser().getOptions().addAllOptions(options);
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
		this.getCliParser().getOptions().addOption(option);
		this.getEnvironmentParser().getOptions().addOption(option);
		this.getPropertyParser().getOptions().addOption(option);
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
		if(this.cliSimpleHelpOption()!=null && !this.getCliParser().getOptions().hasOption(this.cliSimpleHelpOption())){
			this.getCliParser().getOptions().addOption(this.cliSimpleHelpOption());
		}
		else if(this.cliTypedHelpOption()!=null && !this.getCliParser().getOptions().hasOption(this.cliTypedHelpOption())){
			this.getCliParser().getOptions().addOption(this.cliTypedHelpOption());
		}
		if(this.cliVersionOption()!=null && !this.getCliParser().getOptions().hasOption(this.cliVersionOption())){
			this.getCliParser().getOptions().addOption(this.cliVersionOption());
		}

		if(IN_ARGUMENTS(args, this.cliVersionOption())){
			System.out.println(this.getVersion());
			this.getMsgManager().setErrNo(1);
		}
		else if(IN_ARGUMENTS(args, this.cliSimpleHelpOption())){
			this.helpScreen();
			this.getMsgManager().setErrNo(1);
		}
		else if(IN_ARGUMENTS(args, this.cliTypedHelpOption())){
			if(args.length==1){
				this.helpScreen();
				this.getMsgManager().setErrNo(1);
			}
			else if(args.length==2){
				ApoBase opt = this.getOption(args[1]);
				if(opt==null){
					this.getMsgManager().add(Templates_AppStart.HELP_UKNOWN_OPTION.getError(this.getName(), args[1]));
				}
				else{
					this.helpScreen(opt);
					this.getMsgManager().setErrNo(1);
				}
			}
			else{
				//TODO too many help options
			}
		}

		if(this.getErrNo()!=0){
			return;
		}

		this.getMsgManager().setErrNo(0);
		this.getCliParser().parse(args);
		if(this.getCliParser().getErrNo()!=0){
			this.getMsgManager().add(this.getCliParser());
		}

		if(this.getErrNo()<0){
			if(this.cliSimpleHelpOption()!=null){
				this.getMsgManager().add(MessageType.INFO, "try '--help' for list of CLI options");
			}
			if(this.cliTypedHelpOption()!=null){
				this.getMsgManager().add(MessageType.INFO, "try '--help' for list of CLI options or '--help <option>' for detailed help on a CLI option");
			}
		}
		else{
			this.getEnvironmentParser().parse();
			if(this.getEnvironmentParser().getErrNo()<0){
				this.getMsgManager().add(this.getEnvironmentParser());
			}
		}

		if(this.getErrNo()==0){
			this.runApplication();
		}

		this.getMsgManager().printMessagesConsole();
	}

	/**
	 * Returns the CLI parser.
	 * @return the CLI parser, must not be null
	 */
	ApoCliParser getCliParser();

	/**
	 * Returns the width of the console window, printable columns.
	 * @return width of the console window, default is 80
	 */
	default int getConsoleWidth(){
		return 80;
	}

	/**
	 * Returns the environment parser.
	 * @return the environment parser, must not be null
	 */
	ApoEnvParser getEnvironmentParser();

	/**
	 * Returns the number of the last error, 0 if none occurred.
	 * @return last error number
	 */
	default int getErrNo(){
		return this.getMsgManager().getErrNo();
	}

	/**
	 * Finds and returns an option by name from CLI, property, and environment options.
	 * @param name the name, blank strings result in `null` return
	 * @return the option as base option if found, null if not found
	 */
	default ApoBase getOption(String name){
		ApoBase opt = null;
		if(!StringUtils.isBlank(name)){
			for(ApoBaseC cliOpt : this.getCliParser().getOptions().getSet()){
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
	ApoPropParser getPropertyParser();

	/**
	 * Prints a help screen for the application, to be used by an executing component.
	 */
	default void helpScreen(){
		STGroupFile stg = new STGroupFile("de/vandermeer/skb/interfaces/application/help.stg");
		ST st = stg.getInstanceOf("usage");
		st.add("appName", this.getName());
		st.add("appDisplayName", this.getDisplayName());
		st.add("appVersion", this.getVersion());
		st.add("appDescription", Text_To_FormattedText.left(this.getDescription(), this.getConsoleWidth()));
		st.add("requiredCliOptions", this.getCliParser().getOptions().getRequired());

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
	 * The main method for the application, called after {@link #executeApplication(String[])} is finished
	 */
	void runApplication();

	/**
	 * Translates an option's long help object into a string.
	 * @param longDescription the original long description of an option to translate
	 * @return the options long help, null and blank strings mean no long help available
	 */
	String translateLongDescription(Object longDescription);

	/**
	 * Validates an application, throwing {@link IllegalStateException} exceptions if validation failed
	 * @throws IllegalStateException if the validation failed
	 */
	default void validate(){
		Validate.validState(!StringUtils.isBlank(this.getName()), "application name must be set");
		Validate.validState(!StringUtils.isBlank(this.getDescription()), "description must be not blank");
		Validate.validState(!StringUtils.isBlank(this.getDisplayName()), "application display name must be not blank");
		Validate.validState(!StringUtils.isBlank(this.getVersion()), "application version must be not blank");

		Validate.validState(this.getMsgManager()!=null, "MessageManager must not be null");

		Validate.validState(this.getCliParser()!=null, "CLI parser must be set");
		Validate.validState(this.getCliParser().getOptions()!=null, "CLI parser provide non-null options");
		Validate.validState(this.getEnvironmentParser()!=null, "Environment parser must be set");
		Validate.validState(this.getEnvironmentParser().getOptions()!=null, "Environment parser provide non-null options");
		Validate.validState(this.getPropertyParser()!=null, "Property parser must be set");
		Validate.validState(this.getPropertyParser().getOptions()!=null, "Property parser provide non-null options");
	}

}
