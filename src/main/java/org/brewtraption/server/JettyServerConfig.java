package org.brewtraption.server;

import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;

public class JettyServerConfig {
  private String hostname;
  private int port;
  private boolean metricsEnabled;

  public JettyServerConfig() {
    this.hostname = BrewProps.lookupString(Constants.HOST);
    this.port = BrewProps.lookupInt(Constants.PORT);
    this.metricsEnabled = BrewProps.lookupBoolean(Constants.METRICS_ENABLED);
  }

  int getPort() {
    return port;
  }

  String getHost() {
    return hostname;
  }

  boolean isMetricsEnabled() {
    return metricsEnabled;
  }
}