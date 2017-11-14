package org.brewtraption.control;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class OverrideStateTest {

  @Test
  public void testOverridden() {
    assertThat(OverrideState.NONE.overridden(), is(false));
    assertThat(OverrideState.ON.overridden(), is(true));
    assertThat(OverrideState.OFF.overridden(), is(true));
  }

  @Test
  public void heaterState() {
    assertThat(OverrideState.ON.heaterState(), is(true));
    assertThat(OverrideState.OFF.heaterState(), is(false));
  }
}
