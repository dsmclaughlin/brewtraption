package org.brewtraption.command;

public class Result {
  private Status status;
  private String customMessage = "";

  private String stdOut  = "";
  private String stdErr = "";

  private Throwable failureCause = null;

  public Result(final Status status, final String customMessage) {
    this(status);
    this.customMessage = customMessage;
  }

  public Result(final Status status, final String customMessage, final Throwable failureCause) {
    this(status,customMessage);
    this.failureCause = failureCause;
  }

  public Result(final Status status) {
    this.status = status;
  }


  public Status getStatus() {
    return status;
  }

  public String getCustomMessage() {
    return customMessage;
  }

  public String getStdErr() {
    return stdErr;
  }

  public String getStdOut() {
    return stdOut;
  }

  public void setStdOut(final String stdOut) {
    this.stdOut = stdOut;
  }

  public void setStdErr(final String stdErr) {
    this.stdErr = stdErr;
  }

  /**
   *
   * @return Will return null if no Throwable has been attached to this result as the failure failureCause, otherwise will return the throwable
   */
  public Throwable getFailureCause() {
    return failureCause;
  }

  public enum Status {
    NULL,
    FAILURE,
    SUCCESS
  }
}
