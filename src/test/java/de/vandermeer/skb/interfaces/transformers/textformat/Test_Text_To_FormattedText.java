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

import java.util.Collection;

import org.apache.commons.lang3.text.StrBuilder;
import org.junit.Test;

import de.svenjacobs.loremipsum.LoremIpsum;

/**
 * Tests for {@link Text_To_FormattedText}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public class Test_Text_To_FormattedText {

	@Test
	public void testCenter(){
		String words = new LoremIpsum().getWords(50);
		Collection<StrBuilder> coll = Text_To_FormattedText.center(words, 30, Text_To_FormattedText.FORMAT_NONE, '>', '<', '-', null);

		for(StrBuilder sb : coll){
			System.err.println(sb);
		}
	}

	@Test
	public void testLeft(){
		String words = new LoremIpsum().getWords(50);
		Collection<StrBuilder> coll = Text_To_FormattedText.left(words, 30, Text_To_FormattedText.FORMAT_NONE, '~', '_', null);

		for(StrBuilder sb : coll){
			System.err.println(sb);
		}
	}

	@Test
	public void testRight(){
		String words = new LoremIpsum().getWords(50);
		Collection<StrBuilder> coll = Text_To_FormattedText.right(words, 30, Text_To_FormattedText.FORMAT_NONE, '~', '_', null);

		for(StrBuilder sb : coll){
			System.err.println(sb);
		}
	}

	@Test
	public void testJustified(){
		String words = new LoremIpsum().getWords(50);
		Collection<StrBuilder> coll = Text_To_FormattedText.justified(words, 30, Text_To_FormattedText.FORMAT_NONE, '~', null);

		for(StrBuilder sb : coll){
			System.err.println(sb);
		}
	}

	@Test
	public void testJustifiedLeft(){
		String words = new LoremIpsum().getWords(50);
		Collection<StrBuilder> coll = Text_To_FormattedText.justifiedLeft(words, 30, Text_To_FormattedText.FORMAT_NONE, '#', '~', null);

		for(StrBuilder sb : coll){
			System.err.println(sb);
		}
	}

	@Test
	public void testJustifiedRight(){
		String words = new LoremIpsum().getWords(50);
		Collection<StrBuilder> coll = Text_To_FormattedText.justifiedRight(words, 30, Text_To_FormattedText.FORMAT_NONE, '#', '~', null);

		for(StrBuilder sb : coll){
			System.err.println(sb);
		}
	}
}
