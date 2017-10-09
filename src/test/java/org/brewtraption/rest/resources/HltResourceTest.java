package org.brewtraption.rest.resources;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;

public class HltResourceTest extends JerseyTest {

  @Override
  protected Application configure() {
    return new ResourceConfig(HltResource.class);
  }

  @Test
  public void testGetHlt() {
    final String testResourceValue = target("hlt").request().get(String.class);
    //assertThat(testResourceValue, is("HTL Response"));
  }
}
