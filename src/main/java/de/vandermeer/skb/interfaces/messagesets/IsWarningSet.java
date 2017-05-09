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

package de.vandermeer.skb.interfaces.messagesets;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.interfaces.render.DoesRender;

/**
 * Interface for objects that have a set of warning messages.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface IsWarningSet<M> extends IsMessageSet {

	/**
	 * Creates a new warning set.
	 * @param <M> type of the messages in the set
	 * @return new warning set
	 */
	static <M> IsWarningSet<M> create(){
		return new IsWarningSet<M>() {
			final Set<M> warningSet = new LinkedHashSet<>();
			@Override
			public Set<M> getWarningMessages() {
				return this.warningSet;
			}
		};
	}

	/**
	 * Adds all warnings from given collection.
	 * @param warnings collection of warnings to add, ignored if null
	 */
	default void addAllWarnings(Collection<M> warnings){
		if(warnings!=null){
			this.getWarningMessages().addAll(warnings);
		}
	}

	/**
	 * Adds all warnings from given collection.
	 * @param warnings array of warnings to add, ignored if null
	 */
	default void addAllWarnings(M[] warnings){
		if(warnings!=null){
			for(M warn : warnings){
				this.addWarning(warn);
			}
		}
	}

	/**
	 * Adds a new warning.
	 * @param warning warning to add, ignored if null
	 */
	default void addWarning(M warning){
		if(warning!=null){
			this.getWarningMessages().add(warning);
		}
	}

	/**
	 * Clears all warnings.
	 */
	default void clearWarningMessages(){
		this.getWarningMessages().clear();
	}

	/**
	 * Returns the warning set.
	 * @return warning set, should not be null
	 */
	Set<M> getWarningMessages();

	/**
	 * Tests if warnings have been added.
	 * @return true if warnings are in the set, false otherwise
	 */
	default boolean hasWarnings(){
		return (this.getWarningMessages().size()==0)?false:true;
	}

	@Override
	default boolean isErrorSet(){
		return true;
	}

	@Override
	default boolean isWarningSet(){
		return true;
	}

	/**
	 * Renders the warning set.
	 * The method uses {@link DoesRender} or simple toString to render warnings.
	 * Each element in the warning set is rendered in a single line.
	 * @return rendered object
	 */
	@Override
	default String render() {
		StrBuilder ret = new StrBuilder(100);
		for(M m : this.getWarningMessages()){
			if(m instanceof DoesRender){
				ret.append(((DoesRender)m).render());
			}
			else{
				ret.append(m);
			}
			ret.appendNewLine();
		}
		return ret.toString();
	}
}
