package de.vandermeer.skb.interfaces.messages.errors;

import org.slf4j.helpers.FormattingTuple;

import de.vandermeer.skb.interfaces.render.DoesRender;

public interface IsError extends DoesRender {

	/**
	 * Returns the category of the error.
	 * @return error category
	 */
	IsErrorCategory getCategory();

	/**
	 * Returns the error code.
	 * @return error code
	 */
	int getErrorCode();

	/**
	 * Returns the error message as a formatting tuple, not yet rendered.
	 * @return error message as a formatting tuple, must not be null
	 */
	FormattingTuple getErrorMessage();

	@Override
	default String render(){
		return this.getErrorMessage().getMessage();
	}

}