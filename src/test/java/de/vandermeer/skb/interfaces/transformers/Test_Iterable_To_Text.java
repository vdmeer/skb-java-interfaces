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
import java.util.List;

import org.junit.Test;

import de.vandermeer.skb.interfaces.transformers.Iterable_To_Text;

/**
 * Test {@link Iterable_To_Text}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public class Test_Iterable_To_Text {

	@Test public void testList(){
		List<String> l = new ArrayList<String>();
		l.add("one");
		l.add("two");
		l.add("three");

		String s = null;

		Iterable_To_Text<String> itt = Iterable_To_Text.create();
		s = itt.transform(l);
		System.out.println(s);

		s = Iterable_To_Text.create(String.class).transform(l);
		System.out.println(s);
	}
}
