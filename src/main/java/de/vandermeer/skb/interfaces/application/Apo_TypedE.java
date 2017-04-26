package de.vandermeer.skb.interfaces.application;

import org.stringtemplate.v4.ST;

public interface Apo_TypedE<T> extends ApoBaseE, ApoBaseTyped<T> {

	/**
	 * Returns the value of the option.
	 * First the environment value is tested and if not null it is returned.
	 * Last the default value is returned.
	 * @return application value, null if none found
	 */
	default T getValue(){
		return (this.getEnvironmentValue()!=null)?this.getEnvironmentValue():this.getDefaultValue();
	}

	/**
	 * Returns the environment value of the option if any set.
	 * @return environment value, null if none set
	 */
	T getEnvironmentValue();

	/**
	 * Tests if the option is set.
	 * A typed option is set if it has a value that is not `null`.
	 * @return true if set, false otherwise
	 */
	@Override
	default boolean isSet(){
		return this.getValue()!=null;
	}

	@Override
	default ST getHelp(){
		return this.getHelpEnv();
	}

	/**
	 * Returns an ST with a help information for the option.
	 * @return ST with help information, must not be null
	 */
	default ST getHelpEnv(){
		ST st = ApoBaseE.super.getHelpEnv();
		st.add("defaultValue", this.getDefaultValue());
		return st;
	}
}
