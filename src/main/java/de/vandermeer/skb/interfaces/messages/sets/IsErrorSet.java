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

import org.slf4j.helpers.FormattingTuple;

import de.vandermeer.skb.interfaces.MessageType;
import de.vandermeer.skb.interfaces.messages.FormattingTupleWrapper;
import de.vandermeer.skb.interfaces.messages.errors.IsError;
import de.vandermeer.skb.interfaces.render.DoesRender;

/**
 * Interface for objects that have a set of error messages.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface IsErrorSet extends IsMessageSet {

	/**
	 * Creates a new error set.
	 * @return new error set
	 */
	static IsErrorSet create(){
		return new IsErrorSet() {
			Set<DoesRender> messages = new LinkedHashSet<>();

			@Override
			public Set<DoesRender> getMessages() {
				return this.messages;
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
			}
		}
	}

	@Override
	default MessageType getType(){
		return MessageType.ERROR;
	}

}
