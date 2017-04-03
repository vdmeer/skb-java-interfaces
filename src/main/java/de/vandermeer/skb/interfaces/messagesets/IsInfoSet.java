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
 * Interface for objects that have a set of info messages.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public interface IsInfoSet<M> extends IsMessageSet {

	@Override
	default boolean isInfoSet(){
		return true;
	}

	/**
	 * Clears all infos.
	 */
	default void clearInfoMessages(){
		this.getInfoMessages().clear();
	}

	/**
	 * Returns the info set.
	 * @return info set, should not be null
	 */
	Set<M> getInfoMessages();

	/**
	 * Adds a new info.
	 * @param info info to add
	 */
	default void addInfo(M info){
		this.getInfoMessages().add(info);
	}

	/**
	 * Adds all infos from given collection.
	 * @param infos collection of information to add
	 */
	default void addAllInfos(Collection<M> infos){
		this.getInfoMessages().addAll(infos);
	}

	/**
	 * Tests if infos have been added.
	 * @return true if infos are in the set, false otherwise
	 */
	default boolean hasInformation(){
		return (this.getInfoMessages().size()==0)?false:true;
	}

	/**
	 * Renders the info set.
	 * The method uses {@link DoesRender} or simple toString to render infos.
	 * Each element in the info set is rendered in a single line.
	 * @return rendered object
	 */
	@Override
	default String render() {
		StrBuilder ret = new StrBuilder(100);
		for(M m : this.getInfoMessages()){
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

	/**
	 * Creates a new info set.
	 * @param <M> type of the messages in the set
	 * @return new info set
	 */
	static <M> IsInfoSet<M> create(){
		return new IsInfoSet<M>() {
			final Set<M> infoSet = new LinkedHashSet<>();
			@Override
			public Set<M> getInfoMessages() {
				return this.infoSet;
			}
		};
	}
}
