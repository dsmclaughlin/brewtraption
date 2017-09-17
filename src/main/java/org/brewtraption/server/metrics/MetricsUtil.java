/**
 * Copyright (c) 2016 Owain Lewis
 */
package org.brewtraption.server.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;

public class MetricsUtil {

  private static HealthCheckRegistry healthRegistry = null;
  private static MetricRegistry metricsRegistry = null;

  public static MetricRegistry getMetricsRegistry() {
    if (metricsRegistry == null) {
      metricsRegistry = new MetricRegistry();
    }
    return metricsRegistry;
  }

  public static HealthCheckRegistry getHealthCheckRegistry() {
    if (healthRegistry == null) {
      healthRegistry = new HealthCheckRegistry();
    }
    return healthRegistry;
  }

}
