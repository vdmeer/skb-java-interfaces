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

package de.vandermeer.skb.interfaces.validators;

import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.categories.has.HasDescription;
import de.vandermeer.skb.interfaces.categories.has.HasDisplayName;
import de.vandermeer.skb.interfaces.categories.has.HasName;

/**
 * Options for validating files and directories.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public enum FiDiValidationOption implements HasName, HasDisplayName, HasDescription {

	/** Validate that an object is readable. */
	READ(
			"read",
			"Read enabled",
			"Validate that an object is readable."
	),

	/** Validate that an object is writable. */
	WRITE(
			"write",
			"Write enabled",
			"Validate that an object is writable."
	),

	/** Validate that an object is executable. */
	EXECUTE(
			"execute",
			"Execute enabled",
			"Validate that an object is executable."
	),

	/** Validate that an object can be created. */
	CREATE(
			"create",
			"Can create",
			"Validate that an object can be created."
	),

	/** Validate that an object can be deleted. */
	DELETE(
			"delete",
			"Can delete",
			"Validate that an object can be deleted."
	),

	/** Validate that an object is not hidden. */
	NOT_HIDDEN(
			"not-hidden",
			"Not Hidden",
			"Validate that an object is not hidden."
	),

	/** Validate that an object does exist. */
	EXISTS(
			"exists",
			"Exists",
			"Validate that an object does exist."
	),

	;

	/** Location name. */
	final String name;

	/** Location display name. */
	final String displayName;

	/** Location description. */
	final String description;

	/**
	 * Creates a new validation option.
	 * @param name location name, must not be blank
	 * @param displayName location display name, must not be blank
	 * @param description location description, must not be blank
	 */
	FiDiValidationOption(String name, String displayName, String description){
		Validate.notBlank(name);
		Validate.notBlank(displayName);
		Validate.notBlank(description);

		this.name = name;
		this.displayName = displayName;
		this.description = description;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDisplayName() {
		return this.displayName;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

}
