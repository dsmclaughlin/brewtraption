package org.brewtraption.control;

import org.brewtraption.command.CommandFactory;
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
    final double abs = Math.abs(current - target);
    final Double aDouble = BrewProps.lookupDouble(Constants.HTL_OFF_DELTA);
    return abs <= aDouble;
  }

  private static void switchOff() {
    if (BrewProps.lookupBoolean(Constants.HLT_HEATING)) {
      CommandFactory.command().heaterOff();
      BrewProps.writeValue(Constants.HLT_HEATING, "false");
    }
  }

  private static void switchOn() {
    if (!BrewProps.lookupBoolean(Constants.HLT_HEATING)) {
      CommandFactory.command().heaterOn();
      BrewProps.writeValue(Constants.HLT_HEATING, "true");
    }
  }
}
