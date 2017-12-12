package org.brewtraption.control;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import mockit.StrictExpectations;

public class HeaterControllerTest {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Before
  public void setup() throws IOException {
    File propertyFile = temporaryFolder.newFile("brewtraption.properties");
    BrewProps.initialize(propertyFile.getAbsolutePath());
  }

  @Test
  public void testNoOverride() {
    Double current = 10.0;
    Double target = 10.0;
    expectSetHeaterState(current, target);
    BrewProps.writeValue(Constants.HLT_HEATER_OVERRIDE, OverrideState.NONE.toString());
    HeaterController.setHeaterState(current, target);
  }

  @Test
  public void testOverrideToOn() {
    Double current = 15.0;
    Double target = 10.0;
    expectNotToSetHeaterState();
    BrewProps.writeValue(Constants.HLT_HEATER_OVERRIDE, OverrideState.ON.toString());
    HeaterController.setHeaterState(current, target);
    assertThat(BrewProps.lookupBoolean(Constants.HLT_HEATING), is(true));
  }

  @Test
  public void testOverrideToOff() {
    Double current = 10.0;
    Double target = 15.0;
    expectNotToSetHeaterState();
    BrewProps.writeValue(Constants.HLT_HEATER_OVERRIDE, OverrideState.OFF.toString());
    HeaterController.setHeaterState(current, target);
    assertThat(BrewProps.lookupBoolean(Constants.HLT_HEATING), is(false));
  }

  private void expectSetHeaterState(final Double current, final Double target) {
    new StrictExpectations(Thermostat.class) {{
      Thermostat.setHeaterState(current, target); times = 1;
    }};
  }

  private void expectNotToSetHeaterState() {
    new StrictExpectations(Thermostat.class) {{
      Thermostat.setHeaterState(anyDouble, anyDouble); times = 0;
    }};
  }
}
