package org.brewtraption.dto;

import org.brewtraption.control.OverrideState;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "HLT")
public class HltDTO {
  private int currentTemperature;
  private int targetTemperature;
  private boolean heaterOn;
  private OverrideState overrideState;

  public HltDTO() {
  }

  public HltDTO(final int temp, final int targetTemp,
                      final boolean heaterOn, final OverrideState overrideState) {
    this.currentTemperature = temp;
    this.targetTemperature = targetTemp;
    this.heaterOn = heaterOn;
    this.overrideState = overrideState;
  }

  @XmlElement(name = "currentTemperature")
  public int getCurrentTemperature() {
    return currentTemperature;
  }

  public void setCurrentTemperature(final int currentTemperature) {
    this.currentTemperature = currentTemperature;
  }

  @XmlElement(name = "targetTemperature")
  public int getTargetTemperature() {
    return targetTemperature;
  }

  public void setTargetTemperature(final int targetTemperature) {
    this.targetTemperature = targetTemperature;
  }

  @XmlElement(name = "heaterOn")
  public boolean isHeaterOn() {
    return heaterOn;
  }

  public void setHeaterOn(final boolean heaterOn) {
    this.heaterOn = heaterOn;
  }

  @XmlElement(name = "overrideState")
  public OverrideState getOverrideState() {
    return overrideState;
  }

  public void setOverrideState(final OverrideState overrideState) {
    this.overrideState = overrideState;
  }
}
