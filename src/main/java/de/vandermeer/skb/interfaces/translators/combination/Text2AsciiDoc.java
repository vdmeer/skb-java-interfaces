/* Copyright 2017 Sven van der Meer <vdmeer.sven@mykolab.com>
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

package de.vandermeer.skb.interfaces.translators.combination;

import de.vandermeer.skb.interfaces.translators.CombinedTranslator;

/**
 * Combined translator translating from text to AsciiDoc.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface Text2AsciiDoc extends CombinedTranslator {

	/**
	 * Returns a new combined translator for text to ASCIIDOC.
	 * @return new combined translator
	 */
	static Text2AsciiDoc create(){
		return new Text2AsciiDoc() {
		};
	}

	@Override
	default String text2tmp(String input) {
		return de.vandermeer.skb.interfaces.translators.htmlelement.Text2AsciiDoc.create().text2tmp(input);
	}

	@Override
	default String tmp2target(String input) {
		return de.vandermeer.skb.interfaces.translators.htmlelement.Text2AsciiDoc.create().tmp2target(input);
	}

	@Override
	default String translate(String input) {
		String ret = this.text2tmp(input);
		ret = this.translateCharacters(ret);
		ret = this.tmp2target(ret);
		return ret;
	}

	@Override
	default String translateCharacters(String input) {
		return de.vandermeer.skb.interfaces.translators.character.Text2AsciiDoc.create().translateCharacters(input);
	}

	@Override
	default String translateHtmlElements(String input) {
		return de.vandermeer.skb.interfaces.translators.htmlelement.Text2AsciiDoc.create().translateHtmlElements(input);
	}

}
