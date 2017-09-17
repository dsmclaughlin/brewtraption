package org.brewtraption.server.metrics;

import javax.servlet.annotation.WebListener;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;

@WebListener
public class ExampleHealthCheckServletContextListener extends HealthCheckServlet.ContextListener {

  @Override
  protected HealthCheckRegistry getHealthCheckRegistry() {
    return MetricsUtil.getHealthCheckRegistry();
  }

}
