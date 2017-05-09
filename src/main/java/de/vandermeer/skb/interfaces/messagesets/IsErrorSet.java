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
 * Interface for objects that have a set of error messages.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface IsErrorSet<M> extends IsMessageSet {

	/**
	 * Clears all errors.
	 */
	default void clearErrorMessages(){
		this.getErrorMessages().clear();
	}

	/**
	 * Returns the error set.
	 * @return error set, should not be null
	 */
	Set<M> getErrorMessages();

	/**
	 * Adds a new error.
	 * @param error error to add, ignored if null
	 */
	default void addError(M error){
		if(error!=null){
			this.getErrorMessages().add(error);
		}
	}

	/**
	 * Adds all errors from given collection.
	 * @param errors collection of errors to add, ignored if null
	 */
	default void addAllErrors(Collection<M> errors){
		if(errors!=null){
			this.getErrorMessages().addAll(errors);
		}
	}

	/**
	 * Adds all errors from given collection.
	 * @param errors array of errors to add, ignored if null
	 */
	default void addAllErrors(M[] errors){
		if(errors!=null){
			for(M err : errors){
				this.addError(err);
			}
		}
	}

	/**
	 * Tests if errors have been added.
	 * @return true if errors are in the set, false otherwise
	 */
	default boolean hasErrors(){
		return (this.getErrorMessages().size()==0)?false:true;
	}

	/**
	 * Renders the error set.
	 * The method uses {@link DoesRender} or simple toString to render errors.
	 * Each element in the error set is rendered in a single line.
	 * @return rendered object
	 */
	@Override
	default String render() {
		StrBuilder ret = new StrBuilder(100);
		for(M m : this.getErrorMessages()){
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
	 * Creates a new error set.
	 * @param <M> type of the messages in the set
	 * @return new error set
	 */
	static <M> IsErrorSet<M> create(){
		return new IsErrorSet<M>() {
			final Set<M> errorSet = new LinkedHashSet<>();
			@Override
			public Set<M> getErrorMessages() {
				return this.errorSet;
			}
		};
	}
}
