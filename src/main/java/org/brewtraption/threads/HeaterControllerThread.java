package org.brewtraption.threads;

import org.brewtraption.control.HeaterController;
import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeaterControllerThread extends Thread {

  private static Logger logger = LoggerFactory.getLogger(HeaterControllerThread.class);

  public HeaterControllerThread(String str) {
    super(str);
  }

  public void run() {
    while (true) {
      Double currentTemp = BrewProps.lookupDouble(Constants.HLT_CURRENT_TEMP);
      Double targetTemp = BrewProps.lookupDouble(Constants.HLT_TARGET_TEMP);
      HeaterController.setHeaterState(currentTemp, targetTemp);
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
