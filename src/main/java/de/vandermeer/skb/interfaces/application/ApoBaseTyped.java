package de.vandermeer.skb.interfaces.application;

public interface ApoBaseTyped<T> extends ApoBase {

//	/**
//	 * Converts a value of some type to the type the option is using.
//	 * @param value the value to be converted
//	 * @return the value converted to the type of the option, null if not possible
//	 * @throws IllegalStateException if the argument was blank (string) or otherwise problematic
//	 */
//	T convertValue(Object value) throws IllegalStateException;

	/**
	 * Returns the default value of the option.
	 * @return option default value, blank if not set
	 */
	T getDefaultValue();

	/**
	 * Returns the value of the option.
	 * In the base interface this is the default value.
	 * @return application value, null if none found
	 */
	default T getValue(){
		return this.getDefaultValue();
	}

	/**
	 * Tests if the option is set.
	 * A typed option is set if it has a default value.
	 * @return true if set, false otherwise
	 */
	@Override
	default boolean isSet(){
		return this.getValue()!=null;
	}

}
