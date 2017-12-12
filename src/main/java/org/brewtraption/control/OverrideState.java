package org.brewtraption.control;

public enum OverrideState {
  NONE, ON, OFF;

  public Boolean overridden() {
    switch (this) {
      case NONE:
        return false;
      case ON:
        return true;
      case OFF:
        return true;
      default:
        throw new IllegalStateException("Illegal OverrideState encountered when calling overridden method");
    }
  }

  public Boolean heaterState() {
    switch (this) {
      case NONE:
        String message = "Illegal OverrideState %s is not a valid heater state";
        throw new IllegalStateException(String.format(message, NONE.toString()));
      case ON:
        return true;
      case OFF:
        return false;
      default:
        throw new IllegalStateException("Illegal OverrideState encountered, no default implementation.");
    }
  }
}
