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

package de.vandermeer.skb.interfaces.strategies.maps.abstractmap;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import de.vandermeer.skb.interfaces.strategies.maps.abstractmap.LinkedHashMapStrategy;

/**
 * Tests for {@link LinkedHashMapStrategy}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public class Test_LinkedHashMapStrategy {

	@Test
	public void test_LinkedHashMapStrategy(){
		LinkedHashMapStrategy<String, String> hms= LinkedHashMapStrategy.create();
		LinkedHashMapStrategy<String, Integer> hmi= LinkedHashMapStrategy.create();

		Map<String, String> strings = hms.get();
		Map<String, Integer> ints = hmi.get();

		assertTrue(strings instanceof LinkedHashMap);
		assertTrue(ints instanceof LinkedHashMap);
	}
}
