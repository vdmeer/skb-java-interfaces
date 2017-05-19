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

import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.categories.has.HasDescription;
import de.vandermeer.skb.interfaces.categories.has.HasDisplayName;
import de.vandermeer.skb.interfaces.categories.has.HasName;

/**
 * A category for shell commands.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface Sh_CmdCategory extends HasName, HasDisplayName, HasDescription {

	/**
	 * Creates a new category.
	 * @param name category name, must not be blank
	 * @param displayName category display name, must not be blank
	 * @param description category description, must not be blank
	 * @return the new category
	 */
	static Sh_CmdCategory create(final String name, final String displayName, final String description){
		Validate.notNull(name);
		Validate.notNull(displayName);
		Validate.notNull(description);

		return new Sh_CmdCategory() {
			@Override
			public String getDescription() {
				return description;
			}

			@Override
			public String getDisplayName() {
				return displayName;
			}

			@Override
			public String getName() {
				return name;
			}
		};
	}
}
