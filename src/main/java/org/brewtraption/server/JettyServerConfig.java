package org.brewtraption.server;

import org.brewtraption.util.BreweryProperties;
import org.brewtraption.util.Constants;

public class JettyServerConfig {
  private String hostname;
  private int port;
  private boolean metricsEnabled;

  public JettyServerConfig() {
    this.hostname = BreweryProperties.lookupString(Constants.HOST);
    this.port = BreweryProperties.lookupInt(Constants.PORT);
    this.metricsEnabled = BreweryProperties.lookupBoolean(Constants.METRICS_ENABLED);
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