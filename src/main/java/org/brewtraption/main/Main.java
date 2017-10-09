package org.brewtraption.main;

import org.brewtraption.server.JettyServer;
import org.brewtraption.server.JettyServerConfig;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.brewtraption.util.BreweryProperties;

public class Main {

  @Parameter(names = {"--port", "-p"}, description = "The port on which to run Jetty")
  private int port = 8080;
  @Parameter(names = {"--host"}, description = "The host on which to run Jetty")
  private String host = "0.0.0.0";
  @Parameter(names = "-console-metrics", description = "Periodically dump Metrics data to the console")
  private boolean consoleMetrics = false;
  @Parameter(names = {"--help", "-h"}, help = true, description = "Displays this help message")
  private boolean help = false;

  public static void main(final String[] args) throws Exception {
    Main main = new Main();
    BreweryProperties.initialize("./src/main/resources/brewtraption.properties");
    System.out.println("hello");
    JCommander jCommander = new JCommander(main, args);
    jCommander.setProgramName("java -jar jetty-jersey-brewtraption");
    if (main.help) {
      jCommander.usage();
      return;
    }
    main.start();
  }

  private void start() {
    JettyServer jettyServer = new JettyServer(new JettyServerConfig());
    jettyServer.start();
  }
}
