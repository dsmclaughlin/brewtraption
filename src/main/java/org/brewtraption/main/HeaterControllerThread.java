package org.brewtraption.main;

import org.brewtraption.command.CommandUtil;
import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeaterControllerThread extends Thread {

  static Logger logger = LoggerFactory.getLogger(HeaterControllerThread.class);

  public HeaterControllerThread(String str) {
    super(str);
  }

  public void run() {
    while (true) {
//      Double currentTemp = BrewProps.lookupDouble(Constants.HLT_CURRENT_TEMP);
//      Double targetTemp = BrewProps.lookupDouble(Constants.HLT_TARGET_TEMP);
//
//      if (currentTemp > targetTemp) {
//        CommandUtil.heaterOff();
//        BrewProps.writeValue(Constants.HLT_HEATING, "false");
//      } else {
//        CommandUtil.heaterOn();
//        BrewProps.writeValue(Constants.HLT_HEATING, "true");
//      }

      if (BrewProps.lookupBoolean(Constants.HLT_HEATING)) {
        CommandUtil.heaterOn();
      } else {
        CommandUtil.heaterOff();
      }

      sleep();
    }
  }

  private void sleep() {
    try {
      Thread.sleep(1000);
    } catch (Exception e) {
      logger.warn(e.getMessage());
    }
  }
}
