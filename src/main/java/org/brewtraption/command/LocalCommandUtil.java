package org.brewtraption.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class LocalCommandUtil implements CommandUtil {

  private static Logger logger = LoggerFactory.getLogger(CommandUtil.class);
  private String LOG_MSG = "Running in local Mode using " + this.getClass().getSimpleName();

  @Override
  public Result heaterOn() {
    return new Result(Result.Status.SUCCESS);
  }

  @Override
  public Result heaterOff() {
    return new Result(Result.Status.SUCCESS);
  }

  @Override
  public Result setHeaterState(final boolean heat) {
    return heat ? heaterOn() : heaterOff();
  }

  @Override
  public Result readTemperature() {
    Result dummyResult = new Result(Result.Status.SUCCESS);

    Random r = new Random();
    int Low = 10;
    int High = 100;
    int randomNum = r.nextInt(High-Low) + Low;

    dummyResult.setStdOut(""+randomNum);
    return dummyResult;
  }
}