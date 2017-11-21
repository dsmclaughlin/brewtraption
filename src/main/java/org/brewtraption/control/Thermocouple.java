package org.brewtraption.control;

import org.brewtraption.command.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Thermocouple {
  private static Logger logger = LoggerFactory.getLogger(Thermocouple.class);
  static final String DEFAULT_TIMESTAMP = "0";
  static final String DEFAULT_TEMP = "99999";

  private String timestamp = DEFAULT_TIMESTAMP;
  private String temperature = DEFAULT_TEMP;

  public Thermocouple(final Result result) {
    if (null != result) {
      processScriptOutput(result);
    } else {
      logger.error("Null Result when trying to read temperature.");
    }
  }

  private void processScriptOutput(final Result result) {
    if (result.getStatus().equals(Result.Status.SUCCESS)) {
      final String[] timeTemperature = result.getStdOut().split("\\s*,\\s*");
      extractTimestamp(timeTemperature);
      extractTemperature(timeTemperature);
    } else {
      String message = "Failed to get result from thermocouple: %s";
      logger.error(String.format(message, result.getStdErr()));
    }
  }

  private void extractTemperature(final String[] timeTemperature) {
    if (timeTemperature.length > 1) {
      this.temperature = timeTemperature[1];
    } else {
      String message = "No temperature returned from thermocouple, setting to %s";
      logger.error(String.format(message, temperature));
    }
  }

  private void extractTimestamp(final String[] timeTemperature) {
    if (timeTemperature.length > 0) {
      timestamp = timeTemperature[0];
    } else {
      String message = "No timestamp returned from thermocouple, setting to %s";
      logger.error(String.format(message, timestamp));
    }
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getTemperature() {
    return temperature;
  }
}