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
    setDataForTest();
    BreweryController.getHTLInfo();
    checkDataForTestGetHLTInfo();
  }

  @Test
  public void testSetTargetTemperature() {
    setDataForTest();
    BreweryController.setHLTTargetTemperature(35.0);
    HltDTO hltDTO = BreweryController.getHTLInfo();
    assertThat(hltDTO.getTargetTemperature(), is(35));
  }

  @Test
  public void testOverrideHeaterOverrideToOn() {
    setDataForTest();
    BreweryController.overrideHeater(OverrideState.ON);
    HltDTO hltDTO = BreweryController.getHTLInfo();
    assertThat(hltDTO.getOverrideState().toString(), is("ON"));
    assertThat(hltDTO.isHeaterOn(), is(true));

  }

  @Test
  public void testOverrideHeaterOverrideToOff() {
    setDataForTest();
    BreweryController.overrideHeater(OverrideState.NONE);
    HltDTO hltDTO = BreweryController.getHTLInfo();
    assertThat(hltDTO.getOverrideState().toString(), is("OFF"));
  }

  //TODO fix above tests and add params to this method.
  private void setDataForTest() {
    BrewProps.writeValue(Constants.HLT_CURRENT_TEMP, "10.0");
    BrewProps.writeValue(Constants.HLT_TARGET_TEMP, "20.0");
    BrewProps.writeValue(Constants.HLT_HEATING, "false");
    BrewProps.writeValue(Constants.HLT_HEATER_OVERRIDE, OverrideState.NONE.toString());
  }

  private void checkDataForTestGetHLTInfo() {
    assertThat(BrewProps.lookupDouble(Constants.HLT_CURRENT_TEMP), is(10.0));
    assertThat(BrewProps.lookupDouble(Constants.HLT_TARGET_TEMP), is(20.0));
    assertThat(BrewProps.lookupBoolean(Constants.HLT_HEATING), is(true));
    assertThat(BrewProps.lookupString(Constants.HLT_HEATER_OVERRIDE), is(OverrideState.NONE.toString()));
  }
}
