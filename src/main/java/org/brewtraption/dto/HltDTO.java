package org.brewtraption.dto;

//TODO should we add annotations?
public class HltDTO {
  private int currentTemperature;
  private int targetTemperature;
  private boolean heaterOn;

  public HltDTO(int currentTemperature, int targetTemperature, boolean heaterOn) {
    this.currentTemperature = currentTemperature;
    this.targetTemperature = targetTemperature;
    this.heaterOn = heaterOn;
  }

  public int getCurrentTemperature() {
    return currentTemperature;
  }

  public void setCurrentTemperature(int currentTemperature) {
    this.currentTemperature = currentTemperature;
  }

  public int getTargetTemperature() {
    return targetTemperature;
  }

  public void setTargetTemperature(int targetTemperature) {
    this.targetTemperature = targetTemperature;
  }

  public boolean isHeaterOn() {
    return heaterOn;
  }

  public void setHeaterOn(boolean heaterOn) {
    this.heaterOn = heaterOn;
  }
}
