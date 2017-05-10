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

package de.vandermeer.skb.interfaces.messages.sets;

import java.util.LinkedHashSet;
import java.util.Set;

import de.vandermeer.skb.interfaces.MessageType;
import de.vandermeer.skb.interfaces.render.DoesRender;

/**
 * Interface for objects that have a set of trace messages.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.3
 */
public interface IsTraceSet extends IsMessageSet {

	/**
	 * Creates a new trace set.
	 * @return new trace set
	 */
	static IsTraceSet create(){
		return new IsTraceSet() {
			Set<DoesRender> messages = new LinkedHashSet<>();

			@Override
			public Set<DoesRender> getMessages() {
				return this.messages;
			}
		};
	}

	@Override
	default MessageType getType(){
		return MessageType.TRACE;
	}
}
