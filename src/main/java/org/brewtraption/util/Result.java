package org.brewtraption.util;

public class Result {
  private Status resultStatus;
  private String customMessage = "";

  private String stdOut  = "";
  private String stdErr = "";

  private Throwable failureCause = null;

  public Result(final Status resultStatus, final String customMessage) {
    this(resultStatus);
    this.customMessage = customMessage;
  }

  public Result(final Status resultStatus, final String customMessage, final Throwable failureCause) {
    this(resultStatus,customMessage);
    this.failureCause = failureCause;
  }

  public Result(final Status resultStatus) {
    this.resultStatus = resultStatus;
  }


  public Status getResultStatus() {
    return resultStatus;
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

  public Throwable getFailureCause() {
    return failureCause;
  }

  public enum Status {
    NULL,
    FAILURE,
    SUCCESS
  }
}
