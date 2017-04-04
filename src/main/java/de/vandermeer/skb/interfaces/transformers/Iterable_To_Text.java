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

package de.vandermeer.skb.interfaces.transformers;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

/**
 * Transforms the input provided by an `iterable` into text using String Templates.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public interface Iterable_To_Text<T> extends IsTransformer<Iterable<T>, String> {

	/** The String Template for an iterator to text translation. */
	static String TO_STRING_ST = "toText(entries) ::= <<\n    <entries:{n | - <n>}; separator=\"\n\">\n>>";

	@Override
	default String transform(Iterable<T> it){
		IsTransformer.super.transform(it);

		STGroup stg = new STGroupString(TO_STRING_ST);
		ST ret = stg.getInstanceOf("toText");

		for(Object obj : it){
			ret.add("entries", obj);
		}
		return ret.render();
	}

	/**
	 * Creates a new transformer.
	 * @param <T> type for `iterable`
	 * @return new transformer
	 */
	static <T> Iterable_To_Text<T> create(){
		return new Iterable_To_Text<T>() {};
	}

	/**
	 * Creates a new transformer.
	 * @param <T> type for `iterable`
	 * @param clazz type for the transformer
	 * @return new transformer
	 */
	static <T> Iterable_To_Text<T> create(Class<T> clazz){
		return new Iterable_To_Text<T>() {};
	}
}
