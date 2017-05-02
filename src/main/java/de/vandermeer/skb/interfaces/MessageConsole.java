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
	 * Print message flag.
	 * 
	 * This flag is used as an über flag for printing messages.
	 * If set to true, messages in general can be printed using the methods in this interface.
	 * If set to false, no messages at all should be printed by any method in this interface.
	 * 
	 * The flag is static, so it is the same for all applications running in the JVM.
	 * 
	 */
	public static boolean PRINT_MESSAGES = true;

	/**
	 * Print error message flag.
	 * 
	 * Errors will be printed to standard error by the error methods in this interface.
	 * If this flag is false, no errors will be printed.
	 * If this flag is false and {@link #PRINT_MESSAGES} is false, no error messages will be printed.
	 * If this flag is true and {@link #PRINT_MESSAGES} is true, error messages will be printed.
	 * 
	 */
	public static boolean PRINT_ERROR_MESSAGES = true & MessageConsole.PRINT_MESSAGES;

	/**
	 * Print warning message flag.
	 * 
	 * Warnings will be printed to standard out by the warning methods in this interface.
	 * If this flag is false, no warnings will be printed.
	 * If this flag is false and {@link #PRINT_MESSAGES} is false, no warning messages will be printed.
	 * If this flag is true and {@link #PRINT_MESSAGES} is true, warning messages will be printed.
	 * 
	 */
	public static boolean PRINT_WARNING_MESSAGES = true & MessageConsole.PRINT_MESSAGES;

	/**
	 * Print info message flag.
	 * 
	 * Info messages will be printed to standard out by the info methods in this interface.
	 * If this flag is false, no info messages will be printed.
	 * If this flag is false and {@link #PRINT_MESSAGES} is false, no info messages will be printed.
	 * If this flag is true and {@link #PRINT_MESSAGES} is true, info messages will be printed.
	 * 
	 */
	public static boolean PRINT_INFO_MESSAGES = true & MessageConsole.PRINT_MESSAGES;

	/**
	 * Print info trace flag.
	 * 
	 * Trace messages will be printed to standard out by the info methods in this interface.
	 * If this flag is false, no trace messages will be printed.
	 * If this flag is false and {@link #PRINT_MESSAGES} is false, no trace messages will be printed.
	 * If this flag is true and {@link #PRINT_MESSAGES} is true, trace messages will be printed.
	 * 
	 */
	public static boolean PRINT_TRACE_MESSAGES = true & MessageConsole.PRINT_MESSAGES;

	/**
	 * Print info debug flag.
	 * 
	 * Debug messages will be printed to standard out by the info methods in this interface.
	 * If this flag is false, no debug messages will be printed.
	 * If this flag is false and {@link #PRINT_MESSAGES} is false, no debug messages will be printed.
	 * If this flag is true and {@link #PRINT_MESSAGES} is true, debug messages will be printed.
	 * 
	 */
	public static boolean PRINT_DEBUG_MESSAGES = true & MessageConsole.PRINT_MESSAGES;

	/**
	 * Prints an info message to standard out.
	 * @param msg info message to print
	 */
	public static void conInfo(String msg){
		if(PRINT_INFO_MESSAGES){
			System.out.println(msg);
		}
	}

	/**
	 * Prints an info message to to standard out using a {@link FormattingTuple}.
	 * @param msg info message to print
	 * @param args arguments for the message
	 */
	public static void conInfo(String msg, Object ... args){
		if(PRINT_INFO_MESSAGES){
			System.out.println(FormattingTupleWrapper.create(msg, args).getMessage());
		}
	}

	/**
	 * Prints a warning message to to standard out.
	 * @param msg warning message to print
	 */
	public static void conWarn(String msg){
		if(PRINT_WARNING_MESSAGES){
			System.out.println(msg);
		}
	}

	/**
	 * Prints a warning message to to standard out using a {@link FormattingTuple}.
	 * @param msg warning message to print
	 * @param args arguments for the message
	 */
	public static void conWarn(String msg, Object ... args){
		if(PRINT_WARNING_MESSAGES){
			System.out.println(FormattingTupleWrapper.create(msg, args).getMessage());
		}
	}

	/**
	 * Prints an error message to standard error.
	 * @param msg error message to print
	 */
	public static void conError(String msg){
		if(PRINT_ERROR_MESSAGES){
			System.err.println(msg);
		}
	}

	/**
	 * Prints an error message to to standard error using a {@link FormattingTuple}.
	 * @param msg error message to print
	 * @param args arguments for the message
	 */
	public static void conError(String msg, Object ... args){
		if(PRINT_ERROR_MESSAGES){
			System.err.println(FormattingTupleWrapper.create(msg, args).getMessage());
		}
	}

	/**
	 * Prints a trace message to standard out.
	 * @param msg trace message to print
	 */
	public static void conTrace(String msg){
		if(PRINT_TRACE_MESSAGES){
			System.out.println(msg);
		}
	}

	/**
	 * Prints a trace message to to standard out using a {@link FormattingTuple}.
	 * @param msg trace message to print
	 * @param args arguments for the message
	 */
	public static void conTrace(String msg, Object ... args){
		if(PRINT_TRACE_MESSAGES){
			System.out.println(FormattingTupleWrapper.create(msg, args).getMessage());
		}
	}

	/**
	 * Prints a debug message to standard out.
	 * @param msg debug message to print
	 */
	public static void conDebug(String msg){
		if(PRINT_DEBUG_MESSAGES){
			System.out.println(msg);
		}
	}

	/**
	 * Prints a debug message to to standard out using a {@link FormattingTuple}.
	 * @param msg debug message to print
	 * @param args arguments for the message
	 */
	public static void conDebug(String msg, Object ... args){
		if(PRINT_DEBUG_MESSAGES){
			System.out.println(FormattingTupleWrapper.create(msg, args).getMessage());
		}
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
	 * Constructor, throws exception since this class should not be instantiated.
	 * @throws UnsupportedOperationException be default
	 */
	public MessageConsole(){
		throw new UnsupportedOperationException(MessageConsole.class.getSimpleName() + " cannot be instantiated");
	}
}
