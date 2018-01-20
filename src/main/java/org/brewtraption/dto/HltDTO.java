package org.brewtraption.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.brewtraption.control.OverrideState;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "HLT")
public class HltDTO {
  private Double currentTemperature;
  private Double targetTemperature;
  private boolean heaterOn;
  private OverrideState overrideState;

  public HltDTO() {
  }

  public HltDTO(final Double temp, final Double targetTemp,
                final boolean heaterOn, final OverrideState overrideState) {
    this.currentTemperature = temp;
    this.targetTemperature = targetTemp;
    this.heaterOn = heaterOn;
    this.overrideState = overrideState;
  }

  @XmlElement(name = "currentTemperature")
  public Double getCurrentTemperature() {
    return currentTemperature;
  }

  public void setCurrentTemperature(final Double currentTemperature) {
    this.currentTemperature = currentTemperature;
  }

  @XmlElement(name = "targetTemperature")
  public Double getTargetTemperature() {
    return targetTemperature;
  }

  public void setTargetTemperature(final Double targetTemperature) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    HltDTO hltDTO = (HltDTO) o;

    return new EqualsBuilder()
      .append(heaterOn, hltDTO.heaterOn)
      .append(currentTemperature, hltDTO.currentTemperature)
      .append(targetTemperature, hltDTO.targetTemperature)
      .append(overrideState, hltDTO.overrideState)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .append(currentTemperature)
      .append(targetTemperature)
      .append(heaterOn)
      .append(overrideState)
      .toHashCode();
  }
}
