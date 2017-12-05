package org.brewtraption.command;

import org.brewtraption.util.Constants;

public class PiCommandUtil implements CommandUtil {

  @Override
  public Result heaterOn() {
    return new Command("python", Constants.HLT_ON_SCRIPT).runCommand();
  }

  public Result heaterOff() {
    return new Command("python", Constants.HLT_OFF_SCRIPT).runCommand();
  }

  public Result setHeaterState(final boolean heat) {
    return heat ? heaterOn() : heaterOff();
  }

  public Result readTemperature() {
    return new Command("python", Constants.HLT_SENSOR_SCRIPT).runCommand();
  }
}