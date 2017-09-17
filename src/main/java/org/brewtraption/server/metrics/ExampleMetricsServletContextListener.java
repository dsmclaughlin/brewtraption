package org.brewtraption.server.metrics;

import javax.servlet.annotation.WebListener;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;

@WebListener
public class ExampleMetricsServletContextListener extends MetricsServlet.ContextListener {

  @Override
  protected MetricRegistry getMetricRegistry() {
    return MetricsUtil.getMetricsRegistry();
  }

}
