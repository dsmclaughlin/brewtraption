package org.brewtraption.main;

import org.brewtraption.server.JettyServer;
import org.brewtraption.server.JettyServerConfig;
import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

  static Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(final String[] args) throws IOException {
    Main main = new Main();
    initaliseRuntimePropsFile();
    main.start();
  }

  private static void initaliseRuntimePropsFile() throws IOException {
    Path source = Paths.get(Constants.INITIAL_PROPS_FILE);
    Path destination = Paths.get(buildTempFilePath());

    Files.copy(source, destination, StandardCopyOption.COPY_ATTRIBUTES);
    BrewProps.initialize(destination.toString());
    String message = "Initialised runtime brewtraption props file to %s.";
    logger.info(String.format(message, destination.toString()));
  }

  private static String buildTempFilePath() {
    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    String createdTime = dateFormat.format(date);
    return System.getProperty("java.io.tmpdir") + "brewtraptionProps-" + createdTime + ".tmp";
  }

  private void start() {
    JettyServer jettyServer = new JettyServer(new JettyServerConfig());
    jettyServer.start();
  }
}
