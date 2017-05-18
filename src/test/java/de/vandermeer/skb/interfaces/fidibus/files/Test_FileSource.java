/* Copyright 2014 Sven van der Meer <vdmeer.sven@mykolab.com>
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

package de.vandermeer.skb.interfaces.fidibus.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Tests for {@link FileSource}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.7
 */
public class Test_FileSource {

	@Test
	public void test_FromFile(){
		FileSource source = FileSource.fromFilesystem("src/test/resources/de/vandermeer/skb/interfaces/fidibus/files/test.properties");
		source.validateSource();
		assertTrue(source.isValid());
	}

	@Test
	public void test_FromResource(){
		FileSource source = FileSource.fromResource("de/vandermeer/skb/interfaces/fidibus/files/test.properties");
		source.validateSource();
		assertTrue(source.isValid());
	}

	@Test
	public void test_FnExtension(){
		FileSource source = FileSource.fromResource("de/vandermeer/skb/interfaces/fidibus/files/test.properties");
		source.validateSource();
		assertTrue(source.isValid());

		assertEquals("properties", source.fnExtension());
	}

	@Test
	public void test_FnBasename(){
		FileSource source = FileSource.fromResource("de/vandermeer/skb/interfaces/fidibus/files/test.properties");
		source.validateSource();
		assertTrue(source.isValid());
		assertEquals("test", source.fnBasename());

		source = FileSource.fromFilesystem("src/test/resources/de/vandermeer/skb/interfaces/fidibus/files/noext");
		source.validateSource();
		assertTrue(source.isValid());
		assertEquals("noext", source.fnBasename());
	}

	@Test
	public void test_FnPath(){
		FileSource source = FileSource.fromResource("de/vandermeer/skb/interfaces/fidibus/files/test.properties");
		source.validateSource();
		assertTrue(source.isValid());

		assertEquals(StringUtils.replace("de/vandermeer/skb/interfaces/fidibus/files", "/", File.separator), source.fnPath());
	}

	@Test
	public void test_FnAbsolutePath(){
		FileSource source = FileSource.fromResource("de/vandermeer/skb/interfaces/fidibus/files/test.properties");
		source.validateSource();
		assertTrue(source.isValid());
	}

	@Test
	public void test_FnName(){
		FileSource source = FileSource.fromResource("de/vandermeer/skb/interfaces/fidibus/files/test.properties");
		source.validateSource();
		assertTrue(source.isValid());

		assertEquals("test.properties", source.fnName());
	}
}
