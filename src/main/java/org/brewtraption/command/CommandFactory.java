package org.brewtraption.command;

import org.brewtraption.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CommandFactory {
  private static Logger logger = LoggerFactory.getLogger(CommandFactory.class);

  private static CommandUtil util = null;

  public static CommandUtil command() {
    if (null == util) {
      return getProperUtil();
    } else {
      return util;
    }
  }

  //TODO - not very good, replace with var that is included in packaged at build time
  private static CommandUtil getProperUtil() {
    if (getHostName().contains("raspberry")) {
      return new PiCommandUtil();
    } else {
      logWarning();
      return new LocalCommandUtil();
    }
  }

  private static void logWarning() {
    String warnMsg = "Host name did not contain raspberry, running in local mode, override by setting % to true.";
    logger.warn(String.format(warnMsg, Constants.DEPLOYED));
  }

  private static String getHostName() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      logger.error("Failed to get host name, assuming we're not running on a Pi");
      return "local";
    }
  }
}
