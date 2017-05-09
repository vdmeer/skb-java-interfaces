/* Copyright 2016 Sven van der Meer <vdmeer.sven@mykolab.com>
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

package de.vandermeer.skb.interfaces.antlr;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.STGroupString;

import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.messagesets.errors.IsError;
import de.vandermeer.skb.interfaces.messagesets.errors.Templates_STG;

/**
 * Interface for objects that represent String Template Group (STG) objects
 * (for which one can check chunks, get name, etc).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface IsSTGroup extends CategoryIs {

	/**
	 * Creates a new object from ST templates read from a directory.
	 * @param dirname the directory name, must not be blank
	 * @param delimiterStartChar the start delimiter
	 * @param delimiterStopChar the end delimiter
	 * @param expectedChunks the expected STG chunks, null if none required, no null elements otherwise
	 * @param appName the name of the application (or object/class) using this object
	 * @return new object
	 */
	static IsSTGroup fromDir(final String dirname, final char delimiterStartChar, final char delimiterStopChar, final Map<String, String[]> expectedChunks, final String appName){
		Validate.notBlank(appName);
		if(expectedChunks!=null){
			Validate.noNullElements(expectedChunks.keySet());
			Validate.noNullElements(expectedChunks.values());
		}

		final IsSTErrorListener errorListener = IsSTErrorListener.create(appName);
		final STGroupDir stg = new STGroupDir(dirname, delimiterStartChar, delimiterStopChar);
		stg.setListener(errorListener);
		stg.load();
		Validate.notNull(stg);

		return new IsSTGroup() {
			@Override
			public String getAppName() {
				return appName;
			}

			@Override
			public IsSTErrorListener getErrorListener() {
				return errorListener;
			}

			@Override
			public Map<String, String[]> getExpectedChunks() {
				return expectedChunks;
			}

			@Override
			public STGroup getSTGroup() {
				return stg;
			}
		};
	}

	/**
	 * Creates a new object from ST templates read from a directory with default delimiters.
	 * @param dirname the directory name, must not be blank
	 * @param expectedChunks the expected STG chunks, null if none required, no null elements otherwise
	 * @param appName the name of the application (or object/class) using this object
	 * @return new object
	 */
	static IsSTGroup fromDir(final String dirname, final Map<String, String[]> expectedChunks, final String appName){
		return fromDir(dirname, '<', '>', expectedChunks, appName);
	}

	/**
	 * Creates a new object from ST templates read from a directory with default delimiters and no expected chunks.
	 * @param dirname the directory name, must not be blank
	 * @param appName the name of the application (or object/class) using this object
	 * @return new object
	 */
	static IsSTGroup fromDir(final String dirname, final String appName){
		return fromDir(dirname, '<', '>', null, appName);
	}

	/**
	 * Creates a new object from an STG file.
	 * @param filename the file name, must not be blank
	 * @param delimiterStartChar the start delimiter
	 * @param delimiterStopChar the end delimiter
	 * @param expectedChunks the expected chunks, null if not required
	 * @param appName the name of the application (or object/class) using this object
	 * @return new object
	 */
	static IsSTGroup fromFile(final String filename, final char delimiterStartChar, final char delimiterStopChar, final Map<String, String[]> expectedChunks, final String appName){
		Validate.notBlank(appName);
		if(expectedChunks!=null){
			Validate.noNullElements(expectedChunks.keySet());
			Validate.noNullElements(expectedChunks.values());
		}

		final IsSTErrorListener errorListener = IsSTErrorListener.create(appName);
		final STGroupFile stg = new STGroupFile(filename, delimiterStartChar, delimiterStopChar);
		stg.setListener(errorListener);
		stg.load();
		Validate.notNull(stg);

		return new IsSTGroup() {
			@Override
			public String getAppName() {
				return appName;
			}

			@Override
			public IsSTErrorListener getErrorListener() {
				return errorListener;
			}

			@Override
			public Map<String, String[]> getExpectedChunks() {
				return expectedChunks;
			}

			@Override
			public STGroup getSTGroup() {
				return stg;
			}
		};
	}

	/**
	 * Creates a new object from an STG file with default delimiters.
	 * @param filename the file name, must not be blank
	 * @param expectedChunks the expected STG chunks, null if none required, no null elements otherwise
	 * @param appName the name of the application (or object/class) using this object
	 * @return new object
	 */
	static IsSTGroup fromFile(final String filename, final Map<String, String[]> expectedChunks, final String appName){
		return fromFile(filename, '<', '>', expectedChunks, appName);
	}

	/**
	 * Creates a new object from an STG file with default delimiters and no expected chunks.
	 * @param filename the file name, must not be blank
	 * @param appName the name of the application (or object/class) using this object
	 * @return new object
	 */
	static IsSTGroup fromFile(final String filename, final String appName){
		return fromFile(filename, '<', '>', null, appName);
	}

	/**
	 * Creates a new object from a string.
	 * @param sourceName the source name, must not be blank
	 * @param text the ST text, must not be blank
	 * @param delimiterStartChar the start delimiter
	 * @param delimiterStopChar the end delimiter
	 * @param expectedChunks the expected STG chunks, null if none required, no null elements otherwise
	 * @param appName the name of the application (or object/class) using this object
	 * @return new object
	 */
	static IsSTGroup fromString(final String sourceName, final String text, final char delimiterStartChar, final char delimiterStopChar, final Map<String, String[]> expectedChunks, final String appName){
		Validate.notBlank(sourceName);
		Validate.notBlank(text);
		Validate.notBlank(appName);
		if(expectedChunks!=null){
			Validate.noNullElements(expectedChunks.keySet());
			Validate.noNullElements(expectedChunks.values());
		}

		final IsSTErrorListener errorListener = IsSTErrorListener.create(appName);
		final STGroupString stg = new STGroupString(sourceName, text, delimiterStartChar, delimiterStopChar);
		stg.setListener(errorListener);
		stg.load();
		Validate.notNull(stg);

		return new IsSTGroup() {
			@Override
			public String getAppName() {
				return appName;
			}

			@Override
			public IsSTErrorListener getErrorListener() {
				return errorListener;
			}

			@Override
			public Map<String, String[]> getExpectedChunks() {
				return expectedChunks;
			}

			@Override
			public STGroup getSTGroup() {
				return stg;
			}
		};
	}

	/**
	 * Creates a new object from a string with default delimiters.
	 * @param sourceName the source name, must not be blank
	 * @param text the ST text, must not be blank
	 * @param expectedChunks the expected STG chunks, null if none required, no null elements otherwise
	 * @param appName the name of the application (or object/class) using this object
	 * @return new object
	 */
	static IsSTGroup fromString(final String sourceName, final String text, final Map<String, String[]> expectedChunks, final String appName){
		return fromString(sourceName, text, '<', '>', expectedChunks, appName);
	}

	/**
	 * Creates a new object from a string with default delimiters and no expected chunks.
	 * @param sourceName the source name, must not be blank
	 * @param text the ST text, must not be blank
	 * @param appName the name of the application (or object/class) using this object
	 * @return new object
	 */
	static IsSTGroup fromString(final String sourceName, final String text, final String appName){
		return fromString(sourceName, text, '<', '>', null, appName);
	}

	//TODO
	//STGroupFile(String fullyQualifiedFileName, String encoding) 
	//STGroupFile(String fullyQualifiedFileName, String encoding, char delimiterStartChar, char delimiterStopChar) 
	//STGroupFile(URL url, String encoding, char delimiterStartChar, char delimiterStopChar)

	/**
	 * An application (or object or class) name for error messages.
	 * @return the application name, must not be blank
	 */
	String getAppName();

	/**
	 * Returns the group's error listener.
	 * @return error listener, must not be null
	 */
	IsSTErrorListener getErrorListener();

	/**
	 * Returns the chunks expected to be defined in the STGroup object.
	 * @return expected chunks as mapping from method name to set of method arguments
	 */
	Map<String, String[]> getExpectedChunks();

	//TODO
	//STGroupDir(String dirName, String encoding) 
	//STGroupDir(String dirName, String encoding, char delimiterStartChar, char delimiterStopChar) 
	//STGroupDir(URL root, String encoding, char delimiterStartChar, char delimiterStopChar) 

	/**
	 * Returns the name of the STGroup object (file name, source name, or group directory name).
	 * @return STGroup name, null if {@link #getSTGroup()} was null or if no name was found
	 */
	default String getGroupName(){
		STGroup stg = this.getSTGroup();
		String ret = null;

		if(stg instanceof STGroupFile){
			ret = ((STGroupFile)stg).fileName;
		}
		else if(stg instanceof STGroupString){
			ret = ((STGroupString)stg).sourceName;
		}
		else if(stg instanceof STGroupDir){
			ret = ((STGroupDir)stg).groupDirName;
		}
		return (ret==null)?ret:StringUtils.substringBeforeLast(ret, ".");
	}

	/**
	 * Creates a new object from ST templates read from a directory.
	 * @param dirname the directory name, must not be blank
	 * @param delimiterStartChar the start delimiter
	 * @param delimiterStopChar the end delimiter
	 * @param expectedChunks the expected STG chunks, null if none required, no null elements otherwise
	 * @param appName the name of the application (or object/class) using this object
	 * @return new object
	 */

	/**
	 * Returns the STGroup object.
	 * @return STGroup object
	 */
	STGroup getSTGroup();

	/**
	 * Validates the STGroup for expected chunks.
	 * @return a set of error messages, empty if no errors found, null if no expected chunks where set or no STGroup was set
	 */
	default Set<IsError> validate(){
		STGroup stg = this.getSTGroup();
		Validate.notNull(stg);

		Set<IsError> ret = new LinkedHashSet<>();
		if(this.getErrorListener().getErrors().size()>0){
			ret.addAll(this.getErrorListener().getErrors());
			return ret;
		}

		for(Entry<String, String[]> entry : this.getExpectedChunks().entrySet()){
			if(entry.getKey()!=null && !"".equals(entry.getKey())){
				if(stg.isDefined(entry.getKey())){
					ST st = stg.getInstanceOf(entry.getKey());
					if(st==null){
						ret.add(Templates_STG.ST_IS_NULL.getError("app", this.getGroupName(), entry.getValue()));
					}
					else{
						IsST isst = IsST.create(st, entry.getValue(), this.getAppName());
						Set<IsError> errors = isst.validate();
						if(errors.size()>0){
							ret.add(Templates_STG.MISSING_ST_ARG_ERRORS.getError("app", this.getGroupName(), entry.getValue()));
							ret.addAll(errors);
						}
					}
				}
				else{
					ret.add(Templates_STG.MISSING_EXPECTED_ST.getError("app", this.getGroupName(), entry.getValue()));
				}
			}
		}
		return ret;
	}
}
