package org.brewtraption.server;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener;
import com.codahale.metrics.servlets.AdminServlet;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.codahale.metrics.servlets.PingServlet;
import com.codahale.metrics.servlets.ThreadDumpServlet;
import org.brewtraption.server.metrics.ExampleHealthCheckServletContextListener;
import org.brewtraption.server.metrics.ExampleMetricsServletContextListener;
import org.brewtraption.server.metrics.MetricsUtil;
import org.brewtraption.websocket.EventSocket;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;

public class JettyServer {

  private static final String API_PREFIX = "/api/*";
  private static final String RESOURCE_PACKAGES_TO_SCAN = "org.brewtraption.rest";
  Logger logger = LoggerFactory.getLogger(JettyServer.class);
  private JettyServerConfig config = null;

  public JettyServer(final JettyServerConfig config) {
    this.config = config;
  }

  public void start() {
    InetSocketAddress socket = new InetSocketAddress(config.getHost(), config.getPort());
    Server server = new Server(socket);
    ServletHolder servletHolder = configureAPIResources();
    ServletContextHandler context = configureServletContextHandler(server, servletHolder);
    initaliseWebSocketContainer(context);
    startBrewtraptionServer(server);
  }

  private void startBrewtraptionServer(Server server) {
    try {
      server.start();
      server.join();
    } catch (Exception e) {
      String message = "Unable to start Brewtraption Server. %s %s";
      logger.error(String.format(message, e.getClass().getName(), e.getMessage()));
    }
  }

  private void initaliseWebSocketContainer(ServletContextHandler context) {
    try {
      ServerContainer container = WebSocketServerContainerInitializer.configureContext(context);
      container.addEndpoint(EventSocket.class);
    } catch (ServletException | DeploymentException e) {
      String message = "Unable to create ServerContainer. %s %s";
      logger.error(String.format(message, e.getClass().getName(), e.getMessage()));
    }
  }

  private ServletContextHandler configureServletContextHandler(Server server, ServletHolder servletHolder) {
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    context.addServlet(servletHolder, API_PREFIX);

    registerMetricsServlets(context);
    server.setHandler(context);
    return context;
  }

  private ServletHolder configureAPIResources() {
    ResourceConfig resourceConfig = new ResourceConfig();
    resourceConfig = resourceConfig.packages(RESOURCE_PACKAGES_TO_SCAN)
      .register(JacksonFeature.class);
    registerMetrics(resourceConfig);
    ServletContainer servletContainer = new ServletContainer(resourceConfig);
    return new ServletHolder(servletContainer);
  }

  private void registerMetricsServlets(final ServletContextHandler context) {
    context.addEventListener(new ExampleHealthCheckServletContextListener());
    context.addEventListener(new ExampleMetricsServletContextListener());
    context.addServlet(AdminServlet.class, "/admin");
    context.addServlet(HealthCheckServlet.class, "/admin/healthcheck");
    context.addServlet(MetricsServlet.class, "/admin/metrics");
    context.addServlet(PingServlet.class, "/admin/ping");
    context.addServlet(ThreadDumpServlet.class, "/admin/threads");
  }

  private void registerMetrics(final ResourceConfig resourceConfig) {
    logger.info("Registering Metrics service");
    MetricRegistry metricsReg = MetricsUtil.getMetricsRegistry();
    resourceConfig.register(new InstrumentedResourceMethodApplicationListener(metricsReg));
    if (config.isMetricsEnabled()) {
      logger.info("Enabling console Metrics reporting");
      ConsoleReporter.forRegistry(metricsReg).convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.MILLISECONDS).build().start(10, TimeUnit.SECONDS);
    }
    logger.info("Registered Metrics service");
  }

}
