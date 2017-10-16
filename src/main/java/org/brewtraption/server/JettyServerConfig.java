package org.brewtraption.server;

import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;

public class JettyServerConfig {
  private String hostname;
  private int port;

  public JettyServerConfig() {
    this.hostname = BrewProps.lookupString(Constants.HOST);
    this.port = BrewProps.lookupInt(Constants.PORT);
  }

  int getPort() {
    return port;
  }

  String getHost() {
    return hostname;
  }
}