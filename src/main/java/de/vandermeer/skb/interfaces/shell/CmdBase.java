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

import de.vandermeer.skb.interfaces.categories.has.HasDescription;
import de.vandermeer.skb.interfaces.categories.has.HasDisplayName;
import de.vandermeer.skb.interfaces.categories.has.HasLongDescription;
import de.vandermeer.skb.interfaces.categories.has.HasName;

/**
 * Base API of a command.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface CmdBase extends HasName, HasDisplayName, HasDescription, HasLongDescription {

	/**
	 * Executes the command.
	 * @return a return value: 0 for success, negative integer for an error, positive integer for a command that requires the shell to stop/exit
	 */
	int executeCommand();

	/**
	 * Returns a category the command belongs to, useful for grouping commands for example in help screens.
	 * @return the category of the command, can be null
	 */
	CmdCategory getCategory();

}
