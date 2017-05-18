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

package de.vandermeer.skb.interfaces.messages.sets;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.helpers.FormattingTuple;

import de.vandermeer.skb.interfaces.FormattingTupleWrapper;
import de.vandermeer.skb.interfaces.MessageType;
import de.vandermeer.skb.interfaces.categories.has.HasErrNo;
import de.vandermeer.skb.interfaces.messages.errors.IsError;
import de.vandermeer.skb.interfaces.render.DoesRender;

/**
 * Interface for objects that have a set of error messages.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface IsErrorSet extends IsMessageSet, HasErrNo {

	/**
	 * Creates a new error set.
	 * @return new error set
	 */
	static IsErrorSet create(){
		return new IsErrorSet() {
			protected Set<DoesRender> messages = new LinkedHashSet<>();
			protected int errno;

			@Override
			public Set<DoesRender> getMessages() {
				return this.messages;
			}

			@Override
			public int getErrNo() {
				return this.errno;
			}

			@Override
			public void setErrNo(int number) {
				this.errno = number;
			}
		};
	}

	/**
	 * Adds a new error message.
	 * @param error the provider of the error message, ignored if null or did not provide a tuple
	 */
	default void add(IsError error){
		if(error!=null){
			FormattingTuple ft = error.getErrorMessage();
			if(ft!=null){
				this.getMessages().add(FormattingTupleWrapper.create(ft));
				this.setErrNo(error.getErrNo());
			}
		}
	}

	@Override
	default MessageType getType(){
		return MessageType.ERROR;
	}

	/**
	 * Convenience method to render all errors into a single line.
	 * @return single line string
	 */
	default String renderToString(){
		return new StrBuilder().appendWithSeparators(this.renderAsCollection(), ", ").build();
	}

	/**
	 * Sets the error number if available.
	 * @param number error number
	 */
	void setErrNo(int number);

	/**
	 * Clears all messages and sets `errno` to `0`.
	 */
	@Override
	default void clearMessages(){
		this.getMessages().clear();
		this.setErrNo(0);
	}

	/**
	 * Adds all errors from another error set.
	 * @param errors set to add errors from, ignored if null or of different type
	 */
	default void addAll(IsErrorSet errors){
		if(errors!=null){
			this.getMessages().addAll(errors.getMessages());
			this.setErrNo(errors.getErrNo());
		}
	}
}
