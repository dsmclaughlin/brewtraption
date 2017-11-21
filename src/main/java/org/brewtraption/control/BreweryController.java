package org.brewtraption.control;

import org.brewtraption.dto.HltDTO;
import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BreweryController {

  private static Logger logger = LoggerFactory.getLogger(BreweryController.class);

  private BreweryController() {
  }

  public static HltDTO getHTLInfo() {
    return new HltDTO(
      BrewProps.lookupInt(Constants.HLT_CURRENT_TEMP),
      BrewProps.lookupInt(Constants.HLT_TARGET_TEMP),
      BrewProps.lookupBoolean(Constants.HLT_HEATING),
      OverrideState.valueOf(BrewProps.lookupString(Constants.HLT_HEATER_OVERRIDE))
    );
  }

  public static void setHLTTargetTemperature(final Double target) {
    BrewProps.writeValue(Constants.HLT_TARGET_TEMP, target.toString());
  }


  public static void overrideHeater(final OverrideState state) {
    BrewProps.setValue(Constants.HLT_HEATER_OVERRIDE, state.toString());
    Boolean currentHeaterState = BrewProps.lookupBoolean(Constants.HLT_HEATING);
    Boolean overriddenHeaterState = state.heaterState();

    if (currentHeaterState != overriddenHeaterState) {
      BrewProps.setValue(Constants.HLT_HEATING, state.heaterState().toString());
    }
  }
}
