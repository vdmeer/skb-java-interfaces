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
 * Things that represent a map strategy.
 * Strategies are defined for a number of standard Java map types:
 * 
 * * Abstract Map
 *   ** Enum Map {@link java.util.EnumMap} -> {@link de.vandermeer.skb.interfaces.strategies.maps.EnumMapStrategy}
 *   ** Hash Map {@link java.util.HashMap} -> {@link de.vandermeer.skb.interfaces.strategies.maps.HashMapStrategy}
 *   ** Identity Hash Map {@link java.util.IdentityHashMap} -> {@link de.vandermeer.skb.interfaces.strategies.maps.IdentityHashMapStrategy}
 *   ** Linked Hash Map {@link java.util.LinkedHashMap} -> {@link de.vandermeer.skb.interfaces.strategies.maps.LinkedHashMapStrategy}
 *   ** Weak Hash Map {@link java.util.WeakHashMap} -> {@link de.vandermeer.skb.interfaces.strategies.maps.WeakHashMapStrategy}
 * * Concurrent Map
 *   ** Concurrent Hash Map {@link java.util.concurrent.ConcurrentHashMap} -> {@link de.vandermeer.skb.interfaces.strategies.maps.ConcurrentHashMapStrategy}
 *   ** Concurrent Skip List Map {@link java.util.concurrent.ConcurrentSkipListMap} -> {@link de.vandermeer.skb.interfaces.strategies.maps.concurrentmap.ConcurrentSkipListMapStrategy}
 * * Hash Table
 *   ** Hash Table {@link java.util.Hashtable} -> {@link de.vandermeer.skb.interfaces.strategies.maps.HashtableStrategy}
 *   ** Properties {@link java.util.Properties} -> {@link de.vandermeer.skb.interfaces.strategies.maps.PropertiesStrategy}
 * * Navigable Map
 *   ** Concurrent Skip List Map {@link java.util.concurrent.ConcurrentSkipListMap} -> {@link de.vandermeer.skb.interfaces.strategies.maps.navigablemap.ConcurrentSkipListMapStrategy}
 *   ** Tree Map {@link java.util.TreeMap} -> {@link de.vandermeer.skb.interfaces.strategies.maps.navigablemap.TreeMapStrategy}
 * * Sorted Map
 *   ** Concurrent Skip List Map {@link java.util.concurrent.ConcurrentSkipListMap} -> {@link de.vandermeer.skb.interfaces.strategies.maps.ConcurrentSkipListMapStrategy}
 *   ** Tree Map {@link TreeMap} -> {@link de.vandermeer.skb.interfaces.strategies.maps.TreeMapStrategy}
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
package de.vandermeer.skb.interfaces.strategies.maps;
