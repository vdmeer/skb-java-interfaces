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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.ST;

import de.vandermeer.skb.interfaces.MessageType;
import de.vandermeer.skb.interfaces.antlr.IsST;
import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.messages.FormattingTupleWrapper;
import de.vandermeer.skb.interfaces.render.DoesRender;
import de.vandermeer.skb.interfaces.render.RendersToCluster;

/**
 * Category of objects that represent a message set.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface IsMessageSet extends CategoryIs, RendersToCluster {

	/**
	 * Adds a new message.
	 * @param message message to add, ignored if null
	 */
	default void add(DoesRender message){
		if(message!=null){
			this.getMessages().add(message);
		}
	}

	/**
	 * Adds a new message.
	 * @param message the message, ignored if null
	 */
	default void add(ST message){
		if(message!=null){
			this.getMessages().add(IsST.create(message));
		}
	}

	/**
	 * Adds a new message.
	 * @param message the message, ignored if blank
	 */
	default void add(String message){
		if(!StringUtils.isBlank(message)){
			this.getMessages().add(FormattingTupleWrapper.create(message));
		}
	}

	/**
	 * Adds a new message.
	 * @param message the message, nothing added if blank
	 * @param obj the elements for the message, ignored if null
	 */
	default void add(String message, Object ... obj){
		if(!StringUtils.isBlank(message) && obj!=null){
			this.getMessages().add(FormattingTupleWrapper.create(message, obj));
		}
	}

	/**
	 * Adds all messages from given array.
	 * @param messages array of messages to add, ignored if null
	 */
	default void addAll(DoesRender[] messages){
		if(messages!=null){
			for(DoesRender msg : messages){
				this.add(msg);
			}
		}
	}

	/**
	 * Adds all messages from another message set, if both are of the same type.
	 * @param messages set to add messages from, ignored if null or of different type
	 */
	default void addAll(IsMessageSet messages){
		if(messages!=null && messages.getType()==this.getType()){
			this.getMessages().addAll(messages.getMessages());
		}
	}

	/**
	 * Adds all messages from given iterable.
	 * @param messages iterable of messages to add, ignored if null
	 */
	default void addAll(Iterable<DoesRender> messages){
		if(messages!=null){
			for(DoesRender dr : messages){
				this.getMessages().add(dr);
			}
		}
	}

	/**
	 * Adds all messages from given iterator.
	 * @param messages iterator of messages to add, ignored if null
	 */
	default void addAll(Iterator<DoesRender> messages){
		if(messages!=null){
			while(messages.hasNext()){
				this.getMessages().add(messages.next());
			}
		}
	}

	/**
	 * Clears all messages.
	 */
	default void clearMessages(){
		this.getMessages().clear();
	}

	Set<DoesRender> getMessages();

	/**
	 * Returns the type of the message set.
	 * @return message set type
	 */
	MessageType getType();

	/**
	 * Tests if messages have been added.
	 * @return true if messages are in the set, false otherwise
	 */
	default boolean hasMessages(){
		return (this.getMessages().size()==0)?false:true;
	}

	@Override
	default Collection<String> renderAsCollection() {
		ArrayList<String> ret = new ArrayList<>();
		for(DoesRender msg : this.getMessages()){
			ret.add(msg.render());
		}
		return ret;
	}
}
