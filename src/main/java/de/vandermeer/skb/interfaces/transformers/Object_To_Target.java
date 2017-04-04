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

import org.apache.commons.lang3.ClassUtils;

/**
 * Converts an Object to a target type with several settings, for instance for null value and false value.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.1 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.1
 */
public interface Object_To_Target<T> extends IsTransformer<Object, T> {

	/**
	 * Returns the class for of the target type.
	 * @return target type class, should not be `null`
	 */
	Class<T> getClazzT();

	/**
	 * Returns the null value for the transformation, used if a null test succeeds
	 * @return null value, can be `null` (then `null` is the null value)
	 */
	T getNullValue();

	/**
	 * Returns the false value for the transformation, used in case no test succeeds.
	 * @return false value, can be `null` (then `null` is the false value)
	 */
	T getFalseValue();

	/**
	 * Returns a flag that say if, when the transform object is a collection, only the first non-null element should be used as return value.
	 * @return use collection first element only flag, defaults to `false`
	 */
	boolean getCollFirstFlag();

	/**
	 * Type safe transformation from Object to target class, with optional special processing for `Object[]` and `Collection`.
	 * The conversion is done in the following sequence:
	 * 
	 *     * If value is `null`, the `nullValue` will be returned.
	 *     * If the requested class is an object array ({@link #getClazzT()}==Object[]),
	 *       then the return is `value` (if value is an `Object[]`)
	 *       or `Collection.toArray()` (if value is a `Collection)`.
	 *       In all other cases the process proceeds
	 *     * If `collFist` is set to `true` and value is a `Collection`, the first value of this collection will be used for the further process
	 *     * Now another `null` test returning `nullValue` if value is `null`
	 *     * Next test if the {@link #getClazzT()} is an instance of `value.class`. If true, `value` will be returned
	 *     * Next try for some standard type conversions for `value`, namely
	 *       ** If {@link #getClazzT()} is an instance of `Boolean` and value is `true` or `on` (case insensitive), then return `Boolean(true)`
	 *       ** If {@link #getClazzT()} is an instance of `Boolean` and value is `false` or `off` (case insensitive), then return `Boolean(false)`
	 *       ** If {@link #getClazzT()} is an instance of `Integer` then try to return `Integer.valueOf(value.toString)`
	 *       ** If {@link #getClazzT()} is an instance of `Double` then try to return `Double.valueOf(value.toString)`
	 *       ** If {@link #getClazzT()} is an instance of `Long` then try to return `Long.valueOf(value.toString)`
	 *     * The last option is to return `valueFalse` to indicate that no test was successful
	 * 
	 * This method does suppress warnings for "`unchecked`" castings, because a casting from any concrete return type to `T` is unsafe.
	 * Because all actual castings follow an explicit type check, this suppression should have not negative impact (i.e. there are no {@link ClassCastException}).
	 * 
	 * @param obj input object for conversion
	 * @return a value of type `T` if a conversion was successful, `nullValue` if `null` was tested successfully, `falseValue` in all other cases
	 */
	@SuppressWarnings("unchecked")
	@Override
	default T transform(Object obj) {
		if(obj==null){
			return this.getNullValue();
		}

		//next check for Object[], because here we want collections unchanged
		if(this.getClazzT().isInstance(new Object[]{})){
			if(obj instanceof Object[]){
				return (T)obj;
			}
			else if(obj instanceof Collection){
				return (T)((Collection<?>)obj).toArray();
			}
		}

		//now, if collection use the first value
		if(this.getCollFirstFlag()==true && ClassUtils.isAssignable(obj.getClass(), Collection.class)){
			Collection_To_FirstElement<Object> tr = Collection_To_FirstElement.create();
			obj = tr.transform((Collection<Object>)obj);
		}

		//check value again, this maybe the one from the collection
		if(obj==null){
			return this.getNullValue();
		}

		//if value is T, return caste to T
		if(this.getClazzT().isInstance(obj)){
			return (T)obj;
		}

		if(this.getClazzT().isInstance(new Boolean(true))){
			Object ret = String_To_Boolean.create().transform(obj.toString());
			if(ret!=null){
				return (T) ret;
			}
		}
		if(this.getClazzT().isInstance(new Integer(0))){
			try{
				return (T)Integer.valueOf(obj.toString());
			}
			catch(Exception ignore){}
		}
		if(this.getClazzT().isInstance(new Double(0))){
			try{
				return (T)Double.valueOf(obj.toString());
			}
			catch(Exception ignore){}
		}
		if(this.getClazzT().isInstance(new Long(0))){
			try{
				return (T)Long.valueOf(obj.toString());
			}
			catch(Exception ignore){}
		}

		//no other option, return falseValue
		return this.getFalseValue();
	}

