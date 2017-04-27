package de.vandermeer.skb.interfaces.application;

import java.util.Properties;

import org.stringtemplate.v4.ST;

public interface Apo_TypedP<T> extends ApoBaseP, ApoBaseTyped<T> {

	@Override
	default ST getHelp(){
		return this.getHelpProperty();
	}

	/**
	 * Returns an ST with a help information for the option.
	 * @return ST with help information, must not be null
	 */
	default ST getHelpProperty(){
		ST st = ApoBaseP.super.getHelpProperty();
		st.add("defaultValue", this.getDefaultValue());
		return st;
	}

	/**
	 * Returns the property value of the option if any set.
	 * @return property value, null if none set
	 */
	T getPropertyValue();

	/**
	 * Returns the value of the option.
	 * First the property value is tested and if not null it is returned.
	 * Last the default value is returned.
	 * @return application value, null if none found
	 */
	default T getValue(){
		return (this.getPropertyValue()!=null)?this.getPropertyValue():this.getDefaultValue();
	}

	/**
	 * Tests if the option is set.
	 * A typed option is set if it has a value that is not `null`.
	 * @return true if set, false otherwise
	 */
	@Override
	default boolean isSet(){
		return this.getValue()!=null;
	}

	/**
	 * Sets the property value of the option.
	 * @param properties set of properties to get the value from
	 * @throws IllegalStateException if the argument was blank (string) or otherwise problematic
	 */
	void setPropertyValue(Properties properties) throws IllegalStateException;

}
