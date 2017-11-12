package org.brewtraption.control;

import org.brewtraption.command.CommandUtil;
import org.brewtraption.command.Result;
import org.brewtraption.dto.HltDTO;
import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BreweryDAO {

  private static Logger logger = LoggerFactory.getLogger(BreweryDAO.class);

  private BreweryDAO() {
  }

  public static HltDTO getHTLInfo() {
    //TODO implement refresh of current temp
    //TODO builder pattern for HltDTO?
    return new HltDTO(
      BrewProps.lookupInt(Constants.HLT_CURRENT_TEMP),
      BrewProps.lookupInt(Constants.HLT_TARGET_TEMP),
      BrewProps.lookupBoolean(Constants.HLT_HEATING)
    );
  }

  public static void setHLTTargetTemperature(final HltDTO hltDTO) {
    Integer target = hltDTO.getTargetTemperature();
    BrewProps.writeValue(Constants.HLT_TARGET_TEMP, target.toString());
  }

  //TODO no good anymore
  public static void heat(final boolean heat) {
    logger.info("Setting heat to " + heat);

    if (heat) {
      Result result = CommandUtil.heaterOn();

      if (result.getStatus() == Result.Status.SUCCESS) {
        BrewProps.writeValue(Constants.HLT_HEATING, "true");
        logger.info("Turned Heater On!");
      } else {
        logger.error("Could not turn heater on: " + result.getStdErr());
      }
    } else {
      Result result = CommandUtil.heaterOff();

      if (result.getStatus() == Result.Status.SUCCESS) {
        BrewProps.writeValue(Constants.HLT_HEATING, "false");
        logger.info("Turned Heater Off!");
      } else {
        logger.error("Could not turn Heater off: " + result.getStdErr());
      }
    }
  }
}
