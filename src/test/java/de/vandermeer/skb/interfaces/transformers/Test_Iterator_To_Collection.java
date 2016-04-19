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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import de.vandermeer.skb.interfaces.strategies.collections.set.HashSetStrategy;
import de.vandermeer.skb.interfaces.transformers.Iterator_To_Collection;

/**
 * Test {@link Iterator_To_Collection}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public class Test_Iterator_To_Collection {

	@Test public void testList(){
		List<String> l = new ArrayList<String>();
		l.add("one");
		l.add("two");
		l.add("three");

		HashSet<String> set = Iterator_To_Collection.create().transform(l.iterator(), HashSetStrategy.create());

		System.out.println(set);
	}
}
