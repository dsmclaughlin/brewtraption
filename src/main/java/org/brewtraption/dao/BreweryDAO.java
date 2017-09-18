package org.brewtraption.dao;

import org.brewtraption.dto.HltDTO;
import org.brewtraption.util.CommandRunner;
import org.brewtraption.util.Result;

public class BreweryDAO {

  private BreweryDAO() {
  }

  //TODO figure out proper names for scripts
  public static HltDTO getHTLInfo() {
    final CommandRunner commandRunner = new CommandRunner("./temperature.py'");
    Result result = commandRunner.runCommand();

    final int currentTemp = Integer.parseInt(result.getStdOut());

    //TODO remove dummy values
    final int targetTemp = 66;
    final boolean heaterOn = false;

    return new HltDTO(currentTemp, targetTemp, heaterOn);
  }

  public static void setHTLTargetTemperature(final HltDTO hltDTO) {
    //TODO this is where we will set the temperature into a properties file.
  }
}
