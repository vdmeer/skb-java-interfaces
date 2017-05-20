/* Copyright 2017 Sven van der Meer <vdmeer.sven@mykolab.com\>
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

package de.vandermeer.skb.interfaces.translators.htmlelement;

import org.apache.commons.lang3.StringUtils;

import de.vandermeer.skb.interfaces.translators.HtmlElementTranslator;

/**
 * HTML Element translator translating from text to LaTeX.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt; - auto-generated by SKB Datatool from SKB Html Element Map data
 * @version    v0.0.2 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface Text2Latex extends HtmlElementTranslator {

	/**
	 * Creates a new text to LaTeX HTML-element translator.
	 * @return new translator
	 */
	static Text2Latex create(){
		return new Text2Latex() {
		};
	}

	/** Array of HTML Element strings. */
	static final String[] SEARCH_LIST_HE = new String[]{
		"</abbr>", "</b>", "</code>", "</i>", "</li>", "</ol>", "</sub>", "</sup>", "</ul>", "<abbr>", "<b>", "<br />", "<br/>", "<br>", "<code>", "<i>", "<li>", "<ol>", "<sub>", "<sup>", "<ul>"
	};

	/** Array of temporary replacements for HTML Element strings. */
	static final String[] REPLACEMNET_LIST_HE = new String[]{
		"(((/abbr)))", "(((/b)))", "(((/code)))", "(((/i)))", "(((/li)))", "(((/ol)))", "(((/sub)))", "(((/sup)))", "(((/ul)))", "(((abbr)))", "(((b)))", "(((br /)))", "(((br/)))", "(((br)))", "(((code)))", "(((i)))", "(((li)))", "(((ol)))", "(((sub)))", "(((sup)))", "(((ul)))"
	};

	/** Array of LaTeX replacements for temporary HTML Element strings. */
	static final String[] LATEX_LIST_HE = new String[]{
		"}", "}", "|", "}", "", "\\end{enumerate}", "}$", "}$", "\\end{itemize}", "\\ac{", "\\textbf{", "\\", "\\", "\\", "\\lstinline|", "\\textit{", "\\item", "\\begin{enumerate}", "$_{", "$^{", "\\begin{itemize}"
	};

	@Override
	default String text2tmp(String input){
		return StringUtils.replaceEach(input, SEARCH_LIST_HE, REPLACEMNET_LIST_HE);
	}

	@Override
	default String tmp2target(String input){
		return StringUtils.replaceEach(input, REPLACEMNET_LIST_HE, LATEX_LIST_HE);
	}

	@Override
	default String translateHtmlElements(String input){
		return StringUtils.replaceEach(input, SEARCH_LIST_HE, LATEX_LIST_HE);
	}
}
