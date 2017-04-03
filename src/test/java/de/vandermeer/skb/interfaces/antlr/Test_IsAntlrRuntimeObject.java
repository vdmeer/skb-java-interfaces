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

import static org.junit.Assert.assertEquals;

import org.antlr.v4.runtime.CommonToken;
import org.junit.Test;

import de.vandermeer.skb.interfaces.antlr.IsAntlrRuntimeObject;

/**
 * Tests for {@link IsAntlrRuntimeObject}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public class Test_IsAntlrRuntimeObject {

	@Test public void test_GetLine(){
		CommonToken tk = new CommonToken(0);
		IsAntlrRuntimeObject iaro = IsAntlrRuntimeObject.create(tk);

		assertEquals(0, iaro.getLine());	//default line for CommonToken is 0

		tk.setLine(99);
		assertEquals(99, iaro.getLine());

		tk.setLine(88);
		assertEquals(88, iaro.getLine());
	
		tk.setLine(0);
		assertEquals(0, iaro.getLine());
	}

	@Test public void test_getColumn(){
		CommonToken tk = new CommonToken(0);
		IsAntlrRuntimeObject iaro = IsAntlrRuntimeObject.create(tk);

		assertEquals(-1, iaro.getColumn());	//default column for CommonToken is not set, so -1

		tk.setCharPositionInLine(99);
		assertEquals(99, iaro.getColumn());

		tk.setCharPositionInLine(88);
		assertEquals(88, iaro.getColumn());
	
		tk.setCharPositionInLine(0);
		assertEquals(0, iaro.getColumn());
	}
}
