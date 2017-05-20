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

package de.vandermeer.skb.interfaces.fidibus.directories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.vandermeer.skb.interfaces.fidibus.files.FileSource;
import de.vandermeer.skb.interfaces.render.DoesRender;

/**
 * Tests for {@link SimpleScanner}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.7
 */
public class Test_SimpleScanner {

	@Test
	public void testScanJavaSource(){
		SimpleScanner scanner = SimpleScanner.create("src/main/java/de/vandermeer/skb/interfaces/fidibus");
		assertFalse(scanner.hasErrors());

		FileSource[] files = scanner.read();
		assertFalse(scanner.hasErrors());

		int checkSize = 36;//TODO update this if java files in src/main are have been removed or added

		assertTrue(files!=null);
		assertEquals(checkSize, files.length);
	}

	@Test
	public void testScan(){
		SimpleScanner scanner = SimpleScanner.create("test/resources/for-scanner-tests");
		assertEquals(4, scanner.getErrorSet().size());
		for(DoesRender s : scanner.getErrorSet().getMessages()){
			System.err.println(s.render());
		}

		scanner = SimpleScanner.create("src/test/resources/for-scanner-tests");
		scanner.read();
		assertEquals(0, scanner.getErrorSet().size());

		System.out.println(scanner.toDebug());

//		scanner = new SimpleDirectoryScanner(new DirectorySource("src/test/resources/for-scanner-tests"));
//		scanner.load();
//		assertEquals(0, scanner.getLoadErrors().getMessages().size());
//		assertEquals(0, scanner.lastWarnings().size());
//		assertEquals(4, scanner.lastInfos().size());
	}

}
