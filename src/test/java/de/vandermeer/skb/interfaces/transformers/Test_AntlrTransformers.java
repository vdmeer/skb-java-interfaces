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

import static org.junit.Assert.assertEquals;

import org.antlr.v4.runtime.CommonToken;
import org.junit.Test;

import de.vandermeer.skb.interfaces.antlr.Antlr_To_Column;
import de.vandermeer.skb.interfaces.antlr.Antlr_To_Line;
import de.vandermeer.skb.interfaces.antlr.Antlr_To_Text;

/**
 * Tests for {@link Antlr_To_Line}, {@link Antlr_To_Column}, and {@link Antlr_To_Text}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public class Test_AntlrTransformers {

	@Test
	public void test_Antlr2Line(){
		CommonToken tk = new CommonToken(0);
//		assertEquals(new Integer(-1), AntlrToLine.create().transform((CommonToken)null));
		assertEquals(new Integer(0), Antlr_To_Line.create().transform(tk));

		tk.setLine(0);
		assertEquals(new Integer(0),  Antlr_To_Line.create().transform(tk));

		tk.setLine(20);
		assertEquals(new Integer(20), Antlr_To_Line.create().transform(tk));
	}

	@Test
	public void test_Antlr2Column(){
		CommonToken tk = new CommonToken(0);
//		assertEquals(new Integer(-1), AntlrToColumn.create().transform((CommonToken)null));
		assertEquals(new Integer(-1), Antlr_To_Column.create().transform(tk));

		tk.setCharPositionInLine(0);
		assertEquals(new Integer(0), Antlr_To_Column.create().transform(tk));

		tk.setCharPositionInLine(20);
		assertEquals(new Integer(20), Antlr_To_Column.create().transform(tk));
	}

	@Test
	public void test_Antlr2Text(){
		CommonToken tk = new CommonToken(0);
		tk.setText("token1");

		assertEquals("token1", Antlr_To_Text.create().transform(tk));
	}
}
