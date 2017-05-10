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

package de.vandermeer.skb.interfaces.messages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.helpers.FormattingTuple;
import org.stringtemplate.v4.ST;

import de.vandermeer.skb.interfaces.MessageType;
import de.vandermeer.skb.interfaces.antlr.IsST;
import de.vandermeer.skb.interfaces.console.MessageConsole;
import de.vandermeer.skb.interfaces.messages.errors.IsError;
import de.vandermeer.skb.interfaces.messages.sets.HasDebugSet;
import de.vandermeer.skb.interfaces.messages.sets.HasErrorSet;
import de.vandermeer.skb.interfaces.messages.sets.HasInfoSet;
import de.vandermeer.skb.interfaces.messages.sets.HasTraceSet;
import de.vandermeer.skb.interfaces.messages.sets.HasWarningSet;
import de.vandermeer.skb.interfaces.messages.sets.IsMessageSet;
import de.vandermeer.skb.interfaces.render.DoesRender;

/**
 * A manager for massage of all messages in {@link MessageType}.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface MessageManager {

	/**
	 * Adds messages.
	 * @param set a message provider to add, nothing added if null or does not provide messages
	 */
	default void add(HasDebugSet set){
		if(set!=null && set.getDebugSet()!=null){
			for(DoesRender message : set.getDebugSet().getMessages()){
				this.add(MessageType.DEBUG, message);
			}
		}
	}

	/**
	 * Adds messages.
	 * @param set a message provider to add, nothing added if null or does not provide messages
	 */
	default void add(HasErrorSet set){
		if(set!=null && set.getErrorSet()!=null){
			for(DoesRender message : set.getErrorSet().getMessages()){
				this.add(MessageType.ERROR, message);
			}
		}
	}

	/**
	 * Adds messages.
	 * @param set a message provider to add, nothing added if null or does not provide messages
	 */
	default void add(HasInfoSet set){
		if(set!=null && set.getInfoSet()!=null){
			for(DoesRender message : set.getInfoSet().getMessages()){
				this.add(MessageType.INFO, message);
			}
		}
	}

	/**
	 * Adds messages.
	 * @param set a message provider to add, nothing added if null or does not provide messages
	 */
	default void add(HasTraceSet set){
		if(set!=null && set.getTraceSet()!=null){
			for(DoesRender message : set.getTraceSet().getMessages()){
				this.add(MessageType.TRACE, message);
			}
		}
	}

	/**
	 * Adds messages.
	 * @param set a message provider to add, nothing added if null or does not provide messages
	 */
	default void add(HasWarningSet set){
		if(set!=null && set.getWarningSet()!=null){
			for(DoesRender message : set.getWarningSet().getMessages()){
				this.add(MessageType.WARNING, message);
			}
		}
	}

	/**
	 * Adds a new error message.
	 * @param error the error providing the message, ignored if null
	 */
	default void add(IsError error){
		if(error!=null){
			FormattingTuple ft = error.getErrorMessage();
			if(ft!=null){
				this.process(MessageType.ERROR, FormattingTupleWrapper.create(ft));
			}
		}
	}

	/**
	 * Adds a new message, if type and message were not null.
	 * @param type the message type, nothing added if null
	 * @param message the message, nothing added if null
	 */
	default void add(MessageType type, DoesRender message){
		if(type!=null && message!=null){
			this.process(type, message);
		}
	}

	/**
	 * Adds a new message, if type and message were not null.
	 * @param type the message type, nothing added if null
	 * @param message the message, nothing added if null
	 */
	default void add(MessageType type, ST message){
		if(type!=null && message!=null){
			this.process(type, IsST.create(message));
		}
	}

	/**
	 * Adds a new message, if type was not null and message was not blank.
	 * @param type the message type, nothing added if null
	 * @param message the message, nothing added if blank
	 */
	default void add(MessageType type, String message){
		if(type!=null && !StringUtils.isBlank(message)){
			this.process(type, FormattingTupleWrapper.create(message));
		}
	}

	/**
	 * Adds a new message if type was not null, message was not blank, and objects were not null.
	 * @param type the message type, nothing added if null
	 * @param message the message, nothing added if blank
	 * @param obj the substitution objects for the message, nothing added if null
	 */
	default void add(MessageType type, String message, Object ... obj){
		if(type!=null && !StringUtils.isBlank(message) && obj!=null){
			this.process(type, FormattingTupleWrapper.create(message, obj));
		}
	}

	/**
	 * Adds new messages.
	 * @param messages the set of messages, nothing added if null
	 */
	default void addAll(IsMessageSet messages){
		if(messages!=null){
			for(DoesRender dr : messages.getMessages()){
				this.process(messages.getType(), dr);
			}
		}
	}

	/**
	 * Adds new messages.
	 * @param type the message type, nothing added if null
	 * @param messages the message array, nothing added if null
	 */
	default void addAll(MessageType type, DoesRender[] messages){
		if(type!=null && messages!=null){
			for(DoesRender dr : messages){
				this.process(type, dr);
			}
		}
	}

	/**
	 * Adds new messages.
	 * @param type the message type, nothing added if null
	 * @param messages the iterable messages, nothing added if null
	 */
	default void addAll(Iterable<IsError> messages){
		if(messages!=null){
			for(DoesRender dr : messages){
				this.process(MessageType.ERROR, dr);
			}
		}
	}

	/**
	 * Adds new messages.
	 * @param type the message type, nothing added if null
	 * @param messages the iterable messages, nothing added if null
	 */
	default void addAll(MessageType type, Iterable<DoesRender> messages){
		if(type!=null && messages!=null){
			for(DoesRender dr : messages){
				this.process(type, dr);
			}
		}
	}

	/**
	 * Adds new messages.
	 * @param type the message type, nothing added if null
	 * @param messages the message iterator, nothing added if null
	 */
	default void addAll(MessageType type, Iterator<DoesRender> messages){
		if(type!=null && messages!=null){
			while(messages.hasNext()){
				this.process(type, messages.next());
			}
		}
	}

	/**
	 * Adds a new debug message.
	 * @param what the what part of the message (what has happened)
	 * @param obj objects to add to the message
	 */
	default void addDebug(String what, Object ... obj){
		Message5WH message = new Message5WH_Builder()
				.addWhat(FormattingTupleWrapper.create(what, obj))
				.setType(MessageType.DEBUG)
				.build()
		;
		this.add(MessageType.DEBUG, message);
	}

	/**
	 * Adds a new error message.
	 * @param what the what part of the message (what has happened)
	 * @param obj objects to add to the message
	 */
	default void addError(String what, Object ... obj){
		Message5WH message = new Message5WH_Builder()
				.addWhat(FormattingTupleWrapper.create(what, obj))
				.setType(MessageType.ERROR)
				.build()
		;
		this.add(MessageType.ERROR, message);
	}

	/**
	 * Adds a new info message.
	 * @param what the what part of the message (what has happened)
	 * @param obj objects to add to the message
	 */
	default void addInfo(String what, Object ... obj){
		Message5WH message = new Message5WH_Builder()
				.addWhat(FormattingTupleWrapper.create(what, obj))
				.setType(MessageType.INFO)
				.build()
		;
		this.add(MessageType.INFO, message);
	}

	/**
	 * Adds a new trace message.
	 * @param what the what part of the message (what has happened)
	 * @param obj objects to add to the message
	 */
	default void addTrace(String what, Object ... obj){
		Message5WH message = new Message5WH_Builder()
				.addWhat(FormattingTupleWrapper.create(what, obj))
				.setType(MessageType.TRACE)
				.build()
		;
		this.add(MessageType.TRACE, message);
	}

	/**
	 * Adds a new warning message.
	 * @param what the what part of the message (what has happened)
	 * @param obj objects to add to the message
	 */
	default void addWarning(String what, Object ... obj){
		Message5WH message = new Message5WH_Builder()
				.addWhat(FormattingTupleWrapper.create(what, obj))
				.setType(MessageType.WARNING)
				.build()
		;
		this.add(MessageType.WARNING, message);
	}


	/**
	 * Clears all messages, removes them.
	 */
	default void clear(){
		for(Entry<MessageType, Integer> entry : this.getMsgCount().entrySet()){
			entry.setValue(0);
		}
		this.getMessages().clear();
	}

	/**
	 * Returns all information messages.
	 * @return list of messages in the order they were added
	 */
	default List<String> geInfos(){
		return this.getMessagesRendered(MessageType.INFO);
	}

	/**
	 * Returns all debug messages.
	 * @return list of messages in the order they were added
	 */
	default List<String> getDebug(){
		return this.getMessagesRendered(MessageType.DEBUG);
	}

	/**
	 * Returns all error messages.
	 * @return list of messages in the order they were added
	 */
	default List<String> getErrors(){
		return this.getMessagesRendered(MessageType.ERROR);
	}

	/**
	 * Returns all messages.
	 * @return list of messages in the order they were added
	 */
	Map<DoesRender, MessageType> getMessages();

	/**
	 * Returns all collected messages rendered as strings, same as a console print.
	 * @return rendered messages
	 */
	default List<String> getMessagesRendered(){
		return this.getMessagesRendered(null);
	}

	/**
	 * Returns all collected messages of a given type rendered as strings, same as console print.
	 * @param type the requested message type, empty set if null or no messages added for the type
	 * @return rendered messages
	 */
	default List<String> getMessagesRendered(MessageType type){
		List<String> ret = new ArrayList<>();
		for(Entry<DoesRender, MessageType> entry : this.getMessages().entrySet()){
			if(type==null || type==MessageType.ALL){
				ret.add(MessageConsole.conMessage(entry.getValue(), entry.getKey().render()));
			}
			else if(entry.getValue()==type){
				ret.add(MessageConsole.conMessage(entry.getValue(), entry.getKey().render()));
			}
		}
		return ret;
	}

	/**
	 * Returns the map of types and count of messages for each type.
	 * @return mapping of message type to count of that type
	 */
	Map<MessageType, Integer> getMsgCount();

	/**
	 * Returns the map of types and registered loggers for each type.
	 * @return mapping of message type to count of that type
	 */
	Map<MessageType, Logger> getMsgLoggers();

	/**
	 * Returns the map of types and maximum count of messages for each type.
	 * @return mapping of message type to maximumcount of that type
	 */
	Map<MessageType, Integer> getMsgMaxCount();

	/**
	 * Returns all trace messages.
	 * @return list of messages in the order they were added
	 */
	default List<String> getTraces(){
		return this.getMessagesRendered(MessageType.TRACE);
	}

	/**
	 * Returns all warning messages.
	 * @return list of messages in the order they were added
	 */
	default List<String> getWarnings(){
		return this.getMessagesRendered(MessageType.WARNING);
	}

	/**
	 * Returns the status of debug messages.
	 * @return true if messages have been added (count > 0), false otherwise
	 */
	default boolean hasDebug(){
		return this.hasMessages(MessageType.DEBUG);
	}

	/**
	 * Returns the status of error messages.
	 * @return true if messages have been added (count > 0), false otherwise
	 */
	default boolean hasErrors(){
		return this.hasMessages(MessageType.ERROR);
	}

	/**
	 * Returns the status of information messages.
	 * @return true if messages have been added (count > 0), false otherwise
	 */
	default boolean hasInfos(){
		return this.hasMessages(MessageType.INFO);
	}

	/**
	 * Returns the status of messages of a given type.
	 * @param type the message type, return of false if null
	 * @return true if messages have been added for the type (count > 0), false otherwise
	 */
	default boolean hasMessages(MessageType type){
		if(type!=null){
			Collection<MessageType> coll = this.getMessages().values();
			return coll.contains(type);
		}
		return false;
	}

	/**
	 * Returns the status of trace messages.
	 * @return true if messages have been added (count > 0), false otherwise
	 */
	default boolean hasTraces(){
		return this.hasMessages(MessageType.TRACE);
	}

	/**
	 * Returns the status of warning messages.
	 * @return true if messages have been added (count > 0), false otherwise
	 */
	default boolean hasWarnings(){
		return this.hasMessages(MessageType.WARNING);
	}

	/**
	 * Processes a message of type.
	 * The method will add the message to the local map and increase the count of the message type.
	 * Depending settings, it will also print to console, and log.
	 * If the maximum count for the message type has been reached, an additional message will be printed and logged.
	 * @param type the type, nothing done if null
	 * @param message the message, nothing done if null
	 */
	default void process(MessageType type, DoesRender message){
		if(type!=null && message!=null){
			MessageConsole.con(type, message);
			this.getMessages().put(message, type);
			this.getMsgCount().put(type, this.getMsgCount().get(type)+1);

			Logger logger = this.getMsgLoggers().get(type);
			String maxc = null;
			if(this.getMsgMaxCount().get(type)==this.getMsgCount().get(type)){
				maxc = "found more than " + this.getMsgMaxCount().get(type) + type.name().toLowerCase() + " messages";
			}
			if(maxc!=null){
				MessageConsole.con(MessageType.ERROR, maxc);
				if(logger!=null){
					logger.error(maxc);
				}
			}
			if(logger!=null){
					switch(type){
					case ALL:
						logger.info(message.render());
						break;
					case DEBUG:
						logger.debug(message.render());
						break;
					case ERROR:
						logger.error(message.render());
						break;
					case INFO:
						logger.info(message.render());
						break;
					case NONE:
						break;
					case TRACE:
						logger.trace(message.render());
						break;
					case WARNING:
						logger.warn(message.render());
						break;
				}
			}
		}
	}

	/**
	 * Sets the logger for all message types.
	 * @param logger new logger, ignored if null
	 */
	default void setLogger(Logger logger){
		if(logger!=null){
			for(Entry<MessageType, Logger> entry : this.getMsgLoggers().entrySet()){
				entry.setValue(logger);
			}
		}
	}

	/**
	 * Sets the logger for a given message type.
	 * @param type the message type, no logger added if null
	 * @param logger the logger, nothing added if null
	 */
	default void setLogger(MessageType type, Logger logger){
		if(type!=null && logger!=null){
			this.getMsgLoggers().put(type, logger);
		}
	}

	/**
	 * Sets the maximum count for all message types.
	 * @param maxCount new maximum count, ignored if smaller than 0
	 */
	default void setMaxCount(int maxCount){
		if(maxCount>0){
			for(Entry<MessageType, Integer> entry : this.getMsgMaxCount().entrySet()){
				entry.setValue(maxCount);
			}
		}
	}

	/**
	 * Sets the maximum count for a given message type.
	 * @param type the message type, nothing set if null
	 * @param maxCount new maximum count, ignored if smaller than 0 
	 */
	default void setMaxCount(MessageType type, int maxCount){
		if(type!=null && maxCount>0){
			this.getMsgMaxCount().put(type, maxCount);
		}
	}

	/**
	 * Creates a new message manager.
	 * @return message manager
	 */
	static MessageManager create(){
		Map<MessageType, Integer> count = new HashMap<>();
		count.put(MessageType.ALL, 0);
		count.put(MessageType.DEBUG, 0);
		count.put(MessageType.ERROR, 0);
		count.put(MessageType.INFO, 0);
		count.put(MessageType.NONE, 0);
		count.put(MessageType.TRACE, 0);
		count.put(MessageType.WARNING, 0);

		Map<MessageType, Integer> maxCount = new HashMap<>();
		count.put(MessageType.ALL, 100);
		count.put(MessageType.DEBUG, 100);
		count.put(MessageType.ERROR, 100);
		count.put(MessageType.INFO, 100);
		count.put(MessageType.NONE, 100);
		count.put(MessageType.TRACE, 100);
		count.put(MessageType.WARNING, 100);

		return new MessageManager() {
			Map<MessageType, Logger> loggers = new HashMap<>();
			Map<DoesRender, MessageType> messages = new LinkedHashMap<>();

			@Override
			public Map<MessageType, Integer> getMsgMaxCount() {
				return maxCount;
			}

			@Override
			public Map<MessageType, Logger> getMsgLoggers() {
				return this.loggers;
			}

			@Override
			public Map<MessageType, Integer> getMsgCount() {
				return count;
			}

			@Override
			public Map<DoesRender, MessageType> getMessages() {
				return this.messages;
			}
		};
	}
}
