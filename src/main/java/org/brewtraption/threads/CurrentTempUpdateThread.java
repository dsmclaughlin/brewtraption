package org.brewtraption.threads;

import org.brewtraption.command.CommandUtil;
import org.brewtraption.command.Result;
import org.brewtraption.control.Thermocouple;
import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CurrentTempUpdateThread extends Thread {

  private static Logger logger = LoggerFactory.getLogger(CurrentTempUpdateThread.class);

  public CurrentTempUpdateThread(String str) {
    super(str);
  }

  public void run() {
    while (true) {
      Result result = CommandUtil.readTemperature();
      Thermocouple thermocouple = new Thermocouple(result);
      BrewProps.writeValue(Constants.HLT_CURRENT_TEMP, thermocouple.getTemperature());
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
