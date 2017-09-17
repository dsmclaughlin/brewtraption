/**
 * Copyright (c) 2016 Owain Lewis
 */
package org.brewtraption.controller.exception;

public class UserException extends Exception {

  private static final long serialVersionUID = 5974799126431172931L;

  public UserException() {
    super();
  }

  public UserException(final String message, final Throwable cause, final boolean enableSuppression,
                       final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public UserException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public UserException(final String message) {
    super(message);
  }

  public UserException(final Throwable cause) {
    super(cause);
  }

}
