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

package de.vandermeer.skb.interfaces.strategies.collections.set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import de.vandermeer.skb.interfaces.strategies.collections.IsSetStrategy;
import de.vandermeer.skb.interfaces.strategies.collections.set.HashSetStrategy;

/**
 * Tests for {@link HashSetStrategy}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public class Test_HashSetStrategy {

	@Test
	public void testArrayListStrategy(){
		List<String> stringsIn = Arrays.asList(new String[]{"one", "two", "three"});
		List<Integer> intsIn = Arrays.asList(new Integer[]{1, 2, 3});

		IsSetStrategy<?, String> sls = HashSetStrategy.create();
		Set<String> strSimple = sls.get(strSimple=null);
		Set<String> strMore = sls.get(stringsIn);

		IsSetStrategy<?, Integer> sli = HashSetStrategy.create();
		Set<Integer> intSimple = sli.get(intSimple=null);
		Set<Integer> intMore = sli.get(intsIn);

		assertNotNull(strMore);
		assertEquals(0, strSimple.size());
		assertEquals(3, strMore.size());

		assertNotNull(intMore);
		assertEquals(0, intSimple.size());
		assertEquals(3, strMore.size());

		assertTrue(strSimple instanceof HashSet);
		assertTrue(strMore instanceof HashSet);
		assertTrue(intSimple instanceof HashSet);
		assertTrue(intMore instanceof HashSet);
	}
}
