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

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.StrBuilder;
import org.stringtemplate.v4.ST;

import de.vandermeer.skb.interfaces.render.DoesRender;
import de.vandermeer.skb.interfaces.render.DoesRenderToWidth;
import de.vandermeer.skb.interfaces.render.HasText;
import de.vandermeer.skb.interfaces.render.HasTextCluster;
import de.vandermeer.skb.interfaces.render.RendersToCluster;
import de.vandermeer.skb.interfaces.render.RendersToClusterWidth;

/**
 * A null-safe transformer that takes an object and tries return an {@link StrBuilder} using various strategies.
 * 
 * The object is processed as follows:
 * 
 * - test object for being null, throw null pointer exception if that's the case
 * - test if object implements {@link HasText}, take the text
 * - test if object implements {@link HasTextCluster}, take the cluster and add each element to the text
 * - test if object is an {@link ST}, render and take rendered text
 * - test if object implements {@link DoesRender}, take the rendered text
 * - test if object implements {@link DoesRenderToWidth}, take the rendered text
 * - test if object implements {@link RendersToCluster}, take the rendered cluster and add as text
 * - test if object implements {@link RendersToClusterWidth}, take the rendered cluster and add as text
 * - test if object is an {@link Iterator}, iterate and call this method for each member
 * - test if object is an {@link Iterable}, iterate and call this method for each member
 * - test if object is an array of something, iterate the array and call this method for each member
 * - use the object as is (which will take a string if it is a string, use the `toString()` method otherwise).
 * 
 * Null objects in clusters are silently ignored.
 * Blank strings are processed like any other string (they do not impact the text anyway).
 * 
 * The method is recursive for iterators, iterables, and arrays.
 * Care needs to be taken that the provided clusters do not lead to endless loops.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public interface Object_To_StrBuilder extends IsTransformer<Object, StrBuilder> {

	/**
	 * Transforms an object to a string builder.
	 * @param obj input object
	 * @return string builder
	 */
	@Override
	default StrBuilder transform(Object obj) {
		StrBuilder ret = new StrBuilder();
		return this.transform(obj, ret);
	}

	/**
	 * Transforms an object to a string builder using the given builder.
	 * @param obj input object
	 * @param sb a given builder, new builder will be created if null
	 * @return string builder
	 */
	default StrBuilder transform(Object obj, StrBuilder sb) {
		Validate.notNull(obj);
		sb = (sb==null)?new StrBuilder():sb;

		if(obj instanceof HasText){
			sb.appendSeparator(' ').append(((HasText)obj).getText());
		}
		else if(obj instanceof HasTextCluster){
			Collection<String> collection = ((HasTextCluster)obj).getTextAsCollection();
			if(collection!=null){
				for(String s : collection){
					sb.appendSeparator(' ').append(s);
				}
			}
		}
		else if (obj instanceof ST){
			sb.appendSeparator(' ').append(((ST)obj).render());
		}
		else if(obj instanceof DoesRender){
			sb.appendSeparator(' ').append(((DoesRender)obj).render());
		}
		else if(obj instanceof DoesRenderToWidth){
			sb.appendSeparator(' ').append(((DoesRenderToWidth)obj).render(80));
		}
		else if(obj instanceof RendersToCluster){
			Collection<String> collection = ((RendersToCluster)obj).renderAsCollection();
			if(collection!=null){
				for(String s : collection){
					sb.appendSeparator(' ').append(s);
				}
			}
		}
		else if(obj instanceof RendersToClusterWidth){
			Collection<String> collection = ((RendersToClusterWidth)obj).renderAsCollection(80);
			if(collection!=null){
				for(String s : collection){
					sb.appendSeparator(' ').append(s);
				}
			}
		}
		else if(obj instanceof Iterator<?>){
			Iterator<?> it = (Iterator<?>)obj;
			while(it.hasNext()){
				Object o = it.next();
				if(o!=null){
					this.transform(o, sb);
				}
			}
		}
		else if(obj instanceof Iterable<?>){
			for(Object o : (Iterable<?>)obj){
				if(o!=null){
					this.transform(o, sb);
				}
			}
		}
		else if(obj.getClass().isInstance(new Object[]{})){
			Object[] oa = (Object[])obj;
			for(Object o : oa){
				if(o!=null){
					this.transform(o, sb);
				}
			}
		}
		else{
			// this will capture Strings and everything that uses toString (like other StrBuilders)
			sb.appendSeparator(' ').append(obj);
		}
		return sb;
	}

	/**
	 * Creates a transformer that takes an object and returns a string builder.
	 * @return new transformer
	 * @see Object_To_StrBuilder interface description for how the converter works
	 */
	static Object_To_StrBuilder create(){
		return new Object_To_StrBuilder() {};
	}

	/**
	 * Transforms an object to a string builder.
	 * @param obj input object
	 * @return string builder
	 * @see Object_To_StrBuilder interface description for how the convertion works
	 */
	static StrBuilder convert(Object obj){
		return Object_To_StrBuilder.create().transform(obj);
	}
}
