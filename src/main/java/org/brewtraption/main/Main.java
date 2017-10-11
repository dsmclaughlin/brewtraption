package org.brewtraption.main;

import org.brewtraption.server.JettyServer;
import org.brewtraption.server.JettyServerConfig;
import org.brewtraption.util.BrewProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  static Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(final String[] args) throws Exception {
    Main main = new Main();

    BrewProps.initialize("./src/main/resources/brewtraption.properties");
    logger.warn("TODO: Update so temporary props file is made before initalise");

    main.start();
  }

  private void start() {
    JettyServer jettyServer = new JettyServer(new JettyServerConfig());
    jettyServer.start();
  }
}
