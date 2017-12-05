package org.brewtraption.command;

public interface CommandUtil {
  Result heaterOn();

  Result heaterOff();

  Result setHeaterState(boolean heat);

  Result readTemperature();
}
