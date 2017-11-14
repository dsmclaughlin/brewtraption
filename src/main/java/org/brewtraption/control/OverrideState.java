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
        throw new IllegalStateException();
    }
  }

  public Boolean heaterState() {
    switch (this) {
      case NONE:
        throw new IllegalStateException();
      case ON:
        return true;
      case OFF:
        return false;
      default:
        throw new IllegalStateException();
    }
  }
}
