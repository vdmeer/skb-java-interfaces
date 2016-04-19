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

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import de.vandermeer.skb.interfaces.transformers.String_To_ConditionalBreak;

/**
 * Test {@link String_To_ConditionalBreak}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class Test_String_To_ConditionalBreak {

	@Test
	public void test_Convert(){
		String s1 = "paragraph 1\nparagraph 2";
		String[] s1Ar = String_To_ConditionalBreak.convert(s1);
		assertEquals(2, s1Ar.length);
		assertEquals("paragraph 1", s1Ar[0]);
		assertEquals("paragraph 2", s1Ar[1]);

		String s2 = "paragraph 1\n\nparagraph 2";
		String[] s2Ar = String_To_ConditionalBreak.convert(s2);
		assertEquals(3, s2Ar.length);
		assertEquals("paragraph 1", s2Ar[0]);
		assertEquals("", s2Ar[1]);
		assertEquals("paragraph 2", s2Ar[2]);

		String lf = "paragraph 1\nparagraph 2";
		String[] ar = String_To_ConditionalBreak.convert(lf);
//		String[] ar = ArrayTransformations.WRAP_LINES(100, String_To_ConditionalBreak.convert(lf));
		System.err.println(ArrayUtils.toString(ar));

		lf = "paragraph 1\n\nparagraph 2";
		ar = String_To_ConditionalBreak.convert(lf);
//		ar = ArrayTransformations.WRAP_LINES(100, String_To_ConditionalBreak.convert(lf));
		System.err.println(ArrayUtils.toString(ar));

		lf = "paragraph 1\nparagraph 2";
		ar = String_To_ConditionalBreak.convert(lf);
//		ar = ArrayTransformations.WRAP_LINES(5, String_To_ConditionalBreak.convert(lf));
		System.err.println(ArrayUtils.toString(ar));

		lf = "paragraph 1\n\nparagraph 2";
		ar = String_To_ConditionalBreak.convert(lf);
//		ar = ArrayTransformations.WRAP_LINES(5, String_To_ConditionalBreak.convert(lf));
		System.err.println(ArrayUtils.toString(ar));
	}
}
