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

package de.vandermeer.skb.interfaces.fidibus.directories;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.interfaces.fidibus.files.FileLocation;
import de.vandermeer.skb.interfaces.fidibus.files.FileSource;
import de.vandermeer.skb.interfaces.messages.sets.HasInfoSet;
import de.vandermeer.skb.interfaces.messages.sets.HasWarningSet;
import de.vandermeer.skb.interfaces.messages.sets.IsErrorSet;
import de.vandermeer.skb.interfaces.messages.sets.IsInfoSet;
import de.vandermeer.skb.interfaces.messages.sets.IsWarningSet;
import de.vandermeer.skb.interfaces.render.DoesRender;

/**
 * Simple directory scanner.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface SimpleScanner extends DirectoryLoader, HasInfoSet, HasWarningSet {

	/**
	 * Creates a new simple scanner.
	 * @param directoryName the directory to scan, must not be null
	 * @return new scanner
	 */
	static SimpleScanner create(final String directoryName){
		Validate.notBlank(directoryName);

		SimpleScanner ret = new SimpleScanner() {
			protected final DirectorySource source = DirectorySource.create(directoryName, new DirectoryLocation[]{DirectoryLocation.FILESYSTEM});
			protected final IsErrorSet errors = IsErrorSet.create();
			protected final IsInfoSet infos = IsInfoSet.create();
			protected final IsWarningSet warnings = IsWarningSet.create();

			@Override
			public String getDescription() {
				return "Recursiely scans directories and collects file infos";
			}

			@Override
			public String getDisplayName() {
				return "Simple Directory Scanner";
			}

			@Override
			public String getName() {
				return "simple-scanner";
			}

			@Override
			public IsErrorSet getErrorSet() {
				return this.errors;
			}

			@Override
			public DirectorySource getSource() {
				return source;
			}

			@Override
			public IsInfoSet getInfoSet() {
				return this.infos;
			}

			@Override
			public IsWarningSet getWarningSet() {
				return this.warnings;
			}
		};
		ret.validateSource();
		return ret;
	}

	/**
	 * Recursively scans directories and collects files, warnings and statistics.
	 * @param fDir file object representing a directory
	 * @param list collected file sources
	 * @param scDir number of scanned directories
	 * @param scDirUnread number of unreadable directories
	 * @param scFiles number of found files
	 * @param scFilesUnread number of unreadable files
	 */
	default void doScan(File fDir, List<FileSource> list, int scDir, int scDirUnread, int scFiles, int scFilesUnread){
		if(fDir!=null && fDir.exists() && !fDir.isHidden()){
			File[] fDirarr = fDir.listFiles();
			if(fDirarr!=null){
				for(final File entry : fDirarr) {
					if(entry.isHidden()){
						continue;
					}
					if(!entry.isDirectory()) {
						scFiles++;
						if(entry.canRead()){
							list.add(FileSource.create(entry.getAbsolutePath(), new FileLocation[]{FileLocation.FILESYSTEM}));
						}
						else{
							scFilesUnread++;
							this.getWarningSet().add("found file <{}> but cannot read, ignore", " + entry.getAbsolutePath() + ");
						}
					}
					else if(entry.isDirectory()){
						scDir++;
						if(entry.canRead()){
							this.doScan(entry, list, scDir, scDirUnread, scFiles, scFilesUnread);
						}
						else{
							scDirUnread++;
							this.getWarningSet().add("found directory <{}> but cannot read, ignore", " + entry.getAbsolutePath() + ");
						}
					}
				}
			}
		}
	}

	@Override
	default FileSource[] read(){
		if(!this.getErrorSet().hasMessages()){
			int scDir = 0;			//Number of directories scanned
			int scDirUnread = 0;	//Number of unreadable directories scanned
			int scFiles = 0;		//Number of files found
			int scFilesUnread = 0;	//Number of unreadable files found

			this.getInfoSet().clearMessages();
			this.getWarningSet().clearMessages();

			File file = this.getSource().getSource().toFile();
			List<FileSource> ret = new ArrayList<>();
			this.doScan(file, ret, scDir, scDirUnread, scFiles, scFilesUnread);

			this.getInfoSet().add("scanned directories:    {}", scDir);
			this.getInfoSet().add("unreadable directories: {}", scDirUnread);
			this.getInfoSet().add("found files:      {}", scFiles);
			this.getInfoSet().add("unreadable files: {}", scFilesUnread);

			return ret.toArray(new FileSource[0]);
		}
		return null;
	}

	/**
	 * Returns a string with infos and warnings from a scan.
	 * @return info string
	 */
	default String toDebug(){
		StrBuilder ret = new StrBuilder(100);

		ret.append("infos: ").append(this.getInfoSet().size()).appendNewLine();
		if(this.getInfoSet().size()>0){
			for(DoesRender msg : this.getInfoSet().getMessages()){
				ret.append("  - ").append(msg.render()).appendNewLine();
			}
		}
		ret.appendNewLine();

		ret.append("warnings: ").append(this.getWarningSet().size()).appendNewLine();
		if(this.getWarningSet().size()>0){
			for(DoesRender msg : this.getWarningSet().getMessages()){
				ret.append("  - ").append(msg.render()).appendNewLine();
			}
		}
		ret.appendNewLine();

		ret.append("errors: ").append(this.getErrorSet().size()).appendNewLine();
		if(this.getErrorSet().size()>0){
			for(DoesRender msg : this.getErrorSet().getMessages()){
				ret.append("  - ").append(msg.render()).appendNewLine();
			}
		}
		ret.appendNewLine();

		return ret.build();
	}
}
