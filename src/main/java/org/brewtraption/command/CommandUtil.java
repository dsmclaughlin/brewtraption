/*
 * Copyright 2014 WANDisco
 */
package org.brewtraption.command;

import org.brewtraption.util.Constants;

public class CommandUtil {

  public static Result heaterOn() {
    return new Command("python", Constants.HLT_ON_SCRIPT).runCommand();
  }

  public static Result heaterOff() {
    return new Command("python", Constants.HLT_OFF_SCRIPT).runCommand();
  }

  public static Result setHeaterState(final boolean heat) {
    if (heat) {
      return heaterOn();
    }
    return heaterOff();
  }

  public static Result readTemperature() {
    return new Command("python", Constants.HLT_SENSOR_SCRIPT).runCommand();
  }
}
