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

package de.vandermeer.skb.interfaces.antlr;

import de.vandermeer.skb.interfaces.transformers.IsTransformer;

/**
 * A transformer that takes an ANTLR runtime object and returns an integer for the line (of the first token).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface Antlr_To_Line extends IsTransformer<Object, Integer> {

	@Override
	default Integer transform(Object obj){
		IsTransformer.super.transform(obj);
		if(obj instanceof IsAntlrRuntimeObject){
			return ((IsAntlrRuntimeObject)obj).getLine();
		}
		return IsAntlrRuntimeObject.create(obj).getLine();
	}

	/**
	 * Creates a transformer that takes an object and returns an Integer with line information for ANTLR classes.
	 * The returned transformer will throw runtime exceptions (null pointer, illegal argument) if the given object was not an ANTLR runtime object.
	 * The transformer returns line information for ANTLR classes `RecognitionException`, `Token`, `ParserRuleContext` and `TerminalNode` or -1 as default.
	 * @return new transformer
	 */
	static Antlr_To_Line create(){
		return new Antlr_To_Line() {};
	}
}
