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

import java.util.function.Predicate;

public class Test_ClusterElementTransformer {

	Predicate<String> predicate = new Predicate<String>() {
		@Override
		public boolean test(String t) {
			if("one".equals(t)){
				return true;
			}
			return false;
		}
	};

//	@Test
//	public void test_ClusterElementTransformer(){
//		//TODO this is only a small test
//		ArrayList<String> al = new ArrayList<>();
//		al.add("one");
//		al.add("two");
//		al.add("three");
//
//		ClusterElementTransformer mct = ClusterElementTransformer.create();
//		ArrayDeque<String> ad = mct.transform(al, Object_To_RenderedString.create(), ArrayDequeStrategy.create());
//		System.err.println(ad);
//
//		ad = mct.transform(al, Object_To_RenderedString.create(), predicate, ArrayDequeStrategy.create());
//		System.err.println(ad);
//	}
}
