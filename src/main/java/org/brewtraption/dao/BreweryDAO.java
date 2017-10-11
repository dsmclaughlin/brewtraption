package org.brewtraption.dao;

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
      BrewProps.lookupInt(Constants.HTL_CURRENT_TEMP),
      BrewProps.lookupInt(Constants.HLT_TARGET_TEMP),
      BrewProps.lookupBoolean(Constants.HTL_HEATING)
    );
  }

  public static void setHLTTargetTemperature(final HltDTO hltDTO) {
    Integer target = hltDTO.getTargetTemperature();
    BrewProps.writeValue(Constants.HLT_TARGET_TEMP, target.toString());
  }
}
