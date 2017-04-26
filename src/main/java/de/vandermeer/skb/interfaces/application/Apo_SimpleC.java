package de.vandermeer.skb.interfaces.application;

public interface Apo_SimpleC extends ApoBaseC {

	/**
	 * Tests if the option is set.
	 * A simple option with CLI is set if it is present in a parsed command line.
	 * @return true if set, false otherwise
	 */
	@Override
	default boolean isSet(){
		return this.inCli();
	}

}
