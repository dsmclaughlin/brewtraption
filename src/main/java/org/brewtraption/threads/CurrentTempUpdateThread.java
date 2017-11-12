package org.brewtraption.threads;

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

  static Logger logger = LoggerFactory.getLogger(CurrentTempUpdateThread.class);

  public CurrentTempUpdateThread(String str) {
    super(str);
  }

  public void run() {
    while (true) {
      Double currentTemp = readFIle();
      BrewProps.writeValue(Constants.HLT_CURRENT_TEMP, currentTemp.toString());
      sleep();
    }
  }

  private Double readFIle() {
    Path path = Paths.get(Constants.SENSOR_OUTPUT);
    List<String> lines = null;

    try {
      lines = Files.readAllLines(path);
    } catch (IOException e) {
      logger.error("Could not read temperature from: " + Constants.SENSOR_OUTPUT);
    }

    String temp = "0";
    if (null != lines && !lines.isEmpty()) {
      temp = lines.get(0);
    } else {
      logger.error("Could not read temperature from: " + Constants.SENSOR_OUTPUT);
    }

    return Double.parseDouble(temp);
  }

  private void sleep() {
    try {
      Thread.sleep(1000);
    } catch (Exception e) {
      logger.warn(e.getMessage());
    }
  }
}
