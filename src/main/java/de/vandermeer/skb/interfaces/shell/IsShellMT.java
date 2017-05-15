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

package de.vandermeer.skb.interfaces.shell;

import org.apache.commons.lang3.Validate;

/**
 * The base API of a shell with principal flow and multi-threaded methods.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.3
 */
public interface IsShellMT extends IsShell {

	@Override
	default void cleanup(){
		if(this.getNotifyObject()!=null){
			synchronized(this.getNotifyObject()){
				this.getNotifyObject().notify();
			}
		}
	}

	/**
	 * Stops the shell thread.
	 */
	default void stop(){
		if(this.getCurrentThread()!=null){
			this.setNotifyObject(null);
			synchronized(this.getCurrentThread()){
				this.getCurrentThread().interrupt();
			}
			this.setCurrentThread(null);
		}
		this.setIsRunning(false);
	}

	/**
	 * Starts the shell as a new thread.
	 * @param notify an object to notify if the shell is terminated
	 * @return the created thread for the shell
	 */
	default Thread start(Object notify){
		Validate.notNull(notify);

		if(this.getCurrentThread()==null){
			this.setNotifyObject(notify);
			this.setCurrentThread(new Thread(){
				@Override
				public void run(){
					runShell();
				}
			});
			this.getCurrentThread().start();
			return this.getCurrentThread();
		}
		return null;
	}

	/**
	 * Sets the current shell thread, automatically done when using {@link #start(Object)}.
	 * @param thread new thread, null when terminating the shell
	 */
	void setCurrentThread(Thread thread);

	/**
	 * Returns the current shell thread.
	 * @return current shell thread, null if none started
	 */
	Thread getCurrentThread();

	/**
	 * Sets the notify object for the shell thread.
	 * @return notify object, null if none set (i.e. no thread active)
	 */
	Object getNotifyObject();

	/**
	 * Sets the notify object, automatically done when using {@link #start(Object)}.
	 * @param obj the new notify object, null if no active thread
	 */
	void setNotifyObject(Object obj);

}
