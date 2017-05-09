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

package de.vandermeer.skb.interfaces;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.slf4j.helpers.FormattingTuple;

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
	 * Flag to activate all messages.
	 * This flag is used as an über flag for printing messages.
	 * It will switch on all messages: errors, warnings, information, trace, and debug messages.
	 */
	public static final transient int ALL =			0b11111;

	/**
	 * Flag to activate error messages.
	 * If used, errors will be printed to standard error by the error methods in this class.
	 */
	public static final transient int ERRORS =		0b00001;

	/**
	 * Flag to activate warning messages.
	 * If used, warnings will be printed to standard out by the warning methods in this class.
	 */
	public static final transient int WARNINGS =	0b00010;

	/**
	 * Flag to activate information messages.
	 * If used, information messages will be printed to standard out by the information methods in this class.
	 */
	public static final transient int INFOS =		0b00100;

	/**
	 * Flag to activate trace messages.
	 * If used, trace messages will be printed to standard out by the trace methods in this class.
	 */
	public static final transient int TRACE =		0b01000;

	/**
	 * Flag to activate debug messages.
	 * If used, debug messages will be printed to standard out by the debug methods in this class.
	 */
	public static final transient int DEBUG =		0b10000;

	/**
	 * The bitmap for printing messages.
	 * The default is: errors, warnings, and information messages activated, trace and debug deactivated.
	 */
	private static transient int PRINT = ERRORS | WARNINGS | INFOS;

	/**
	 * Activate a particular message type.
	 * Use the defined static flags.
	 * For instance, to activate trace messages use `TRACE`.
	 * @param type the type to activate
	 */
	public static void activate(int type){
		PRINT = PRINT | type;
	}

	/**
	 * Activates all messages: errors, warnings, information, trace, and debug.
	 */
	public static void activateAll(){
		PRINT = ALL;
	}

	/**
	 * Prints a debug message to to standard out.
	 * @param msg debug message to print, ignored if null
	 */
	public static void conDebug(FormattingTupleWrapper msg){
		if(isActive(DEBUG) && msg!=null){
			System.out.println(msg.getMessage());
		}
	}

	/**
	 * Prints debug messages to to standard out.
	 * @param messages debug messages to print, ignored if null
	 */
	public static void conDebug(Set<FormattingTupleWrapper> messages){
		if(isActive(DEBUG) && messages!=null){
			for(FormattingTupleWrapper msg : messages){
				conDebug(msg);
			}
		}
	}

	/**
	 * Prints a debug message to standard out.
	 * @param msg debug message to print, nothing printed if null
	 */
	public static void conDebug(String msg){
		if(isActive(DEBUG) && msg!=null){
			System.out.println(msg);
		}
	}

	/**
	 * Prints a debug message to to standard out using a {@link FormattingTuple}.
	 * @param msg debug message to print, nothing printed if null
	 * @param args arguments for the message
	 */
	public static void conDebug(String msg, Object ... args){
		if(isActive(DEBUG) && msg!=null){
			System.out.println(FormattingTupleWrapper.create(msg, args).getMessage());
		}
	}

	/**
	 * Prints an error message to to standard error.
	 * @param msg error message to print, ignored if null
	 */
	public static void conError(FormattingTupleWrapper msg){
		if(isActive(ERRORS) && msg!=null){
			System.err.println(msg.getMessage());
		}
	}

	/**
	 * Prints error messages to to standard error.
	 * @param messages error messages to print, ignored if null
	 */
	public static void conError(Set<FormattingTupleWrapper> messages){
		if(isActive(ERRORS) && messages!=null){
			for(FormattingTupleWrapper msg : messages){
				conError(msg);
			}
		}
	}

	/**
	 * Prints an error message to standard error.
	 * @param msg error message to print, nothing printed if null
	 */
	public static void conError(String msg){
		if(isActive(ERRORS) && msg!=null){
			System.err.println(msg);
		}
	}

	/**
	 * Prints an error message to to standard error using a {@link FormattingTuple}.
	 * @param msg error message to print, nothing printed if null
	 * @param args arguments for the message
	 */
	public static void conError(String msg, Object ... args){
		if(isActive(ERRORS) && msg!=null){
			System.err.println(FormattingTupleWrapper.create(msg, args).getMessage());
		}
	}

	/**
	 * Prints an info message to to standard out.
	 * @param msg info message to print, ignored if null
	 */
	public static void conInfo(FormattingTupleWrapper msg){
		if(isActive(INFOS) && msg!=null){
			System.out.println(msg.getMessage());
		}
	}

	/**
	 * Prints info messages to to standard out.
	 * @param messages info messages to print, ignored if null
	 */
	public static void conInfo(Set<FormattingTupleWrapper> messages){
		if(isActive(INFOS) && messages!=null){
			for(FormattingTupleWrapper msg : messages){
				conInfo(msg);
			}
		}
	}

	/**
	 * Prints an info message to standard out.
	 * @param msg info message to print, nothing printed if null
	 */
	public static void conInfo(String msg){
		if(isActive(INFOS) && msg!=null){
			System.out.println(msg);
		}
	}

	/**
	 * Prints an info message to to standard out using a {@link FormattingTuple}.
	 * @param msg info message to print, nothing printed if null
	 * @param args arguments for the message
	 */
	public static void conInfo(String msg, Object ... args){
		if(isActive(INFOS) && msg!=null){
			System.out.println(FormattingTupleWrapper.create(msg, args).getMessage());
		}
	}

	/**
	 * Prints a trace message to to standard out.
	 * @param msg trace message to print, ignored if null
	 */
	public static void conTrace(FormattingTupleWrapper msg){
		if(isActive(TRACE) && msg!=null){
			System.out.println(msg.getMessage());
		}
	}

	/**
	 * Prints trace messages to to standard out.
	 * @param messages trace messages to print, ignored if null
	 */
	public static void conTrace(Set<FormattingTupleWrapper> messages){
		if(isActive(TRACE) && messages!=null){
			for(FormattingTupleWrapper msg : messages){
				conTrace(msg);
			}
		}
	}

	/**
	 * Prints a trace message to standard out.
	 * @param msg trace message to print, nothing printed if null
	 */
	public static void conTrace(String msg){
		if(isActive(TRACE) && msg!=null){
			System.out.println(msg);
		}
	}

	/**
	 * Prints a trace message to to standard out using a {@link FormattingTuple}.
	 * @param msg trace message to print, nothing printed if null
	 * @param args arguments for the message
	 */
	public static void conTrace(String msg, Object ... args){
		if(isActive(TRACE) && msg!=null){
			System.out.println(FormattingTupleWrapper.create(msg, args).getMessage());
		}
	}

	/**
	 * Prints a warning message to to standard out.
	 * @param msg warning message to print, ignored if null
	 */
	public static void conWarn(FormattingTupleWrapper msg){
		if(isActive(WARNINGS) && msg!=null){
			System.out.println(msg.getMessage());
		}
	}

	/**
	 * Prints warning messages to to standard out.
	 * @param messages warning messages to print, ignored if null
	 */
	public static void conWarn(Set<FormattingTupleWrapper> messages){
		if(isActive(WARNINGS) && messages!=null){
			for(FormattingTupleWrapper msg : messages){
				conWarn(msg);
			}
		}
	}

	/**
	 * Prints a warning message to to standard out.
	 * @param msg warning message to print, nothing printed if null
	 */
	public static void conWarn(String msg){
		if(isActive(WARNINGS) && msg!=null){
			System.out.println(msg);
		}
	}

	/**
	 * Prints a warning message to to standard out using a {@link FormattingTuple}.
	 * @param msg warning message to print, nothing printed if null
	 * @param args arguments for the message
	 */
	public static void conWarn(String msg, Object ... args){
		if(isActive(WARNINGS) && msg!=null){
			System.out.println(FormattingTupleWrapper.create(msg, args).getMessage());
		}
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
	 * @param appName application name for error messages
	 * @return new buffered reader for standard in using UTF-8 encoding, null if error occurred
	 */
	public static BufferedReader getStdIn(String appName){
		BufferedReader ret = null;
		try {
			ret = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		}
		catch(UnsupportedEncodingException ex) {
			MessageConsole.conError("{}: encoding exception opening SdtIn, expecting UTF-8 -> {}", appName, ex.getMessage());
		}
		return ret;
	}

	/**
	 * Tests if a particular type is activates
	 * @param type the type, use the defined static types of this class
	 * @return true if message type is activated, false otherwise
	 */
	private static boolean isActive(int type){
		return ((PRINT & type) == type);
	}

	/**
	 * Sets the print bitmap to the given value.
	 * To activate all messages use {@link #ALL}.
	 * To set a particular combination, use any of the defined message flags with a logical `or`.
	 * For instance, to activate error and debug messages use `ERRORS | DEBUG`.
	 * @param print the print bitmap to set
	 */
	public static void setPrint(int print){
		PRINT = print;
	}

	/**
	 * Constructor, throws exception since this class should not be instantiated.
	 * @throws UnsupportedOperationException be default
	 */
	public MessageConsole(){
		throw new UnsupportedOperationException(MessageConsole.class.getSimpleName() + " cannot be instantiated");
	}
}
