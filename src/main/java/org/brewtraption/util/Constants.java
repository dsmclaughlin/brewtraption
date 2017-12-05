package org.brewtraption.util;

public class Constants {

  private Constants() {
  }

  public static final String HOST = "jetty.bind.host";
  public static final String PORT = "jetty.bind.port";
  public static final String DEPLOYED = "jetty.deployed";

  public static final String HLT_TARGET_TEMP = "hlt.temp.target";
  public static final String HLT_CURRENT_TEMP = "hlt.temp.current";
  public static final String HLT_HEATING = "hlt.heating";
  public static final String HLT_HEATER_OVERRIDE = "hlt.heater.override";
  public static final String HTL_OFF_DELTA = "hlt.heater.off.delta";

  public static final String HLT_ON_SCRIPT = "./resources/heater_on.py";
  public static final String HLT_OFF_SCRIPT = "./resources/heater_off.py";
  public static final String HLT_SENSOR_SCRIPT = "./resources/sensor.py";


  public static final String PROPS_FILE = "./resources/brewtraption.properties";
  public static final String SENSOR_OUTPUT = "./resources/sensor.out";
}
