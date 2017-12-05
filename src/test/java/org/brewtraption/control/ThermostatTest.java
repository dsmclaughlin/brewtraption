package org.brewtraption.control;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.sun.tools.classfile.ConstantPool;
import mockit.Capturing;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import org.brewtraption.command.CommandFactory;
import org.brewtraption.command.CommandUtil;
import org.brewtraption.command.PiCommandUtil;
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

  @Before
  public void setup() throws IOException {
    File propertyFile = temporaryFolder.newFile("brewtraption.properties");
    BrewProps.initialize(propertyFile.getAbsolutePath());
    BrewProps.writeValue(Constants.DEPLOYED, "true");
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
    final CommandUtil util = new PiCommandUtil();
    new StrictExpectations(PiCommandUtil.class, CommandFactory.class) {{
      CommandFactory.command(); result = util;
      util.heaterOn(); times = 1;
      util.heaterOff(); times = 0;
    }};
  }

  private void expectHeaterToSwitchOff() {
    final CommandUtil util = new PiCommandUtil();
    new StrictExpectations(PiCommandUtil.class, CommandFactory.class) {{
      CommandFactory.command(); result = util;
      util.heaterOn(); times = 0;
      util.heaterOff(); times = 1;
    }};
  }
}
