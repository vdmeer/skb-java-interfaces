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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import de.vandermeer.skb.interfaces.transformers.Object_To_Target;
import de.vandermeer.skb.interfaces.transformers.Transformer;

/**
 * Test {@link Object_To_Target}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.1
 */
public class Test_Object_To_Target {

	private String nullValue = "<null>";

	private String falseValue = "<false>";

	@Test public void testConvert(){
		//String = ok, <null> (can't be <false>)
		assertEquals("test string", Object_To_Target.convert("test string", String.class, nullValue, falseValue));
		assertEquals(nullValue, Object_To_Target.convert(null, String.class, nullValue, falseValue));

		//try a set with null, then with a string, then with an object
		Set<Object>set = new HashSet<Object>();
		assertEquals(nullValue, Object_To_Target.convert(set, String.class, nullValue, falseValue));
		set.add(null);
		assertEquals(nullValue, Object_To_Target.convert(set, String.class, nullValue, falseValue));
		set.add("test string2");
		assertEquals("test string2", Object_To_Target.convert(set, String.class, nullValue, falseValue));
		set.clear();
		set.add(0);//no string should result in false
		assertEquals(falseValue, Object_To_Target.convert(set, String.class, nullValue, falseValue));
	}

	@Test public void testConvertObjectarr(){
		//test for nullValue
		assertNull(Object_To_Target.convert(null, Object[].class, null, null));
		assertNotNull(Object_To_Target.convert(null, Object[].class, new Object[]{}, null));
		assertTrue(Object_To_Target.convert(null, Object[].class, new Object[]{}, null).length==0);

		//test for falseValue
		assertNull(Object_To_Target.convert("", Object[].class, new Object[]{}, null));

		//test for valid object[]
		Object[] arr=new Object[]{null, "two", new Integer(100), '/'};
		assertEquals(arr.hashCode(), Object_To_Target.convert(arr, Object[].class, null, null).hashCode());
	}

	@Test public void testConvertBoolean(){
		//test with nullValue returns
		assertNull(Object_To_Target.convert(null, Boolean.class, null, null));
		assertFalse(Object_To_Target.convert(null, Boolean.class, false, null));
		assertTrue(Object_To_Target.convert(null, Boolean.class, true, null));

		//falseValue returns
		assertNull(Object_To_Target.convert("", Boolean.class, false, null));
		assertFalse(Object_To_Target.convert("", Boolean.class, true, false));
		assertTrue(Object_To_Target.convert("", Boolean.class, false, true));

		//some good native values
		assertTrue(Object_To_Target.convert(Boolean.TRUE, Boolean.class, null, null));
		assertFalse(Object_To_Target.convert(Boolean.FALSE, Boolean.class, null, null));

		Set<Object>set = new HashSet<Object>();
		set.add(null);
		assertFalse(Object_To_Target.convert(set, Boolean.class, false, true));
		set.add(Boolean.TRUE);
		assertEquals(Boolean.TRUE, Object_To_Target.convert(set, Boolean.class, false, false));

		//some values that come from String (it uses BooleanUtils.toBooleanObject)
		assertTrue(Object_To_Target.convert("true", Boolean.class, false, false));
		assertTrue(Object_To_Target.convert("True", Boolean.class, false, false));
		assertTrue(Object_To_Target.convert("on", Boolean.class, false, false));
		assertTrue(Object_To_Target.convert("ON", Boolean.class, false, false));

		assertFalse(Object_To_Target.convert("false", Boolean.class, true, true));
		assertFalse(Object_To_Target.convert("False", Boolean.class, true, true));
		assertFalse(Object_To_Target.convert("off", Boolean.class, true, true));
		assertFalse(Object_To_Target.convert("Off", Boolean.class, true, true));

		assertFalse(Object_To_Target.convert("bla", Boolean.class, true, false));
		assertFalse(Object_To_Target.convert("foo", Boolean.class, true, false));
	}

