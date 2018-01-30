package org.brewtraption.server;

import org.brewtraption.threads.CurrentTempUpdateThread;
import org.brewtraption.threads.HeaterControllerThread;
import org.brewtraption.threads.WebSocketBroadcastThread;
import org.brewtraption.websocket.EventSocket;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

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
    addUIResourcesToContext(context);

    WebSocketBroadcastThread numberThread =
      new WebSocketBroadcastThread(WebSocketBroadcastThread.class.getSimpleName());
    numberThread.start();

    HeaterControllerThread heaterControllerThread =
      new HeaterControllerThread(HeaterControllerThread.class.getSimpleName());
    heaterControllerThread.start();

    CurrentTempUpdateThread updateThread =
      new CurrentTempUpdateThread(CurrentTempUpdateThread.class.getSimpleName());
    updateThread.start();

    startBrewtraptionServer(server);
  }

  private void addUIResourcesToContext(ServletContextHandler context) {
    context.setWelcomeFiles(new String[] { "index.html" });
    ServletHolder holderPwd = new ServletHolder("default", DefaultServlet.class);
    holderPwd.setInitParameter("resourceBase", "client/");
    context.addServlet(holderPwd,"/");
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

    FilterHolder filterHolder = new FilterHolder(CrossOriginFilter.class);
    filterHolder.setInitParameter("allowedOrigins", "*");
    filterHolder.setInitParameter("allowedMethods", "GET, POST, PUT");
    context.addFilter(filterHolder, "/*", null);

    server.setHandler(context);
    return context;
  }

  private ServletHolder configureAPIResources() {
    ResourceConfig resourceConfig = new ResourceConfig();
    resourceConfig = resourceConfig.packages(RESOURCE_PACKAGES_TO_SCAN)
      .register(JacksonFeature.class);
    ServletContainer servletContainer = new ServletContainer(resourceConfig);
    return new ServletHolder(servletContainer);
  }
}
