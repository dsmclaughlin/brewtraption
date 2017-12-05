package org.brewtraption.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalCommandUtil implements CommandUtil {

  private static Logger logger = LoggerFactory.getLogger(CommandUtil.class);
  private String LOG_MSG = "Running in local Mode using " + this.getClass().getSimpleName();

  @Override
  public Result heaterOn() {
    logger.info(LOG_MSG);
    return new Result(Result.Status.SUCCESS);
  }

  @Override
  public Result heaterOff() {
    logger.info(LOG_MSG);
    return new Result(Result.Status.SUCCESS);
  }

  @Override
  public Result setHeaterState(final boolean heat) {
    return heat ? heaterOn() : heaterOff();
  }

  @Override
  public Result readTemperature() {
    logger.info(LOG_MSG);
    return new Result(Result.Status.SUCCESS);
  }
}