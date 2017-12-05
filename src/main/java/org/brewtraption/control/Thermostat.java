package org.brewtraption.control;

import org.brewtraption.command.CommandFactory;
import org.brewtraption.command.PiCommandUtil;
import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;

public class Thermostat {
  public static void setHeaterState(Double current, Double target) {
    if (closeEnough(current, target)) {
      switchOff();
    } else {
      switchHeater(current, target);
    }
  }

  private static void switchHeater(Double current, Double target) {
    if (current > target) {
      switchOff();
    } else {
      switchOn();
    }
  }

  private static boolean closeEnough(Double current, Double target) {
    return Math.abs(current-target) <= BrewProps.lookupDouble(Constants.HTL_OFF_DELTA);
  }

  private static void switchOff() {
    CommandFactory.command().heaterOff();
    BrewProps.writeValue(Constants.HLT_HEATING, "false");
  }

  private static void switchOn() {
    CommandFactory.command().heaterOn();
    BrewProps.writeValue(Constants.HLT_HEATING, "true");
  }
}
