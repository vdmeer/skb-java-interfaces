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

package de.vandermeer.skb.interfaces.messages.errors;

import org.junit.Test;

public class Test_Templates_AppOptionsGeneral {

	@Test
	public void test_Constructor(){
		@SuppressWarnings("unused")
		Templates_AppOptionsGeneral[] errors = Templates_AppOptionsGeneral.values();
	}

	@Test
	public void test_Validation(){
		IsErrorTemplate.validate(Templates_AppOptionsGeneral.values());
	}

	@Test
	public void test_SomeErrors(){
//		String msg;

//		msg = Templates_AppOptionsGeneral.DIR_NOT_SET.getError().getErrorMessageString();
//		assertTrue(StringUtils.isNotBlank(msg));
//		System.out.println(msg);
	}
}