	/**
	 * Creates a transformer that takes an Object and returns a target type.
	 * @param <T> target type for the transformation
	 * @param clazz the requested type of the return value, required for initialization
	 * @param nullValue the value to be used if a null test succeeds
	 * @param falseValue the value to be used in case no test succeeds
	 * @param collFirst if set true, collections will be processed and the first element returned, no special collection processing otherwise
	 * @return new transformer that returns a value of type `T` if a conversion was successful, `nullValue` if `null` was tested successfully, `falseValue` in all other cases
	 */
	static <T> Object_To_Target<T> create(final Class<T> clazz, final T nullValue, final T falseValue, final boolean collFirst){
		return new Object_To_Target<T>() {
			@Override
			public Class<T> getClazzT() {
				return clazz;
			}

			@Override
			public T getNullValue() {
				return nullValue;
			}

			@Override
			public T getFalseValue() {
				return falseValue;
			}

			@Override
			public boolean getCollFirstFlag() {
				return collFirst;
			}};
	}

	/**
	 * Type safe casting or conversion from Object to target class, special processing for Object[] and Collections.
	 * This is a convenient method for {@link #convert(Object, Class, Object, Object, boolean)} with both return values set to null and
	 * the last argument set to true (i.e. special processing of collections).
	 * @see #convert(Object, Class, Object, Object, boolean)
	 * @param <T> type of the return object
	 * @param value the value that should be converted
	 * @param clazz the requested type of the return value, needed for initialization
	 * @return a value of type `T` if a conversion was successful, `nullValue` if `null` was tested successfully, `falseValue` in all other cases
	 */
	static <T> T convert(Object value, Class<T> clazz){
		return Object_To_Target.convert(value, clazz, null, null, true);
	}

	/**
	 * Type safe casting or conversion from Object to target class, special processing for Object[] and Collections.
	 * This is a convenient method for {@link #convert(Object, Class, Object, Object, boolean)} with the last 
	 * argument set to true (i.e. special processing of collections).
	 * @see #convert(Object, Class, Object, Object, boolean)
	 * @param <T> type of the return object
	 * @param value the value that should be converted
	 * @param clazz the requested type of the return value, needed for initialization
	 * @param nullValue the value to be used if a null test succeeds
	 * @param falseValue the value to be used in case no test succeeds
	 * @return a value of type `T` if a conversion was successful, `nullValue` if `null` was tested successfully, `falseValue` in all other cases
	 */
	static <T> T convert(Object value, Class<T> clazz, T nullValue, T falseValue){
		return Object_To_Target.convert(value, clazz, nullValue, falseValue, true);
	}

	/**
	 * Type safe casting or conversion from Object to target class, with optional special processing for Object[] and Collections.
	 * @see #create
	 * @param <T> type of the return object
	 * @param value the value that should be converted
	 * @param clazz the requested type of the return value, needed for initialization
	 * @param nullValue the value to be used if a null test succeeds
	 * @param falseValue the value to be used in case no test succeeds
	 * @param collFirst if set true, collections will be processed and the first element returned, no special collection processing otherwise
	 * @return a value of type `T` if a conversion was successful, `nullValue` if `null` was tested successfully, `falseValue` in all other cases
	 */
	static <T> T convert(Object value, Class<T> clazz, T nullValue, T falseValue, boolean collFirst){
		return Object_To_Target.create(clazz, nullValue, falseValue, collFirst).transform(value);
	}
}
