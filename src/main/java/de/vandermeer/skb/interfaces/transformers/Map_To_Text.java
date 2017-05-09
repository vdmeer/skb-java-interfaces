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

package de.vandermeer.skb.interfaces.transformers;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

/**
 * Transforms a map into (well-formatted) text.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface Map_To_Text extends IsTransformer<Map<?, ?>, String> {

	/**
	 * Creates a transformer that transforms a map into a textual representation, for instance for debug output.
	 * @return new transformer
	 */
	static Map_To_Text create(){
		return new Map_To_Text() {};
	}

	/**
	 * Transforms a map into a textual representation, for instance for debug output.
	 * @param map input collection
	 * @return textual representation of the map, empty string as default
	 */
	@Override
	default String transform(Map<?, ?> map){
		String collG = "map(tree) ::= <<\n    <tree.keys:{k | - <k> ==> [<tree.(k).(\"type\")> <tree.(k).(\"val\")>]}; separator=\"\n\">\n>>";
		STGroup stg = new STGroupString(collG);
		ST ret = stg.getInstanceOf("map");

		LinkedHashMap<String, LinkedHashMap<String, String>> tree = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		@SuppressWarnings("unchecked")
		Set<String> keySet = (Set<String>)map.keySet();
		Iterator<String> keyIt = keySet.iterator();
		while(keyIt.hasNext()){
			LinkedHashMap<String, String> node = new LinkedHashMap<String, String>();
			String key = keyIt.next();

			Object v = map.get(key);
			if(v!=null){
				node.put("type", v.getClass().getSimpleName());//TODO add maybe TypeMap
				String val = v.toString();
				if(val.contains("\n")){
					node.put("val", "\n    "+val);
				}
				else{
					node.put("val", val);
				}
				tree.put(key, node);
			}
		}
		ret.add("tree", tree);
		return ret.render();
	}
}
