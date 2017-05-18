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

import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;
import de.vandermeer.skb.interfaces.validators.STGValidator;

/**
 * String Template Group STG file loader.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface STG_FileLoader extends FileLoader {

	/**
	 * Creates a new STG file loader reading from file system first and as resource next.
	 * Start and end delimiters are set to default.
	 * No validation of the STG will be done.
	 * @param filename the STG filename, must not be blank
	 * @return a new file loader
	 */
	static STG_FileLoader create(String filename, Map<String, String[]> expectedChunks){
		return create(filename, new FileLocation[]{FileLocation.FILESYSTEM, FileLocation.RESOURCE}, null, null, expectedChunks, false);
	}

	/**
	 * Creates a new STG file loader reading from file system first and as resource next.
	 * Start and end delimiters are set to default.
	 * No validation of the STG will be done.
	 * @param filename the STG filename, must not be blank
	 * @return a new file loader
	 */
	static STG_FileLoader create(String filename, Map<String, String[]> expectedChunks, boolean unknownChunkIsError){
		return create(filename, new FileLocation[]{FileLocation.FILESYSTEM, FileLocation.RESOURCE}, null, null, expectedChunks, unknownChunkIsError);
	}

	/**
	 * Creates a new STG file loader reading from file system first and as resource next.
	 * Start and end delimiters are set to default.
	 * No validation of the STG will be done.
	 * @param filename the STG filename, must not be blank
	 * @return a new file loader
	 */
	static STG_FileLoader create(String filename){
		return create(filename, new FileLocation[]{FileLocation.FILESYSTEM, FileLocation.RESOURCE}, null, null, null, false);
	}

	/**
	 * Creates a new STG file loader.
	 * @param filename the STG filename, must not be blank
	 * @param locations the file locations, must not have null members
	 * @param startDelim the start delimiter, null means default
	 * @param endDelim the end delimiter, null means default
	 * @param expectedChunks chunks expected in the STG, null means no validation will be done
	 * @param unknownChunkIsError flag for STG chunk validation, use true to have unexpected chunks marked as error, false otherwise
	 * @return a new string file loader
	 */
	static STG_FileLoader create(String filename, FileLocation[] locations, Character startDelim, Character endDelim, Map<String, String[]> expectedChunks, boolean unknownChunkIsError){
		Validate.notBlank(filename);
		Validate.noNullElements(locations);

		STG_FileLoader ret = new STG_FileLoader() {
			protected final STG_FileSource source = STG_FileSource.create(filename, locations);
			protected final IsErrorSet errors = IsErrorSet.create();

			@Override
			public String getDescription() {
				return "Loads STG from a file.";
			}

			@Override
			public String getDisplayName() {
				return "STG File Loader";
			}

			@Override
			public String getName() {
				return "stg-loader";
			}

			@Override
			public IsErrorSet getErrorSet() {
				return this.errors;
			}

			@Override
			public STG_FileSource getSource() {
				return this.source;
			}

			@Override
			public Map<String, String[]> getExpectedChunks() {
				return expectedChunks;
			}

			@Override
			public Character startDelimiter() {
				return (startDelim==null)?'<':startDelim;
			}

			@Override
			public Character endDelimiter() {
				return (endDelim==null)?'>':endDelim;
			}

			@Override
			public boolean unknownChunkIsError() {
				return unknownChunkIsError;
			}
		};
		ret.validateSource();
		return ret;
	}

	@Override
	default STGroupFile read() {
		this.getErrorSet().clearMessages();
		if(this.validateSource()==false){
			//no valid source
			//TODO ERROR
			return null;
		}

		STGroupFile ret = null;
		if(this.getSource().getURL()!=null){
			ret = new STGroupFile(this.getSource().getURL(), "UTF-8", this.startDelimiter(), this.endDelimiter());
		}
		else{
			ret = new STGroupFile(this.getSource().getFilename(), this.startDelimiter(), this.endDelimiter());
		}
		if(this.getExpectedChunks()!=null){
			STGValidator stgv = STGValidator.create();
			if(!stgv.validate(ret, this.getExpectedChunks())){
				this.getErrorSet().addAll(stgv.getErrorSet());
				return null;
			}
		}
		return ret;
	}

	@Override
	STG_FileSource getSource();

	/**
	 * Returns the expected chunks for the STG.
	 * If chunks are provided, the loader will validate the loaded STG.
	 * If chunks is null, then the loader will not validate the STG.
	 * @return expected chunks, null if no validation required
	 */
	Map<String, String[]> getExpectedChunks();

	/**
	 * Returns the character for delimiter start.
	 * @return delimiter start character, null means using default
	 */
	Character startDelimiter();

	/**
	 * Returns the character for delimiter end.
	 * @return delimiter end character, null means using default
	 */
	Character endDelimiter();

	/**
	 * Returns a validation flag important when dealing with unknown chunks in an STG.
	 * @return true if unknown chunks are an error, false otherwise
	 */
	boolean unknownChunkIsError();

}
