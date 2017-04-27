package de.vandermeer.skb.interfaces.application;

import org.stringtemplate.v4.ST;

public interface Apo_TypedCE<T> extends Apo_TypedC<T>, Apo_TypedE<T> {

	@Override
	default ST getHelp(){
		ST st = Apo_TypedC.super.getHelpCli();
		st.add("envKey", this.getEnvironmentKey());

		return st;
	}

	/**
	 * Returns the value of the option.
	 * First, the CLI value is tested and if not null it is returned.
	 * Second, the environment value is tested and if not it is returned.
	 * Last the default value is returned.
	 * @return application value, null if none found
	 */
	default T getValue(){
		if(this.getCliValue()!=null){
			return this.getCliValue();
		}
		if(this.getEnvironmentValue()!=null){
			return this.getEnvironmentValue();
		}
		return this.getDefaultValue();
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

	@Override
	default void validate() throws IllegalStateException {
		Apo_TypedC.super.validate();
		Apo_TypedE.super.validate();
	}
}