	@Test public void testConvertInteger(){
		//test with nullValue returns
		assertNull(Object_To_Target.convert(null, Integer.class, null, null));
		assertEquals(new Integer(-1), Object_To_Target.convert(null, Integer.class, -1, null));
		assertEquals(new Integer(0), Object_To_Target.convert(null, Integer.class, 0, null));

		//falseValue returns
		assertNull(Object_To_Target.convert("", Integer.class, 0, null));
		assertEquals(new Integer(0), Object_To_Target.convert("", Integer.class, -1, 0));
		assertEquals(new Integer(-1), Object_To_Target.convert("", Integer.class, 0, -1));

		//some values that come from String
		assertEquals(new Integer(1), Object_To_Target.convert("1", Integer.class, -1, -2));
		assertEquals(new Integer(10), Object_To_Target.convert("10", Integer.class, -1, -2));

		assertEquals(new Integer(-2), Object_To_Target.convert("xxx", Integer.class, -1, -2));
		assertEquals(new Integer(-2), Object_To_Target.convert("1x", Integer.class, -1, -2));
	}

	@Test public void testConvertDouble(){
		//test with nullValue returns
		assertNull(Object_To_Target.convert(null, Double.class, null, null));
		assertEquals(new Double(-1.0), Object_To_Target.convert(null, Double.class, -1.0, null));
		assertEquals(new Double(0.0), Object_To_Target.convert(null, Double.class, 0.0, null));

		//falseValue returns
		assertNull(Object_To_Target.convert("", Double.class, 0.0, null));
		assertEquals(new Double(0.0), Object_To_Target.convert("", Double.class, -1.0, 0.0));
		assertEquals(new Double(-1.0), Object_To_Target.convert("", Double.class, 0.0, -1.0));

//		//some values that come from String
		assertEquals(new Double(1.1), Object_To_Target.convert("1.1", Double.class, -1.0, -2.0));
		assertEquals(new Double(10.2), Object_To_Target.convert("10.2", Double.class, -1.0, -2.0));

		assertEquals(new Double(-2.0), Object_To_Target.convert("xxx", Double.class, -1.0, -2.0));
		assertEquals(new Double(-2.0), Object_To_Target.convert("1x", Double.class, -1.0, -2.0));
	}

	@Test public void testConvertLong(){
		//test with nullValue returns
		assertNull(Object_To_Target.convert(null, Long.class, null, null));
		assertEquals(new Long(-1), Object_To_Target.convert(null, Long.class, new Long(-1), null));
		assertEquals(new Long(0), Object_To_Target.convert(null, Long.class, new Long(0), null));

		//falseValue returns
		assertNull(Object_To_Target.convert("", Long.class, new Long(0), null));
		assertEquals(new Long(0), Object_To_Target.convert("", Long.class, new Long(-1), new Long(0)));
		assertEquals(new Long(-1), Object_To_Target.convert("", Long.class, new Long(0), new Long(-1)));

		//some values that come from String
		assertEquals(new Long(1), Object_To_Target.convert("1", Long.class, new Long(-1), new Long(-2)));
		assertEquals(new Long(10), Object_To_Target.convert("10", Long.class, new Long(-1), new Long(-2)));

		assertEquals(new Long(-2), Object_To_Target.convert("xxx", Long.class, new Long(-1), new Long(-2)));
		assertEquals(new Long(-2), Object_To_Target.convert("1x", Long.class, new Long(-1), new Long(-2)));
	}

	@Test public void test_Object2Target_General(){
		//most other tests are done in Transformations
		Transformer<Object, String> toStr = Object_To_Target.create(String.class, nullValue, falseValue, true);

		//String = ok, <null> (can't be <false>)
		assertEquals("test string", toStr.transform("test string"));
		assertEquals(nullValue, toStr.transform(null));

		//try a set with null, then with a string, then with an object
		Set<Object>set = new HashSet<Object>();
		assertEquals(nullValue, toStr.transform(set));
		set.add(null);
		assertEquals(nullValue, toStr.transform(set));
		set.add("test string2");
		assertEquals("test string2", toStr.transform(set));
		set.clear();
		assertEquals(nullValue, toStr.transform(set));

//		set.add(SetStrategy.HASH_SET);//TODO from refactoring unto utils
//		assertEquals(falseValue, toStr.transform(set));//TODO from refactoring unto utils
	}
}
