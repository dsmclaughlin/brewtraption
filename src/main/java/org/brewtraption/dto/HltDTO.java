package org.brewtraption.dto;

//TODO should we add annotations?
public class HltDTO {
  private int currentTemperature;
  private int targetTemperature;
  private boolean heaterOn;

  public HltDTO() {
  }

  public HltDTO(int temp, int targetTemp, boolean heaterOn) {
    this.currentTemperature = temp;
    this.targetTemperature = targetTemp;
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
