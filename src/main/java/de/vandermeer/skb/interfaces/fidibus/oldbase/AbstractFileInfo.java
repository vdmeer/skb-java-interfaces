/* Copyright 2014 Sven van der Meer <vdmeer.sven@mykolab.com>
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

package de.vandermeer.skb.interfaces.fidibus.oldbase;

/**
 * An abstract file info implementation that can be configured for use as source or target.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.7
 */
public abstract class AbstractFileInfo {

	/**
	 * Options for an asString method
	 * @author Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
	 */
	public enum Options {
		/** As string returns the absolute path of the file source. */
		AS_STRING_ABSOLUTE_PATH,

		/** As string returns the absolute name of the file source. */
		AS_STRING_ABSOLUTE_NAME,

		/** As string returns the full file name of the file source. */
		AS_STRING_FULL_FILE_NAME,

		/** As string returns the base name of the file of the file source. */
		AS_STRING_BASE_FILE_NAME,

		/** As string returns the file extension of the file source. */
		AS_STRING__FILE_EXTENSION,

		/** As string returns the root path of the file source. */
		AS_STRING_ROOT_PATH,

		/** As string returns the set-root path of the file source. */
		AS_STRING_SET_ROOT_PATH,

		/** As string returns the set-root name of the file source. */
		AS_STRING_SET_ROOT_NAME,
	}

}
