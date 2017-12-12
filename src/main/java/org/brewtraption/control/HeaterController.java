package org.brewtraption.control;

import org.brewtraption.command.CommandFactory;
import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;

public class HeaterController {

  public static void setHeaterState(final Double current, final Double target) {
    OverrideState override = BrewProps.lookUpOverrideState(Constants.HLT_HEATER_OVERRIDE);

    if (override.overridden()) {
      setOverride(override);
    } else {
      Thermostat.setHeaterState(current, target);
    }
  }

  private static void setOverride(final OverrideState override) {
    CommandFactory.command().setHeaterState(override.heaterState());
    BrewProps.writeValue(Constants.HLT_HEATING, override.heaterState().toString());
    BrewProps.writeValue(Constants.HLT_HEATER_OVERRIDE, override.toString());
  }
}
