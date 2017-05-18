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

package de.vandermeer.skb.interfaces.console;

import static de.vandermeer.skb.interfaces.MessageType.ALL;
import static de.vandermeer.skb.interfaces.MessageType.ERROR;
import static de.vandermeer.skb.interfaces.MessageType.INFO;
import static de.vandermeer.skb.interfaces.MessageType.WARNING;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import de.vandermeer.skb.interfaces.MessageType;
import de.vandermeer.skb.interfaces.render.DoesRender;

/**
 * Utilities for message input/output to a console using loggers.
 * The interface provides settings and method to print information, warning, error, trace, and debug messages.
 * While information, warnings, and errors are obviously of different type, trace and debug messages are arbitrary (i.e. whatever you want it to be).
 * This interface only provides means to separate those types, and handle them independently.
 * This also means that, in contrast to logging frameworks, there is no hierarchy of message types.
 * The behavior of each type can be set separately, without impacting the behavior of any other type.
 * Additionally, an über flag is provided to switch off all message printing.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public final class MessageConsole {

	/**
	 * The bitmap for printing messages.
	 * The default is: errors, warnings, and information messages activated, trace and debug deactivated.
	 */
	private static transient int PRINT = ERROR.getFlag() | WARNING.getFlag() | INFO.getFlag();

	/**
	 * An application or object name to prefix messages with.
	 * If the name is blank, no prefix will be created, otherwise the prefix is the name followed by a colon and a space.
	 */
	private static transient String APPLICATION_NAME = "";

	/** Prefix for error messages. */
	private static transient String ERROR_PREFIX = "error: ";

	/** Prefix for warning messages. */
	private static transient String WARNING_PREFIX = "warning: ";

	/** Prefix for information messages. */
	private static transient String INFO_PREFIX = "";

	/** Prefix for trace messages. */
	private static transient String TRACE_PREFIX = " ---> ";

	/** Prefix for debug messages. */
	private static transient String DEBUG_PREFIX = " ===> ";

	/**
	 * Activate a particular message type.
	 * For instance, to activate trace messages use `TRACE`.
	 * @param type the type to activate, ignored if null
	 * @return the new flag
	 */
	public static int activate(MessageType type){
		if(type!=null){
			PRINT = PRINT | type.getFlag();
		}
		return PRINT;
	}

	/**
	 * Activates all messages: errors, warnings, information, trace, and debug.
	 * @return the new flag
	 */
	public static int activateAll(){
		PRINT = ALL.getFlag();
		return PRINT;
	}

	/**
	 * Prints a message of given type.
	 * @param type the type, noting done if null or {@link MessageType#NONE}
	 * @param msg the message, nothing done if null
	 */
	public static void con(MessageType type, DoesRender msg){
		if(type==null){
			return;
		}
		print(type, msg.render(), true);
	}

	/**
	 * Prints a message of given type.
	 * @param type the type, noting done if null or {@link MessageType#NONE}
	 * @param msg the message, nothing done if null
	 * @param newline true for a newline after the message, false otherwise
	 */
	public static void con(MessageType type, DoesRender msg, boolean newline){
		if(type==null){
			return;
		}
		print(type, msg.render(), newline);
	}

	/**
	 * Prints a message of given type.
	 * @param type the type, noting done if null or {@link MessageType#NONE}
	 * @param messages the messages, nothing done if null
	 */
	public static void con(MessageType type, Set<DoesRender> messages){
		if(type==null){
			return;
		}
		for(DoesRender dr : messages){
			print(type, dr.render(), true);
		}
	}

	/**
	 * Prints a message of given type.
	 * @param type the type, noting done if null or {@link MessageType#NONE}
	 * @param messages the messages, nothing done if null
	 * @param newline true for a newline after the message, false otherwise
	 */
	public static void con(MessageType type, Set<DoesRender> messages, boolean newline){
		if(type==null){
			return;
		}
		for(DoesRender dr : messages){
			print(type, dr.render(), newline);
		}
	}

	/**
	 * Prints a message of given type.
	 * @param type the type, noting done if null or {@link MessageType#NONE}
	 * @param msg the message, nothing done if null
	 */
	public static void con(MessageType type, StrBuilder msg){
		print(type, msg.build(), true);
	}

	/**
	 * Prints a message of given type.
	 * @param type the type, noting done if null or {@link MessageType#NONE}
	 * @param msg the message, nothing done if null
	 * @param newline true for a newline after the message, false otherwise
	 */
	public static void con(MessageType type, StrBuilder msg, boolean newline){
		print(type, msg.build(), newline);
	}

	/**
	 * Prints a message of given type.
	 * @param type the type, noting done if null or {@link MessageType#NONE}
	 * @param msg the message, nothing done if null
	 */
	public static void con(MessageType type, String msg){
		print(type, msg, true);
	}

	/**
	 * Prints a message of given type.
	 * @param type the type, noting done if null or {@link MessageType#NONE}
	 * @param msg the message, nothing done if null
	 * @param newline true for a newline after the message, false otherwise
	 */
	public static void con(MessageType type, String msg, boolean newline){
		print(type, msg, newline);
	}

	/**
	 * Prints a message using a {@link FormattingTuple}.
	 * @param type the type, noting done if null or {@link MessageType#NONE}
	 * @param msg message to print, nothing printed if null
	 * @param args arguments for the message
	 */
	public static void con(MessageType type, String msg, Object ... args){
		print(type, MessageFormatter.arrayFormat(msg, args).getMessage(), true);
	}

	/**
	 * Prints a message using a {@link FormattingTuple}.
	 * @param type the type, noting done if null or {@link MessageType#NONE}
	 * @param newline true for a newline after the message, false otherwise
	 * @param msg message to print, nothing printed if null
	 * @param args arguments for the message
	 */
	public static void con(MessageType type, boolean newline, String msg, Object ... args){
		print(type, MessageFormatter.arrayFormat(msg, args).getMessage(), newline);
	}

	/**
	 * Deactivate a particular message type.
	 * Use the defined static flags.
	 * For instance, to activate trace messages use `TRACE`.
	 * @param type the type to de-activate
	 */
	public static void deActivate(int type){
		PRINT = PRINT & ~type;
	}

	/**
	 * Builds a message using the application and message specific prefixes.
	 * @param type the type, noting done if null or {@link MessageType#NONE}
	 * @param msg the message, nothing done if null
	 * @return built message string, null if type or msg where null
	 */
	public static String generateMessage(MessageType type, String msg){
		if(type!=null && !StringUtils.isBlank(msg)){
			switch(type){
				case DEBUG:
					return APPLICATION_NAME + DEBUG_PREFIX + msg;
				case ERROR:
					return APPLICATION_NAME + ERROR_PREFIX + msg;
				case INFO:
					return APPLICATION_NAME + INFO_PREFIX + msg;
				case TRACE:
					return APPLICATION_NAME + TRACE_PREFIX + msg;
				case WARNING:
					return APPLICATION_NAME + WARNING_PREFIX + msg;
				case ALL:
					return APPLICATION_NAME + msg;
				case NONE:
					return null;
			}
		}
		else if(StringUtils.isBlank(msg)){
			return "";
		}
		return null;
	}

	/**
	 * Returns the default runtime encoding.
	 * @return string with the encoding
	 */
	public static String getDefaultEncoding(){
		byte [] byteArray = {'a'};
		InputStream inputStream = new ByteArrayInputStream(byteArray);
		InputStreamReader reader = new InputStreamReader(inputStream);
		return(reader.getEncoding());
	}

	/**
	 * Returns a new buffered reader for standard in, using UTF-8 encoding.
	 * @return new buffered reader for standard in using UTF-8 encoding, null if error occurred
	 */
	public static BufferedReader getStdIn(){
		BufferedReader ret = null;
		try {
			ret = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		}
		catch(UnsupportedEncodingException ex) {
			MessageConsole.con(MessageType.ERROR, "encoding exception opening SdtIn, expecting UTF-8 -> {}", ex.getMessage());
		}
		return ret;
	}

	/**
	 * Tests if a particular type is activates.
	 * @param type the type
	 * @return true if message type is activated, false otherwise
	 */
	public static boolean isActive(int type){
		return ((PRINT & type) == type);
	}

	/**
	 * Prints a message of given type.
	 * @param type the type, noting done if null or {@link MessageType#NONE}
	 * @param msg the message, nothing done if null
	 * @param newline true for a newline after the message, false otherwise
	 * @return the printed message
	 */
	private static void print(MessageType type, String msg, boolean newline){
		if(type==null || !isActive(type.getFlag())){
			return;
		}
		String message = generateMessage(type, msg);
		if(msg!=null){
			if(type==MessageType.ERROR){
				System.err.print(message);
				if(newline){
					System.err.println();
				}
			}
			else{
				System.out.print(message);
				if(newline){
					System.out.println();
				}
			}
		}
	}

	/**
	 * Sets an application name as prefix for messages.
	 * @param appName new application name, null or blank of none required
	 */
	public static void setApplicationName(String appName){
		if(StringUtils.isBlank(appName)){
			APPLICATION_NAME = "";
		}
		else{
			APPLICATION_NAME = appName + ": ";
		}
	}


	/**
	 * Sets an debug prefix for messages.
	 * @param prefix new prefix, null or blank of none required
	 */
	public static void setDebugPrefix(String prefix){
		if(StringUtils.isBlank(prefix)){
			DEBUG_PREFIX = "";
		}
		else{
			DEBUG_PREFIX = prefix + " ";
		}
	}

	/**
	 * Sets an error prefix for messages.
	 * @param prefix new prefix, null or blank of none required
	 */
	public static void setErrorPrefix(String prefix){
		if(StringUtils.isBlank(prefix)){
			ERROR_PREFIX = "";
		}
		else{
			ERROR_PREFIX = prefix + " ";
		}
	}

	/**
	 * Sets an info prefix for messages.
	 * @param prefix new prefix, null or blank of none required
	 */
	public static void setInfoPrefix(String prefix){
		if(StringUtils.isBlank(prefix)){
			INFO_PREFIX = "";
		}
		else{
			INFO_PREFIX = prefix + " ";
		}
	}

	/**
	 * Sets the print bitmap to the given value.
	 * To activate all messages use {@link MessageType#ALL}.
	 * To set a particular combination, use any of the defined message flags with a logical `or`.
	 * For instance, to activate error and debug messages use `ERRORS | DEBUG`.
	 * @param print the print bitmap to set
	 * @return the new flag
	 */
	public static int setPrint(int print){
		PRINT = print;
		return PRINT;
	}

	/**
	 * Sets an trace prefix for messages.
	 * @param prefix new prefix, null or blank of none required
	 */
	public static void setTracePrefix(String prefix){
		if(StringUtils.isBlank(prefix)){
			TRACE_PREFIX = "";
		}
		else{
			TRACE_PREFIX = prefix + " ";
		}
	}

	/**
	 * Sets an warning prefix for messages.
	 * @param prefix new prefix, null or blank of none required
	 */
	public static void setWarningPrefix(String prefix){
		if(StringUtils.isBlank(prefix)){
			WARNING_PREFIX = "";
		}
		else{
			WARNING_PREFIX = prefix + " ";
		}
	}

	/**
	 * Constructor, throws exception since this class should not be instantiated.
	 * @throws UnsupportedOperationException be default
	 */
	public MessageConsole(){
		throw new UnsupportedOperationException(MessageConsole.class.getSimpleName() + " cannot be instantiated");
	}
}
