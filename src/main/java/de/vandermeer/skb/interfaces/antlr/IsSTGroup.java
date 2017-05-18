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

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.STGroupString;

import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.validators.STGValidator;

/**
 * Interface for objects that represent String Template Group (STG) objects.
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
	 * @return new object
	 */
	static IsSTGroup fromDir(final String dirname, final char delimiterStartChar, final char delimiterStopChar, final Map<String, String[]> expectedChunks){
		if(expectedChunks!=null){
			Validate.noNullElements(expectedChunks.keySet());
			Validate.noNullElements(expectedChunks.values());
		}

		final STGroupDir stg = new STGroupDir(dirname, delimiterStartChar, delimiterStopChar);
//		STGValidator.create().validate(stg, expectedChunks);

		return new IsSTGroup() {
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
	 * @return new object
	 */
	static IsSTGroup fromDir(final String dirname, final Map<String, String[]> expectedChunks){
		return fromDir(dirname, '<', '>', expectedChunks);
	}

	/**
	 * Creates a new object from ST templates read from a directory with default delimiters and no expected chunks.
	 * @param dirname the directory name, must not be blank
	 * @return new object
	 */
	static IsSTGroup fromDir(final String dirname){
		return fromDir(dirname, '<', '>', null);
	}

//	/**
//	 * Creates a new object from an STG file.
//	 * @param filename the file name, must not be blank
//	 * @param delimiterStartChar the start delimiter
//	 * @param delimiterStopChar the end delimiter
//	 * @param expectedChunks the expected chunks, null if not required
//	 * @return new object
//	 */
//	static IsSTGroup fromFile(final String filename, final char delimiterStartChar, final char delimiterStopChar, final Map<String, String[]> expectedChunks){
//		if(expectedChunks!=null){
//			Validate.noNullElements(expectedChunks.keySet());
//			Validate.noNullElements(expectedChunks.values());
//		}
//
//		final STGroupFile stg = new STGroupFile(filename, delimiterStartChar, delimiterStopChar);
//		STGValidator.create().validate(stg, expectedChunks);
//
//		return new IsSTGroup() {
//			@Override
//			public Map<String, String[]> getExpectedChunks() {
//				return expectedChunks;
//			}
//
//			@Override
//			public STGroup getSTGroup() {
//				return stg;
//			}
//		};
//	}

//	/**
//	 * Creates a new object from an STG file with default delimiters.
//	 * @param filename the file name, must not be blank
//	 * @param expectedChunks the expected STG chunks, null if none required, no null elements otherwise
//	 * @return new object
//	 */
//	static IsSTGroup fromFile(final String filename, final Map<String, String[]> expectedChunks){
//		return fromFile(filename, '<', '>', expectedChunks);
//	}

//	/**
//	 * Creates a new object from an STG file with default delimiters and no expected chunks.
//	 * @param filename the file name, must not be blank
//	 * @return new object
//	 */
//	static IsSTGroup fromFile(final String filename){
//		return fromFile(filename, '<', '>', null);
//	}

	/**
	 * Creates a new object from a string.
	 * @param sourceName the source name, must not be blank
	 * @param text the ST text, must not be blank
	 * @param delimiterStartChar the start delimiter
	 * @param delimiterStopChar the end delimiter
	 * @param expectedChunks the expected STG chunks, null if none required, no null elements otherwise
	 * @return new object
	 */
	static IsSTGroup fromString(final String sourceName, final String text, final char delimiterStartChar, final char delimiterStopChar, final Map<String, String[]> expectedChunks){
		Validate.notBlank(sourceName);
		Validate.notBlank(text);
		if(expectedChunks!=null){
			Validate.noNullElements(expectedChunks.keySet());
			Validate.noNullElements(expectedChunks.values());
		}

		final STGroupString stg = new STGroupString(sourceName, text, delimiterStartChar, delimiterStopChar);
//		STGValidator.create().validate(stg, expectedChunks);

		return new IsSTGroup() {
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
	 * @return new object
	 */
	static IsSTGroup fromString(final String sourceName, final String text, final Map<String, String[]> expectedChunks){
		return fromString(sourceName, text, '<', '>', expectedChunks);
	}

	/**
	 * Creates a new object from a string with default delimiters and no expected chunks.
	 * @param sourceName the source name, must not be blank
	 * @param text the ST text, must not be blank
	 * @return new object
	 */
	static IsSTGroup fromString(final String sourceName, final String text){
		return fromString(sourceName, text, '<', '>', null);
	}

	//TODO
	//STGroupFile(String fullyQualifiedFileName, String encoding) 
	//STGroupFile(String fullyQualifiedFileName, String encoding, char delimiterStartChar, char delimiterStopChar) 
	//STGroupFile(URL url, String encoding, char delimiterStartChar, char delimiterStopChar)

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
	 * Returns the STGroup object.
	 * @return STGroup object
	 */
	STGroup getSTGroup();
}
