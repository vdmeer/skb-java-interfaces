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

/**
 * Things that represent a collection strategy for sets.
 * Strategies are defined for a number of standard Java map types:
 * 
 * * Set
 *   ** Hash Set {@link java.util.HashSet} -> {@link de.vandermeer.skb.interfaces.strategies.collections.set.HashSetStrategy}
 *   ** Linked Hash Set {@link java.util.LinkedHashSet} -> {@link de.vandermeer.skb.interfaces.strategies.collections.set.LinkedHashSetStrategy}
 * 
 * * Sorted Set
 *   ** Concurrent Skip Set {@link java.util.concurrent.ConcurrentSkipListSet} -> {@link de.vandermeer.skb.interfaces.strategies.collections.set.ConcurrentSkipSetStrategy}
 *   ** Tree Set {@link java.util.TreeSet} -> {@link de.vandermeer.skb.interfaces.strategies.collections.set.TreeSetStrategy}
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
package de.vandermeer.skb.interfaces.strategies.collections.set;
