package org.brewtraption.control;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.brewtraption.dto.HltDTO;
import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

public class BreweryControllerTest {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Before
  public void setup() throws IOException {
    File propertyFile = temporaryFolder.newFile("brewtraption.properties");
    BrewProps.initialize(propertyFile.getAbsolutePath());
  }

  @Test
  public void testGetHTLInfo() {
    setDataForTest("10.0", "20.0", "false", OverrideState.NONE);
    BreweryController.getHTLInfo();
    checkDataForTestGetHLTInfo(10.0, 20.0, false, OverrideState.NONE);
  }

  @Test
  public void testSetTargetTemperature() {
    setDataForTest("30.0", "20.0", "false", OverrideState.NONE);
    BreweryController.setHLTTargetTemperature(35.0);
    HltDTO hltDTO = BreweryController.getHTLInfo();
    assertThat(hltDTO.getTargetTemperature(), is(35.0));
  }

  @Test
  public void testOverrideHeaterOverrideToOn() {
    setDataForTest("10", "20", "false", OverrideState.NONE);
    BreweryController.overrideHeater(OverrideState.ON);
    HltDTO hltDTO = BreweryController.getHTLInfo();
    assertThat(hltDTO.getOverrideState().toString(), is("ON"));
    assertThat(hltDTO.isHeaterOn(), is(true));

  }

  @Test
  public void testOverrideHeaterOverrideToOff() {
    setDataForTest("10", "20", "false", OverrideState.NONE);
    BreweryController.overrideHeater(OverrideState.OFF);
    HltDTO hltDTO = BreweryController.getHTLInfo();
    assertThat(hltDTO.getOverrideState().toString(), is("OFF"));
  }

  @Test
  public void testOverrideHeaterOverrideToNone() {
    setDataForTest("10", "20", "false", OverrideState.ON);
    BreweryController.overrideHeater(OverrideState.NONE);
    HltDTO hltDTO = BreweryController.getHTLInfo();
    assertThat(hltDTO.getOverrideState().toString(), is("NONE"));
  }

  private void setDataForTest(final String current, final String target,
                              final String heating, final OverrideState override) {
    BrewProps.writeValue(Constants.HLT_CURRENT_TEMP, current);
    BrewProps.writeValue(Constants.HLT_TARGET_TEMP, target);
    BrewProps.writeValue(Constants.HLT_HEATING, heating);
    BrewProps.writeValue(Constants.HLT_HEATER_OVERRIDE, override.toString());
  }

  private void checkDataForTestGetHLTInfo(final Double current, final Double target,
                                          final boolean heating, final OverrideState override) {
    assertThat(BrewProps.lookupDouble(Constants.HLT_CURRENT_TEMP), is(current));
    assertThat(BrewProps.lookupDouble(Constants.HLT_TARGET_TEMP), is(target));
    assertThat(BrewProps.lookupBoolean(Constants.HLT_HEATING), is(heating));
    assertThat(BrewProps.lookupString(Constants.HLT_HEATER_OVERRIDE), is(override.toString()));
  }
}
