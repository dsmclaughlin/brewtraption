package org.brewtraption.control;

import org.brewtraption.command.Result;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import static org.brewtraption.command.Result.*;

public class ThermocoupleTest {

  @Test
  public void testCallWithNullResult() {
    Thermocouple thermocouple = new Thermocouple(null);
    assertThat(thermocouple.getTemperature(), is(Thermocouple.DEFAULT_TEMP));
    assertThat(thermocouple.getTimestamp(), is(Thermocouple.DEFAULT_TIMESTAMP));
  }

  @Test
  public void testCallWithErrorResult() {
    Result failedResult = new Result(Status.FAILURE, "Testing Failure");
    Thermocouple thermocouple = new Thermocouple(failedResult);
    assertThat(thermocouple.getTemperature(), is(Thermocouple.DEFAULT_TEMP));
    assertThat(thermocouple.getTimestamp(), is(Thermocouple.DEFAULT_TIMESTAMP));
  }

  @Test
  public void testCallWithGoodResult() {
    Result successfulResult = new Result(Status.SUCCESS, "Testing Success");
    successfulResult.setStdOut("time,temp");
    Thermocouple thermocouple = new Thermocouple(successfulResult);
    String temp = thermocouple.getTemperature();
    String time = thermocouple.getTimestamp();
    assertThat(temp, is("temp"));
    assertThat(time, is("time"));
  }
}
