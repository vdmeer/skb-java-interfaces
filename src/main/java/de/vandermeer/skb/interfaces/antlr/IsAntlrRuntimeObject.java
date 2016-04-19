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

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.Validate;

import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.render.HasText;

/**
 * Interface for objects that represent ANTLR runtime objects
 * (which have source, line, column, and other characteristics).
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public interface IsAntlrRuntimeObject extends CategoryIs, HasText {

	/**
	 * Returns the ANTLR runtime object.
	 * @return ANTLR runtime object
	 */
	Object getAntlrObject();

	/**
	 * Returns the column (character position in line) for the ANTLR object.
	 * @return column, -1 if none found or the object wasn't an ANTLR object with a column
	 */
	default int getColumn(){
		Integer ret = -1;
		Object o = this.getAntlrObject();
		if(o==null){
			return ret;
		}
		else if(o instanceof RecognitionException){
			try{
				ret = ((RecognitionException)o).getOffendingToken().getCharPositionInLine();
			}
			catch(Exception ignore){}
		}
		else if(o instanceof Token){
			ret = ((Token)o).getCharPositionInLine();
		}
		else if(o instanceof ParserRuleContext){
			try{
				ret = ((ParserRuleContext)o).getStart().getCharPositionInLine();
			}
			catch(Exception ignore){}
		}
		else if(o instanceof TerminalNode){
			try{
				ret = ((TerminalNode)o).getSymbol().getCharPositionInLine();
			}
			catch(Exception ignore){}
		}
		return ret;
	}

	/**
	 * Returns the line for the ANTLR object.
	 * @return line, -1 if none found or the object wasn't an ANTLR object with a line
	 */
	default int getLine(){
		Integer ret = -1;
		Object o = this.getAntlrObject();
		if(o==null){
			return ret;
		}
		else if(o instanceof RecognitionException){
			try{
				ret = ((RecognitionException)o).getOffendingToken().getLine();
			}
			catch(Exception ignore){}
		}
		else if(o instanceof Token){
			ret = ((Token)o).getLine();
		}
		else if(o instanceof ParserRuleContext){
			try{
				ret = ((ParserRuleContext)o).getStart().getLine();
			}
			catch(Exception ignore){}
		}
		else if(o instanceof TerminalNode){
			try{
				ret = ((TerminalNode)o).getSymbol().getLine();
			}
			catch(Exception ignore){}
		}
		return ret;
	}

	/**
	 * Returns the source file of the ANTLR object.
	 * @return source file
	 */
	default String getFilename(){
		String ret = null;
		Object o = this.getAntlrObject();
		if(o==null){
			return ret;
		}
		else if(o instanceof RecognitionException){
			try{
				ret = ((RecognitionException)o).getOffendingToken().getTokenSource().getSourceName();
			}
			catch(Exception ignore){}
			ret = (ret==null)?"":ret;
		}
		else if(o instanceof Token){
			try{
				ret = ((Token)o).getTokenSource().getSourceName();
			}
			catch(Exception ignore){}
			ret = (ret==null)?"":ret;
		}
		else if(o instanceof ParserRuleContext){
			ret = ((ParserRuleContext)o).getStart().getTokenSource().getSourceName();
			ret = (ret==null)?"":ret;
		}
		else if(o instanceof TerminalNode){
			try{
				ret = ((TerminalNode)o).getSymbol().getTokenSource().getSourceName();
			}
			catch(Exception ignore){}
			ret = (ret==null)?"":ret;
		}
		return ret;
	}

	@Override
	default String getText(){
		String ret = null;
		Object o = this.getAntlrObject();

		if(o==null){
			return ret;
		}
		else if(o instanceof RecognitionException){
			try{
				ret = ((RecognitionException)o).toString();
			}
			catch(Exception ignore){}
			ret = (ret==null)?"":ret;
		}
		else if(o instanceof Token){
			try{
				ret = ((Token)o).getText();
			}
			catch(Exception ignore){}
			ret = (ret==null)?"":ret;
		}
		else if(o instanceof ParserRuleContext){
			ret = ((ParserRuleContext)o).getText();
			ret = (ret==null)?"":ret;
		}
		else if(o instanceof ParseTree){
			try{
				ret = ((ParseTree)o).getText();
			}
			catch(Exception ignore){}
			ret = (ret==null)?"":ret;
		}
		else if(o instanceof TerminalNode){
			try{
				ret = ((TerminalNode)o).getText();
			}
			catch(Exception ignore){}
			ret = (ret==null)?"":ret;
		}
		return ret;
	}

	/**
	 * Creates a new object from an object.
	 * @param obj the object for creation
	 * @return new object
	 * @throws NullPointerException if the `obj` was null
	 * @throws IllegalArgumentException if `obj` was not an accepted ANTRL runtime object
	 */
	static IsAntlrRuntimeObject create(final Object obj){
		Validate.notNull(obj);
		if(obj instanceof RecognitionException){
			return new IsAntlrRuntimeObject() {
				@Override
				public RecognitionException getAntlrObject() {
					return (RecognitionException)obj;
				}
			};
		}
		if(obj instanceof Token){
			return new IsAntlrRuntimeObject() {
				@Override
				public Token getAntlrObject() {
					return (Token)obj;
				}
			};
		}
		if(obj instanceof ParserRuleContext){
			return new IsAntlrRuntimeObject() {
				@Override
				public ParserRuleContext getAntlrObject() {
					return (ParserRuleContext)obj;
				}
			};
		}
		if(obj instanceof ParseTree){
			return new IsAntlrRuntimeObject() {
				@Override
				public ParseTree getAntlrObject() {
					return (ParseTree)obj;
				}
			};
		}
		if(obj instanceof TerminalNode){
			return new IsAntlrRuntimeObject() {
				@Override
				public TerminalNode getAntlrObject() {
					return (TerminalNode)obj;
				}
			};
		}

		throw new IllegalArgumentException("expected ANTLR runtime object, found <" + obj.getClass().getSimpleName() +">");
	}
}
