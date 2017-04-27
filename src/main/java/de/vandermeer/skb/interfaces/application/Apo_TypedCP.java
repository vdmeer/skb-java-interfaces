package de.vandermeer.skb.interfaces.application;

import org.stringtemplate.v4.ST;

public interface Apo_TypedCP<T> extends Apo_TypedC<T>, Apo_TypedP<T> {

	@Override
	default ST getHelp(){
		ST st = Apo_TypedC.super.getHelpCli();
		st.add("propertyKey", this.getPropertyKey());

		return st;
	}

	/**
	 * Returns the value of the option.
	 * First, the CLI value is tested and if not null it is returned.
	 * Second, the property value is tested and if not it is returned.
	 * Last the default value is returned.
	 * @return application value, null if none found
	 */
	default T getValue(){
		if(this.getCliValue()!=null){
			return this.getCliValue();
		}
		if(this.getPropertyValue()!=null){
			return this.getPropertyValue();
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
		Apo_TypedP.super.validate();
	}
}
