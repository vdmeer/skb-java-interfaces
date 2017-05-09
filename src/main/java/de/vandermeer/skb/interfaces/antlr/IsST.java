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

package de.vandermeer.skb.interfaces.antlr;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.stringtemplate.v4.ST;

import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.messagesets.errors.IsError;
import de.vandermeer.skb.interfaces.messagesets.errors.Templates_ST;
import de.vandermeer.skb.interfaces.render.DoesRender;
import de.vandermeer.skb.interfaces.render.DoesRenderToWidth;

/**
 * Interface for objects that represent String Template (ST) objects
 * (for which one can check chunks etc).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface IsST extends DoesRender, DoesRenderToWidth, CategoryIs {

	/**
	 * Returns the ST object.
	 * @return ST object
	 */
	ST getST();

	/**
	 * The name of the ST Group in which the template was defined.
	 * @return STGroup name, null if none set
	 */
	default String getGroupName(){
		return null;
	}

	@Override
	default String render() {
		if(this.getST()==null){
			return "";
		}
		return this.getST().render();
	};

	@Override
	default String render(int width) {
		if(this.getST()==null || width<1){
			return "";
		}
		return this.getST().render(width);
	}

	/**
	 * Returns the arguments expected to be defined in the ST object.
	 * @return expected arguments as set of argument names
	 */
	String[] getExpectedArguments();

	/**
	 * Validates the ST for expected arguments.
	 * @return a set of error messages, empty if no errors found, null if no expected arguments where set or no ST was set
	 */
	default Set<IsError> validate(){
		if(this.getExpectedArguments()==null){
			return null;
		}
		if(this.getST()==null){
			return null;
		}

		Set<IsError> ret = new LinkedHashSet<>();
		Map<?,?> formalArgs = this.getST().impl.formalArguments;
		if(formalArgs==null){
			for(String s : this.getExpectedArguments()){
				ret.add(Templates_ST.MISSING_EXPECTED_ARGUMENT_STG.getError("app", this.getGroupName(), this.getST().getName(), s));
			}
		}
		else{
			for(String s : this.getExpectedArguments()){
				if(!formalArgs.containsKey(s)){
					ret.add(Templates_ST.MISSING_EXPECTED_ARGUMENT_STG.getError("app", this.getGroupName(), this.getST().getName(), s));
				}
			}
		}
		return ret;
	}

	/**
	 * Creates a new IsST object.
	 * @param st the contained ST object
	 * @return new IsST object
	 * @throws NullPointerException if `st` was null
	 */
	static IsST create(ST st){
		return IsST.create(st, null, null);
	}

	/**
	 * Creates a new IsST object.
	 * @param st the contained ST object
	 * @param expectedArguments set of expected arguments for the ST, null if none required
	 * @param groupName the name of the ST group of the template, null if none set or known
	 * @return new IsST object
	 * @throws NullPointerException if `st` was null or the expected arguments was not null and contained null elements
	 */
	static IsST create(final ST st, final String[] expectedArguments, String groupName){
		Validate.notNull(st);
		if(expectedArguments!=null){
			Validate.noNullElements(expectedArguments);
		}

		return new IsST() {
			@Override
			public ST getST() {
				return st;
			}

			@Override
			public String[] getExpectedArguments() {
				return expectedArguments;
			}

			@Override
			public String getGroupName(){
				return groupName;
			}
		};
	}
}
