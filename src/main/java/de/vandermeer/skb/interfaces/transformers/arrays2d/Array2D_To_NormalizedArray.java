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
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.interfaces.transformers.IsTransformer;

/**
 * Transforms a 2-dimensional array into a normalized 2-dimensional array.
 * Normalized means that all rows in the returned array have the same number of columns.
 * Additionally, all columns will have the same length, space being added to pad.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public interface Array2D_To_NormalizedArray extends IsTransformer<String[][], String[][]> {

	/**
	 * Returns the number of columns,
	 * @return number of columns
	 * @throws {@link NullPointerException} if argument was null
	 */
	int getNumberOfColumns();

	@Override
	default String[][] transform(String[][] ar){
		IsTransformer.super.transform(ar);

		int rows = 0;
		for(int row=0; row<ar.length; row++){
			rows = Math.max(rows, ArrayUtils.getLength(ar[row]));
		}
		if(rows==0){
			rows = 1;
		}
		String[][] ret = new String[this.getNumberOfColumns()][rows];

		for(int row=0; row<ar.length; row++){
			int curSize = 0;
			if(ar[row]!=null && ar[row][0]!=null){
				curSize = ar[row][0].length();
			}
			if(ar[row]==null){
				for(int i=0; i<rows; i++){
					ret[row][i] = null;
				}
			}
			else if(ar[row].length==0){
				for(int i=0; i<rows; i++){
					ret[row][i] = new StrBuilder().appendPadding(curSize, ' ').toString();
				}
			}
			else{
				for(int col=0; col<ar[row].length; col++){
					ret[row][col] = ar[row][col];
				}
				if(ar[row].length<rows){
					for(int i=ar[row].length; i<rows; i++){
						ret[row][i] = new StrBuilder().appendPadding(curSize, ' ').toString();
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
