package org.brewtraption.threads;

import org.brewtraption.command.CommandFactory;
import org.brewtraption.command.Result;
import org.brewtraption.control.Thermocouple;
import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CurrentTempUpdateThread extends Thread {

  private static Logger logger = LoggerFactory.getLogger(CurrentTempUpdateThread.class);

  public CurrentTempUpdateThread(String str) {
    super(str);
  }

  public void run() {
    while (true) {
      Result result = CommandFactory.command().readTemperature();

      if (BrewProps.lookupBoolean(Constants.DEPLOYED)) {
        Thermocouple thermocouple = new Thermocouple(result);
        BrewProps.writeValue(Constants.HLT_CURRENT_TEMP, thermocouple.getTemperature());
      } else {
        String dummyTemp = result.getStdOut();
        BrewProps.writeValue(Constants.HLT_CURRENT_TEMP, dummyTemp);
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
