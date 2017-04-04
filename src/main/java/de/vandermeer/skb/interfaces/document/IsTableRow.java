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

import java.util.LinkedList;

import de.vandermeer.skb.interfaces.objctxt.HasObjectContext;

/**
 * A table row.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public interface IsTableRow extends HasObjectContext {

	@Override
	IsTableRowContext getContext();

	/**
	 * Returns the style of the rule row.
	 * @return rule row style, default is {@link TableRowStyle#UNKNOWN}
	 */
	default TableRowStyle getStyle(){
		return TableRowStyle.UNKNOWN;
	}

	/**
	 * Returns the type of the rule row.
	 * @return rule row type, default is {@link TableRowType#UNKNOWN}
	 */
	default TableRowType getType(){
		return TableRowType.UNKNOWN;
	}

	/**
	 * Returns the cells of a content row in a table.
	 * @return cells, can be null
	 */
	default LinkedList<? extends IsTableCell> getCells(){
		return null;
	}

	/**
	 * Tests if the row has cells, if it is a content row.
	 * @return true if it has cells (is a content row), false otherwise (then it is a row representing a rule)
	 */
	default boolean hasCells(){
		return (this.getCells()==null)?false:true;
	}
}
