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

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

/**
 * Base for a CLI option.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.2
 */
public interface ApoBaseC extends ApoBase {

	/**
	 * Tests if the CLI option is required meaning it must be used in the command line.
	 * @return true if required, false otherwise
	 */
	boolean cliIsRequired();

	/**
	 * Returns the long CLI option.
	 * @return long CLI option, must not be blank
	 */
	String getCliLong();

	/**
	 * Returns the short CLI option.
	 * @return short CLI option, null if none set
	 */
	Character getCliShort();

	@Override
	default ST getHelp(){
		STGroupFile stg = new STGroupFile(ApoBase.STG_FILE);
		ST cliST = stg.getInstanceOf("cli");
		cliST.add("cliShort", this.getCliShort());
		cliST.add("cliLong", this.getCliLong());
		cliST.add("cliRequired", this.cliIsRequired());

		ST st = ApoBase.super.getHelp();
		st.add("cli", cliST);
		return st;
	}

	/**
	 * Tests if the option was present in a parsed command line.
	 * @return true if it was present, false otherwise
	 */
	boolean inCli();

	/**
	 * Sets the option flag for being in a command line.
	 * @param true if in a command line, false otherwise
	 */
	void setInCLi(boolean inCli);

	@Override
	default void validate() throws IllegalStateException {
		ApoBase.super.validate();
	}
}
