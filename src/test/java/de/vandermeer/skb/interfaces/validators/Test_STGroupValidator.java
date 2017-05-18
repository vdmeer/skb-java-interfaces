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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.skb.interfaces.render.DoesRender;

/**
 * Tests for {@link STGValidator}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public class Test_STGroupValidator {

	public final String stgFileEmpty = "de/vandermeer/skb/interfaces/validators/test-empty.stg";

	public final String stgFileSimple = "de/vandermeer/skb/interfaces/validators/test-simple.stg";

	@Test
	public void testSuccess(){
		STGroupFile stg = new STGroupFile(stgFileSimple);
		STGValidator stgv = STGValidator.create();

		Map<String, String[]> map = new HashMap<>();
		map.put("noArg", new String[0]);
		map.put("oneArg", new String[]{"one"});
		map.put("twoArgs", new String[]{"one", "two"});

		assertTrue(stgv.validate(stg, map));
	}

	@Test
	public void testErrorS1(){
		STGroupFile stg = new STGroupFile(stgFileSimple);
		STGValidator stgv = STGValidator.create();

		Map<String, String[]> map = new HashMap<>();
		map.put("noArg", new String[0]);
		map.put("oneArg", new String[]{"one"});
		map.put("twoArgs", new String[]{"one", "three"});

		assertFalse(stgv.validate(stg, map));
		assertEquals(2, stgv.getErrorSet().size());
	}

	@Test
	public void testErrorS2(){
		STGroupFile stg = new STGroupFile(stgFileSimple);
		STGValidator stgv = STGValidator.create();

		Map<String, String[]> map = new HashMap<>();
		map.put("noArg", new String[0]);
		map.put("oneArg", new String[]{"one"});
		map.put("twoArgs", new String[]{"three", "four"});

		assertFalse(stgv.validate(stg, map));
		assertEquals(3, stgv.getErrorSet().size());
	}

	@Test
	public void testNull(){
		STGValidator stgv = STGValidator.create();

		assertFalse(stgv.validate(null, new HashMap<String, String[]>()));
		assertEquals(1, stgv.getErrorSet().size());

//		assertFalse(stgv.validate(new STGroup(), null));
//		assertEquals(1, stgv.getErrorSet().size());

		assertFalse(stgv.validate(null, null));
		assertEquals(1, stgv.getErrorSet().size());
	}

	@Test
	public void testEmpty(){
		STGValidator stgv = STGValidator.create();

		assertTrue(stgv.validate(new STGroup(), new HashMap<String, String[]>()));
	}

	@Test
	public void testNoArgSTG(){
		Map<String, String[]> chunks = new HashMap<>();
		STGroup stg = new STGroupFile(this.stgFileSimple);
		assertNotNull(stg);
		STGValidator stgv = STGValidator.create();

		assertTrue(stgv.validate(stg, chunks));

//		chunks.put(null, null);
//		assertTrue(stgv.validate(stg, chunks));

//		chunks.put("", null);
//		assertTrue(stgv.validate(stg, chunks));

//		chunks.put(null, new String[0]);
//		assertTrue(stgv.validate(stg, chunks));

//		chunks.put("noArg", null);
//		assertTrue(stgv.validate(stg, chunks));

		chunks.put("noArg", new String[0]);
		assertTrue(stgv.validate(stg, chunks));
	}

	@Test
	public void test1ArgST(){
		Map<String, String[]> chunks = new HashMap<>();
		STGroup stg = new STGroupFile(this.stgFileSimple);
		assertNotNull(stg);
		STGValidator stgv = STGValidator.create();

		chunks.put("oneArg", new String[0]);
		assertTrue(stgv.validate(stg, chunks));

		chunks.put("oneArg", new String[]{"one"});
		assertTrue(stgv.validate(stg, chunks));

		chunks.put("oneArg", new String[]{"one", "two"});
		assertFalse(stgv.validate(stg, chunks));
		assertEquals(2, stgv.getErrorSet().size());

		chunks.put("oneArg", new String[]{"one", "two", "three"});
		assertFalse(stgv.validate(stg, chunks));
		assertEquals(3, stgv.getErrorSet().size());
	}

	@Test
	public void test2ArgST(){
		Map<String, String[]> chunks = new HashMap<>();
		STGroup stg = new STGroupFile(this.stgFileSimple);
		assertNotNull(stg);
		STGValidator stgv = STGValidator.create();

		chunks = new HashMap<String, String[]>(){
			private static final long serialVersionUID = 1L;{
				put("noArg", new String[0]);
				put("oneArg", new String[]{"one"});
				put("twoArgs", new String[]{"one", "two"});
			}
		};
		assertTrue(stgv.validate(stg, chunks));

		chunks = new HashMap<String, String[]>(){
			private static final long serialVersionUID = 1L;{
				put("noArg", new String[0]);
				put("oneArg", new String[]{"three"});
				put("twoArgs", new String[]{"four", "five"});
			}
		};
		assertFalse(stgv.validate(stg, chunks));
		assertEquals(5, stgv.getErrorSet().size());
	}

	@Test
	public void test_BadSTG(){
		STGValidator stgv = STGValidator.create();
		Map<String, String[]> chunks = new HashMap<>();
		chunks.put("test", new String[]{"arg1", "arg2"});
		STGroupFile stg = new STGroupFile("de/vandermeer/skb/interfaces/validators/bad.stg");

		assertFalse(stgv.validate(stg, chunks));
		assertEquals(1, stgv.getErrorSet().size());
		for(DoesRender err : stgv.getErrorSet().getMessages()){
			System.out.println(err.render());
		}
	}

	@Test
	public void test_MissingST(){
		STGValidator stgv = STGValidator.create();
		Map<String, String[]> chunks = new HashMap<>();
		chunks.put("test", new String[]{"arg1", "arg2"});
		STGroupFile stg = new STGroupFile("de/vandermeer/skb/interfaces/validators/missing-st.stg");

		assertFalse(stgv.validate(stg, chunks));
		assertEquals(1, stgv.getErrorSet().size());
		for(DoesRender err : stgv.getErrorSet().getMessages()){
			System.out.println(err.render());
		}
	}

	@Test
	public void test_MissingSTArg(){
		STGValidator stgv = STGValidator.create();
		Map<String, String[]> chunks = new HashMap<>();
		chunks.put("test", new String[]{"arg1", "arg2"});
		STGroupFile stg = new STGroupFile("de/vandermeer/skb/interfaces/validators/missing-arg.stg");

		assertFalse(stgv.validate(stg, chunks));
		assertEquals(2, stgv.getErrorSet().size());
		for(DoesRender err : stgv.getErrorSet().getMessages()){
			System.out.println(err.render());
		}
	}

	@Test
	public void test_GoodSTG(){
		STGValidator stgv = STGValidator.create();
		Map<String, String[]> chunks = new HashMap<>();
		chunks.put("test", new String[]{"arg1", "arg2"});
		STGroupFile stg = new STGroupFile("de/vandermeer/skb/interfaces/validators/good.stg");

		assertTrue(stgv.validate(stg, chunks));
		assertEquals(0, stgv.getErrorSet().size());
	}
}
