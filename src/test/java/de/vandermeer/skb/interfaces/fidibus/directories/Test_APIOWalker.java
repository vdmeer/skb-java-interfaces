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

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.junit.Test;

import de.vandermeer.skb.interfaces.fidibus.FileSystemFilters;
import de.vandermeer.skb.interfaces.fidibus.files.FileSource;

/**
 * Tests for {@link APIOWalker}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public class Test_APIOWalker {

	@Test
	public void testWalkJavaSource(){
		APIOWalker walker = APIOWalker.create(
				"src/main/java/de/vandermeer/skb/interfaces/fidibus",
				DirectoryFileFilter.INSTANCE,
				FileSystemFilters.WILDECARD("java")
		);

		assertFalse(walker.hasErrors());

		FileSource[] result = walker.read();
		assertFalse(walker.hasErrors());

		int checkSize = 36;//TODO update this if java files change in fidibus source folder

		assertTrue(result!=null);
		assertEquals(checkSize, result.length);
	}

}
