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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.vandermeer.skb.interfaces.transformers.Map_To_Text;

/**
 * Tests for {@link Map_To_Text}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public class Test_Map_To_Text {

	@Test public void testMap(){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("eins", "one");
		m.put("zwei", "two");
		m.put("drei", "three");

		String s = Map_To_Text.create().transform(m);
		System.out.println(s);

		m.clear();
		m.put("one", Arrays.asList(new String[]{"1", "2", "3"}));
		s = Map_To_Text.create().transform(m);
		System.out.println(s);
	}
}
