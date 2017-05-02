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

package de.vandermeer.skb.interfaces.document;

import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.objctxt.IsObjectContext;

/**
 * The context (all settings) of a table.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface IsTableContext extends IsObjectContext {

	/**
	 * Returns the width of the text in the table (overall width minus any margins etc).
	 * @return text width
	 */
	default int getTextWidth(){
		return this.getTextWidth(this.getWidth());
	}

	/**
	 * Returns the width of the text in the table (overall width minus any margins etc).
	 * @param width a width to calculate the text width for
	 * @return text width
	 */
	int getTextWidth(int width);

	/**
	 * Returns the width set for the table.
	 * @return set width
	 */
	int getWidth();

	/**
	 * Checks a width against the text width (calculated)
	 * @param width the width to check against
	 * @return true if text has at least 1 character
	 * @throws IllegalStateException if the text width is less than 1
	 */
	default boolean checkWidth(int width){
		Validate.validState(this.getTextWidth(width)>0, "text width smaller than 1");
		return (this.getTextWidth(width)>0)?true:false;
	}
}
