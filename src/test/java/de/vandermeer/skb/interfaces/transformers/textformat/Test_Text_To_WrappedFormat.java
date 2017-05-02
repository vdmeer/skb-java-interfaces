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

package de.vandermeer.skb.interfaces.transformers.textformat;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import de.svenjacobs.loremipsum.LoremIpsum;

/**
 * Tests for {@link Text_To_WrappedFormat}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public class Test_Text_To_WrappedFormat {

	static String LINEBREAK = "<br />";

	@Test
	public void test_Simple(){
		String words = new LoremIpsum().getWords(30);
		String text = words;
		text = StringUtils.replace(words, "dolor ", "dolor " + LINEBREAK);
		text = StringUtils.replace(text, "dolore ", "dolore " + LINEBREAK);

		Pair<ArrayList<String>, ArrayList<String>> textPair;

		textPair = Text_To_WrappedFormat.convert(text, 20, null);
		assertEquals(0, textPair.getLeft().size());
		assertEquals(11, textPair.getRight().size());
		assertEquals(words, StringUtils.join(textPair.getRight(), ' '));

		textPair= Text_To_WrappedFormat.convert(text, 30, Pair.of(6, 10));

		System.err.println(words);
		System.err.println(text);
		System.err.println("\n----[ top ]----");
		System.err.println("123456789012345678901234567890");
		for(String s : textPair.getLeft()){
			System.err.println(s);
		}
		System.err.println("\n----[ bottom ]----");
		System.err.println("123456789012345678901234567890");
		for(String s : textPair.getRight()){
			System.err.println(s);
		}
	}

}
