package org.brewtraption.control;

public enum OverrideState {
  NO_OVERRIDE, ON, OFF;

  public Boolean overridden() {
    switch (this) {
      case NO_OVERRIDE:
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
      case NO_OVERRIDE:
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
