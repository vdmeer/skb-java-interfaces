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

package de.vandermeer.skb.interfaces.validators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * Tests for {@link STValidator}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public class Test_STValidator {

	public final String stgFileEmpty = "de/vandermeer/skb/interfaces/validators/test-empty.stg";

	public final String stgFileSimple = "de/vandermeer/skb/interfaces/validators/test-simple.stg";

	@Test
	public void testSuccess(){
		STGroupFile stg = new STGroupFile(stgFileSimple);
		STValidator stv = STValidator.create();

		assertTrue(stv.validate(stg.getInstanceOf("noArg"), new String[0]));
		assertTrue(stv.validate(stg.getInstanceOf("oneArg"), new String[]{"one"}));
		assertTrue(stv.validate(stg.getInstanceOf("twoArgs"), new String[]{"one", "two"}));
	}

	@Test
	public void testError(){
		STGroupFile stg = new STGroupFile(stgFileSimple);
		STValidator stv = STValidator.create();

		assertFalse(stv.validate(stg.getInstanceOf("twoArgs"), new String[]{"one", "three"}));
	}

	@Test
	public void testNull(){
		STValidator stv = STValidator.create();

		assertFalse(stv.validate(null, new String[0]));
		assertEquals(1, stv.getErrorSet().size());

		assertFalse(stv.validate(null, null));
		assertEquals(1, stv.getErrorSet().size());
	}

	@Test
	public void testEmpty(){
		STValidator stv = STValidator.create();

		assertTrue(stv.validate(new ST(""), new String[0]));
		assertEquals(0, stv.getErrorSet().size());
	}

	@Test
	public void testNoArgST(){
		STValidator stv = STValidator.create();
		STGroup stg = new STGroupFile(this.stgFileSimple);
		assertNotNull(stg);

		assertTrue(stv.validate(stg.getInstanceOf("noArg"), new String[0]));

		assertFalse(stv.validate(stg.getInstanceOf("noArg"), new String[]{"one"}));
		assertEquals(1, stv.getErrorSet().size());

		assertFalse(stv.validate(stg.getInstanceOf("noArg"), new String[]{"one", "two"}));
		assertEquals(2, stv.getErrorSet().size());
	}

	@Test
	public void test1ArgST(){
		STValidator stv = STValidator.create();
		STGroup stg = new STGroupFile(this.stgFileSimple);
		assertNotNull(stg);

		assertTrue(stv.validate(stg.getInstanceOf("oneArg"), new String[0]));

		assertTrue(stv.validate(stg.getInstanceOf("oneArg"), new String[]{"one"}));

		assertFalse(stv.validate(stg.getInstanceOf("oneArg"), new String[]{"one", "two"}));
		assertEquals(1, stv.getErrorSet().size());
	}

	@Test
	public void test2ArgST(){
		STValidator stv = STValidator.create();
		STGroup stg = new STGroupFile(this.stgFileSimple);
		assertNotNull(stg);

		assertTrue(stv.validate(stg.getInstanceOf("twoArgs"), new String[0]));

		assertTrue(stv.validate(stg.getInstanceOf("twoArgs"), new String[]{"one"}));
		assertTrue(stv.validate(stg.getInstanceOf("twoArgs"), new String[]{"one", "two"}));

		assertFalse(stv.validate(stg.getInstanceOf("twoArgs"), new String[]{"one", "two", "three"}));
		assertEquals(1, stv.getErrorSet().size());

		assertFalse(stv.validate(stg.getInstanceOf("twoArgs"), new String[]{"one", "two", "three", "four"}));
		assertEquals(2, stv.getErrorSet().size());

		assertFalse(stv.validate(stg.getInstanceOf("twoArgs"), new String[]{"one", "two", "three", "four", "five"}));
		assertEquals(3, stv.getErrorSet().size());
	}

}
