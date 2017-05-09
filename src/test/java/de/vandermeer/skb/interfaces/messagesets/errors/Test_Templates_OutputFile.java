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

package de.vandermeer.skb.interfaces.messagesets.errors;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class Test_Templates_OutputFile {

	@Test
	public void test_Constructor(){
		@SuppressWarnings("unused")
		Templates_OutputFile[] errors = Templates_OutputFile.values();
	}

	@Test
	public void test_AppnameArg(){
		for(Templates_OutputFile error : Templates_OutputFile.values()){
			assertTrue(error.expectedArguments()>0);
		}
	}

	@Test
	public void test_Validation(){
		IsErrorTemplate.validate(Templates_OutputFile.values());
	}

	@Test
	public void test_SomeErrors(){
		String msg;

		msg = Templates_OutputFile.FN_NULL.getError("app", "output").getErrorMessageString();
		assertTrue(StringUtils.isNotBlank(msg));
		System.out.println(msg);

		msg = Templates_OutputFile.FN_HAS_EXTENSION.getError("app", "output", "me.zip").getErrorMessageString();
		assertTrue(StringUtils.isNotBlank(msg));
		System.out.println(msg);

		msg = Templates_OutputFile.FN_WRONG_EXTENSION.getError("app", "output", "me.zip", "one of pdf, png, or svg").getErrorMessageString();
		assertTrue(StringUtils.isNotBlank(msg));
		System.out.println(msg);
	}
}