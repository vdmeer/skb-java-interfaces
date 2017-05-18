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

import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.fidibus.FiDi_Location;

/**
 * Locations for a file.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public enum FileLocation implements FiDi_Location {

	/** A location in the Java class path. */
	CLASSPATH(
			"classpath",
			"Java Classpath",
			"The information is located in the Java Classpath."
	),

	/** A location in the file system. */
	FILESYSTEM(
			"file-system",
			"File System",
			"The information is located in the file system."
	),

	/** A location in resource (a JAR file). */
	RESOURCE(
			"resource",
			"Resource (JAR File)",
			"The information is located in resources (a JAR file)."
	),

	;

	/** Location name. */
	final String name;

	/** Location display name. */
	final String displayName;

	/** Location description. */
	final String description;

	/**
	 * Creates a new file location.
	 * @param name location name, must not be blank
	 * @param displayName location display name, must not be blank
	 * @param description location description, must not be blank
	 */
	FileLocation(String name, String displayName, String description){
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
