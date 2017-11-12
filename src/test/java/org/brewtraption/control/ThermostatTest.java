package org.brewtraption.control;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import mockit.Mocked;
import mockit.StrictExpectations;
import org.brewtraption.command.CommandUtil;
import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

public class ThermostatTest {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Mocked
  public CommandUtil commandUtil;

  @Before
  public void setup() throws IOException {
    File propertyFile = temporaryFolder.newFile("brewtraption.properties");
    BrewProps.initialize(propertyFile.getAbsolutePath());
  }

  @Test
  public void testTurnHeaterOn() {
    expectHeaterToSwitchOn();
    Thermostat.setHeaterState(8.0, 10.0);
    assertThat(BrewProps.lookupBoolean(Constants.HLT_HEATING), is(true));
  }

  @Test
  public void testTurnHeaterOff() {
    expectHeaterToSwitchOff();
    Thermostat.setHeaterState(10.0, 8.0);
    assertThat(BrewProps.lookupBoolean(Constants.HLT_HEATING), is(false));
  }

  @Test
  public void testTurnHeaterOffWhenCloseEnough() {
    expectHeaterToSwitchOff();
    double current = 10.0;
    double target = current + BrewProps.lookupDouble(Constants.HTL_OFF_DELTA);
    Thermostat.setHeaterState(current, target);
  }

  @Test
  public void testHeaterSwitchNotAffectedByDeltaOutsideRage() {
    expectHeaterToSwitchOn();
    double current = 10.0;
    double target = current + BrewProps.lookupDouble(Constants.HTL_OFF_DELTA) + 0.1;
    Thermostat.setHeaterState(current, target);
  }

  private void expectHeaterToSwitchOn() {
    new StrictExpectations() {{
      CommandUtil.heaterOn(); times = 1;
      CommandUtil.heaterOff(); times = 0;
    }};
  }

  private void expectHeaterToSwitchOff() {
    new StrictExpectations() {{
      CommandUtil.heaterOn(); times = 0;
      CommandUtil.heaterOff(); times = 1;
    }};
  }
}
