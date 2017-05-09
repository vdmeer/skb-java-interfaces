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

package de.vandermeer.skb.interfaces.antlr;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import de.vandermeer.skb.interfaces.messagesets.errors.IsError;

public class Test_IsStGroup {

	@Test
	public void test_BadSTG(){
		Map<String, String[]> chunks = new HashMap<>();
		chunks.put("test", new String[]{"arg1", "arg2"});
		IsSTGroup isStg = IsSTGroup.fromFile("de/vandermeer/skb/interfaces/antlr/bad.stg", chunks, "test-app");
		Set<IsError> errors = isStg.validate();
		assertEquals(1, errors.size());
		for(IsError err : errors){
			System.out.println(err.getErrorMessageString());
		}
	}

	@Test
	public void test_MissingST(){
		Map<String, String[]> chunks = new HashMap<>();
		chunks.put("test", new String[]{"arg1", "arg2"});
		IsSTGroup isStg = IsSTGroup.fromFile("de/vandermeer/skb/interfaces/antlr/missing-st.stg", chunks, "test-app");
		Set<IsError> errors = isStg.validate();
		assertEquals(1, errors.size());
		for(IsError err : errors){
			System.out.println(err.getErrorMessageString());
		}
	}

	@Test
	public void test_MissingSTArg(){
		Map<String, String[]> chunks = new HashMap<>();
		chunks.put("test", new String[]{"arg1", "arg2"});
		IsSTGroup isStg = IsSTGroup.fromFile("de/vandermeer/skb/interfaces/antlr/missing-arg.stg", chunks, "test-app");
		Set<IsError> errors = isStg.validate();
		assertEquals(2, errors.size());
		for(IsError err : errors){
			System.out.println(err.getErrorMessageString());
		}
	}

	@Test
	public void test_GoodSTG(){
		Map<String, String[]> chunks = new HashMap<>();
		chunks.put("test", new String[]{"arg1", "arg2"});
		IsSTGroup isStg = IsSTGroup.fromFile("de/vandermeer/skb/interfaces/antlr/good.stg", chunks, "test-app");
		Set<IsError> errors = isStg.validate();
		assertEquals(0, errors.size());
	}
}
