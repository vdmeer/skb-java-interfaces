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

import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.interfaces.transformers.IsTransformer;

/**
 * Transforms a 2-dimensional array into a string representation in table form, for examples for debug output.
 * The returned string will start with the array coordinates, followed by a `:` and the column content.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public interface Array2D_To_String<T> extends IsTransformer<T[][], String> {

	@Override
	default String transform(T[][] ar){
		IsTransformer.super.transform(ar);

		StrBuilder ret = new StrBuilder(50);
		for(int row=0; row<ar.length; row++){ //TODO not null save
			if(ar[row]==null){
				ret.append("[").append(row).appendln("]: null");
			}
			else if(ar[row].length==0){
				ret.append("[").append(row).appendln("]: 0");
			}
			else{
				for(int col=0; col<ar[row].length; col++){
					ret.append("[").append(row).append("][").append(col).append("]: ");
					if(ar[row][col]==null){
						ret.appendln("null");
					}
					else if("".equals(ar[row][col])){
						ret.appendln("0");
					}
					else{
						ret.appendln(ar[row][col]);
					}
				}
			}
		}
		return ret.toString();
	}

	/**
	 * Creates a new transformer.
	 * @param <T> type of the array
	 * @return new transformer
	 */
	static <T> Array2D_To_String<T> create(){
		return new Array2D_To_String<T>() {};
	}
}
