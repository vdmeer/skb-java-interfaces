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

package de.vandermeer.skb.interfaces.strategies.collections.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.junit.Test;

import de.vandermeer.skb.interfaces.strategies.collections.IsListStrategy;
import de.vandermeer.skb.interfaces.strategies.collections.list.StackStrategy;

/**
 * Tests for {@link StackStrategy}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public class Test_StackStrategy {

	@Test
	public void testStackStrategy(){
		List<String> stringsIn = Arrays.asList(new String[]{"one", "two", "three"});
		List<Integer> intsIn = Arrays.asList(new Integer[]{1, 2, 3});

		IsListStrategy<?, String> sls = StackStrategy.create();
		List<String> strSimple = sls.get(strSimple=null);
		List<String> strMore = sls.get(stringsIn);

		IsListStrategy<?, Integer> sli = StackStrategy.create();
		List<Integer> intSimple = sli.get(intSimple=null);
		List<Integer> intMore = sli.get(intsIn);

		assertNotNull(strMore);
		assertEquals(0, strSimple.size());
		assertEquals(3, strMore.size());

		assertNotNull(intMore);
		assertEquals(0, intSimple.size());
		assertEquals(3, strMore.size());

		assertTrue(strSimple instanceof Stack);
		assertTrue(strMore instanceof Stack);
		assertTrue(intSimple instanceof Stack);
		assertTrue(intMore instanceof Stack);
	}
}
