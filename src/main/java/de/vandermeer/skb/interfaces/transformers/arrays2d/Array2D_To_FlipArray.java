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

import de.vandermeer.skb.interfaces.transformers.IsTransformer;

/**
 * Transforms a 2-dimensional array of strings into a new 2-dimensional array of strings.
 * Consider the input array representing a table,
 * the output array will have rows converted to columns and columns converted to rows.
 * 
 * Consider an input array being a table as
 * ----
 * row 1: a1, b1, c1
 * row 2: a2, b2, c2
 * row 3: a3, b3, c3
 * ----
 * 
 * The transformed array will be
 * ----
 * row 1: a1, a2, a3
 * row 2: b1, b2, b3
 * row 3: c1, c2, c3
 * ----
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public interface Array2D_To_FlipArray extends IsTransformer<String[][], String[][]> {

	@Override
	default String[][] transform(String[][] ar){
		IsTransformer.super.transform(ar);

		if(ar==null){
			return null;
		}
		String[][] ret = new String[ar[0].length][ar.length];

		for(int i=0; i<ar[0].length; i++){
			for(int k=0; k<ar.length; k++){
				ret[i][k] = ar[k][i];
			}
		}
		return ret;
	}

	/**
	 * Creates a new transformer.
	 * @return new transformer
	 */
	static Array2D_To_FlipArray create(){
		return new Array2D_To_FlipArray() {};
	}
}
