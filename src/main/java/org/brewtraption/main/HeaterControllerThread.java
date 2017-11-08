package org.brewtraption.main;

import org.brewtraption.command.CommandUtil;
import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;
import org.brewtraption.util.HeaterOverride;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeaterControllerThread extends Thread {

  static Logger logger = LoggerFactory.getLogger(HeaterControllerThread.class);

  public HeaterControllerThread(String str) {
    super(str);
  }

  public void run() {
    while (true) {
      Double currentTemp = BrewProps.lookupDouble(Constants.HLT_CURRENT_TEMP);
      Double targetTemp = BrewProps.lookupDouble(Constants.HLT_TARGET_TEMP);

      //TODO add look up enum
      String savedProp = BrewProps.lookupString(Constants.HLT_HEATER_OVERRIDE);
      HeaterOverride override = HeaterOverride.valueOf(savedProp);

      if (!override.overridden()) {
        checkTempAndSetHeaterState(currentTemp, targetTemp);
      } else {
        setOverride(override);
      }

      sleep();
    }
  }

  private void checkTempAndSetHeaterState(Double currentTemp, Double targetTemp) {
    if (closeEnough(currentTemp, targetTemp)) {
      sleep();
    } else if (currentTemp > targetTemp) {
      switchOff();
    } else {
      switchOn();
    }
  }

  private boolean closeEnough(Double currentTemp, Double targetTemp) {
    return Math.abs(currentTemp-targetTemp) < 1;
  }

  private void switchOff() {
    CommandUtil.heaterOff();
    BrewProps.writeValue(Constants.HLT_HEATING, "false");
  }

  private void switchOn() {
    CommandUtil.heaterOn();
    BrewProps.writeValue(Constants.HLT_HEATING, "true");
  }

  private void setOverride(HeaterOverride override) {
    CommandUtil.setHeaterState(override.heaterState());
    BrewProps.writeValue(Constants.HLT_HEATING, override.heaterState().toString());
  }

  private void sleep() {
    try {
      Thread.sleep(1000);
    } catch (Exception e) {
      logger.warn(e.getMessage());
    }
  }
}
