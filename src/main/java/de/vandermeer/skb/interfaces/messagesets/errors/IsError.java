package de.vandermeer.skb.interfaces.messagesets.errors;

import org.slf4j.helpers.FormattingTuple;

public interface IsError {

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

	/**
	 * Returns the error messages as a fully formated, rendered, string.
	 * @return error message as rendered string, must not be blank
	 */
	default String getErrorMessageString(){
		return this.getErrorMessage().getMessage();
	}

}
