/* Copyright 2014 Sven van der Meer <vdmeer.sven@mykolab.com>
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

/**
 * Types and flags for message handling APIs.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public enum MessageType {

	/** A flag for no messages, deactivate message printing. */
	NONE (0),

	/**
	 * An type for error messages and flag to activate error messages.
	 * If used as flag, errors will be printed to standard error.
	 */
	ERROR (0b00001),

	/**
	 * An type for warning messages and flag to activate warning messages.
	 * If used as flag, warnings will be printed to standard out.
	 */
	WARNING (0b00010),

	/**
	 * An type for information messages and flag to activate information messages.
	 * If used as flag, information will be printed to standard out.
	 */
	INFO (0b00100),

	/**
	 * An type for trace messages and flag to activate trace messages.
	 * If used as flag, traces will be printed to standard out.
	 */
	TRACE (0b01000),

	/**
	 * An type for debug messages and flag to activate debug messages.
	 * If used as flag, debug information will be printed to standard out.
	 */
	DEBUG (0b10000),

	/**
	 * A type covering all messages or a flag to activate all messages.
	 * As flag, this is an über flag for printing messages.
	 * It will switch on all messages: errors, warnings, information, trace, and debug messages.
	 */
	ALL (0b11111),

	;

	/** Message flag. */
	private final int flag;

	/**
	 * Creates a new message type
	 * @param flag the flag, must be unique among all message types
	 */
	MessageType(final int flag){
		this.flag = flag;
	}

	/**
	 * Returns the message flag.
	 * @return message flag
	 */
	public int getFlag(){
		return this.flag;
	}
}
