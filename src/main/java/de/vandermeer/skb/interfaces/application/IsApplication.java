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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrBuilder;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.categories.has.HasDescription;
import de.vandermeer.skb.interfaces.transformers.textformat.Text_To_FormattedText;

/**
 * Base interface for an application with different option types and default argument parsing.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
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
	 * Adds options taken from an collection of objects.
	 * @param options object array, ignored if null, only application options will be taken
	 */
	default void addAllOptions(Iterable<?> options){
		if(options!=null){
			for(Object opt : options){
				this.addOption(opt);
			}
		}
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
			Apo_TypedE<?> eo = (Apo_TypedE<?>)option;
			for(Apo_TypedE<?> op : this.getEnvironmentOptions()){
				Validate.validState(
						!op.getEnvironmentKey().equals(eo.getEnvironmentKey()),
						this.getAppName() + ": environment option <" + eo.getEnvironmentKey() + "> already in use"
				);
			}
			this.getEnvironmentOptions().add(eo);
		}
		if(ClassUtils.isAssignable(option.getClass(), Apo_TypedP.class)){
			Apo_TypedP<?> po = (Apo_TypedP<?>)option;
			for(Apo_TypedP<?> op : this.getPropertyOptions()){
				Validate.validState(
						!op.getPropertyKey().equals(po.getPropertyKey()),
						this.getAppName() + ": property option <" + po.getPropertyKey() + "> already in use"
				);
			}
			this.getPropertyOptions().add(po);
		}
	}

	/**
	 * Prints a help screen for the application, to be used by an executing component.
	 */
	default void appHelpScreen(){
		STGroupFile stg = new STGroupFile("de/vandermeer/skb/interfaces/application/help.stg");
		ST st = stg.getInstanceOf("usage");
		st.add("appName", this.getAppName());
		st.add("appDisplayName", this.getAppDisplayName());
		st.add("appVersion", this.getAppVersion());
		st.add("appDescription", Text_To_FormattedText.left(this.getAppDescription(), this.getConsoleWidth()));
		st.add("required", CliOptionList.getRequired(getCLiALlOptions()));
		for(StrBuilder sb : this.getCliParser().usage(this.getConsoleWidth())){
			st.add("cliOptions", sb);
		}

		if(this.getEnvironmentOptions().size()>0){
			TreeMap<String, Apo_TypedE<?>> map = EnvironmentOptionsList.sortedMap(this.getEnvironmentOptions());
			Map<String, String> envMap = new LinkedHashMap<>();
			int length = 0;
			for(Apo_TypedE<?> te : map.values()){
				if(te.getEnvironmentKey().length()>length){
					length = te.getEnvironmentKey().length();
				}
				envMap.put(te.getEnvironmentKey(), te.getDescription());
			}
			length += 2;

			for(Entry<String, String> entry : envMap.entrySet()){
				StrBuilder argLine = new StrBuilder();
				argLine.append(entry.getKey()).appendPadding(length-argLine.length(), ' ').append("  - ");
				StrBuilder padLine = new StrBuilder();
				padLine.appendPadding(length+4, ' ');

				Collection<StrBuilder> text = Text_To_FormattedText.left(entry.getValue(), this.getConsoleWidth()-length);
				int i = 0;
				for(StrBuilder b : text){
					if(i==0){
						st.add("envOptions", argLine + b.build());
					}
					else{
						st.add("envOptions", padLine + b.build());
					}
					i++;
				}
			}
		}

		if(this.getPropertyOptions().size()>0){
			TreeMap<String, Apo_TypedP<?>> map = PropertyOptionsList.sortedMap(this.getPropertyOptions());
			Map<String, String> envMap = new LinkedHashMap<>();
			int length = 0;
			for(Apo_TypedP<?> te : map.values()){
				if(te.getPropertyKey().length()>length){
					length = te.getPropertyKey().length();
				}
				envMap.put(te.getPropertyKey(), te.getDescription());
			}
			length += 2;

			for(Entry<String, String> entry : envMap.entrySet()){
				StrBuilder argLine = new StrBuilder();
				argLine.append(entry.getKey()).appendPadding(length-argLine.length(), ' ').append("  - ");
				StrBuilder padLine = new StrBuilder();
				padLine.appendPadding(length+4, ' ');

				Collection<StrBuilder> text = Text_To_FormattedText.left(entry.getValue(), this.getConsoleWidth()-length);
				int i = 0;
				for(StrBuilder b : text){
					if(i==0){
						st.add("propOptions", argLine + b.build());
					}
					else{
						st.add("propOptions", padLine + b.build());
					}
					i++;
				}
			}
		}
		System.out.println(st.render());
	}

	/**
	 * Prints specific help for a command line option of the application.
	 * @param arg the command line argument specific help is requested for
	 */
	default void appHelpScreen(String arg){
		if(StringUtils.isBlank(arg)){
			return;
		}

		ApoBase opt = null;
		for(ApoBaseC cliOpt : this.getCLiALlOptions()){
			if(cliOpt.getCliShort()!=null){
				if(arg.equals(cliOpt.getCliShort().toString())){
					opt = cliOpt;
					break;
				}
			}
			if(arg.equals(cliOpt.getCliLong())){
				opt = cliOpt;
				break;
			}
		}
		if(opt==null){
			for(Apo_TypedP<?> propOpt : this.getPropertyOptions()){
				if(arg.equals(propOpt.getPropertyKey())){
					opt = propOpt;
					break;
				}
			}
		}

		if(opt==null){
			for(Apo_TypedE<?> envOpt : this.getEnvironmentOptions()){
				if(arg.equals(envOpt.getEnvironmentKey())){
					opt = envOpt;
					break;
				}
			}
		}

		if(opt==null){
			System.err.println(this.getAppName() + ": unknown option -> " + arg);//TODO Error Code
		}
		else{
			ST st = opt.getHelp();
			st.add("longDescr", this.longDescriptionString(opt.getLongDescription()));
			st.add("strongLine", new StrBuilder().appendPadding(this.getConsoleWidth(), '=' ));
			st.add("line", new StrBuilder().appendPadding(this.getConsoleWidth(), '-' ));
			System.out.println(st.render());
		}
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

		try{
			this.getCliParser().parse(args);
		}
		catch(IllegalStateException ex){
			System.err.println(this.getAppName() + ": error parsing command line -> " + ex.getMessage());
			System.err.println(this.getAppName() + ": try '--help' for list of CLI options or '--help <option>' for detailed help on a CLI option");
			return -1; 
		}
		catch (CliParseException e) {
			System.err.println(this.getAppName() + ": error: " + e.getMessage());
			System.err.println(this.getAppName() + ": try '--help' for list of CLI options or '--help <option>' for detailed help on a CLI option");
			return e.getErrorCode(); 
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
	 * Returns all environment options.
	 * @return all environment options, empty array if none added
	 */
	Set<Apo_TypedE<?>> getEnvironmentOptions();

	/**
	 * Returns all property options.
	 * @return all property options, empty array if none added
	 */
	Set<Apo_TypedP<?>> getPropertyOptions();

	/**
	 * Translates an option's long help object into a string.
	 * @param longDescription the original long description of an option to translate
	 * @return the options long help, null and blank strings mean no long help available
	 */
	String longDescriptionString(Object longDescription);
}
