package org.brewtraption.control;

import org.brewtraption.command.CommandFactory;
import org.brewtraption.command.CommandUtil;
import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Thermostat {

  private static Logger logger = LoggerFactory.getLogger(Thermostat.class);

  public static void setHeaterState(Double current, Double target) {
    if (null == current || null == target) {
      switchOffWithWarning(current, target);
    } else if (closeEnough(current, target)) {
      switchOff();
    } else {
      switchHeater(current, target);
    }
  }

  private static void switchOffWithWarning(final Double current, final Double target) {
    String warning = "Unexpected value of current: %s or target: %s temperature found. Switching heater off";
    logger.warn(String.format(warning, current, target));
    switchOff();
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
