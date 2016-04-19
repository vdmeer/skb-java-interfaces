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

package de.vandermeer.skb.interfaces.transformers.arrays2d;

import org.apache.commons.lang3.ArrayUtils;

import de.vandermeer.skb.interfaces.transformers.IsTransformer;

/**
 * Transforms a 2-dimensional array into a normalized 2-dimensional array.
 * Normalized means that all rows in the returned array have the same number of columns.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public interface Array2D_To_NormalizedArray extends IsTransformer<String[][], String[][]> {

	/**
	 * Returns the number of columns,
	 * @return number of columns
	 */
	int getNumberOfColumns();

	@Override
	default String[][] transform(String[][] ar){
		IsTransformer.super.transform(ar);

		int rows = 0;
		for(int row=0; row<ar.length; row++){ //TODO not null safe
			rows = Math.max(rows, ArrayUtils.getLength(ar[row]));
		}
		if(rows==0){
			rows = 1;
		}
		String[][] ret = new String[this.getNumberOfColumns()][rows];

		for(int row=0; row<ar.length; row++){ //not null safe
			if(ar[row]==null){
				for(int i=0; i<rows; i++){
					ret[row][i] = null;
				}
			}
			else if(ar[row].length==0){
				for(int i=0; i<rows; i++){
					ret[row][i] = "";
				}
			}
			else{
				for(int col=0; col<ar[row].length; col++){
					ret[row][col] = ar[row][col];
				}
				if(ar[row].length<rows){
					for(int i=ar[row].length; i<rows; i++){
						ret[row][i] = "";
					}
				}
			}
		}
		return ret;
	}

	/**
	 * Creates a new transformer.
	 * @param columns number of columns
	 * @return new transformer
	 */
	static Array2D_To_NormalizedArray create(final int columns){
		return new Array2D_To_NormalizedArray() {
			@Override
			public int getNumberOfColumns() {
				return columns;
			}
		};
	}
}
