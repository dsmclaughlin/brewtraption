package org.brewtraption.control;

import org.brewtraption.command.CommandUtil;
import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;

public class HeaterController {

  public static void setHeaterState(final Double current, final Double target) {
    //TODO add look up enum
    String savedProp = BrewProps.lookupString(Constants.HLT_HEATER_OVERRIDE);
    OverrideState override = OverrideState.valueOf(savedProp);

    if (override.overridden()) {
      setOverride(override);
    } else {
      Thermostat.setHeaterState(current, target);
    }
  }

  private static void setOverride(OverrideState override) {
    CommandUtil.setHeaterState(override.heaterState());
    BrewProps.writeValue(Constants.HLT_HEATING, override.heaterState().toString());
  }
}
