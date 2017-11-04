package org.brewtraption.main;

import org.brewtraption.websocket.SocketSessionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncrementalNumberThread extends Thread {

  static Logger logger = LoggerFactory.getLogger(IncrementalNumberThread.class);

  public IncrementalNumberThread(String str) {
    super(str);
  }

  public void run() {
    waitForSocket();

    for (int i = 0; i <= 100; i++) {
      SocketSessionHandler instance = SocketSessionHandler.getInstance();
      instance.sendToAllConnectedSessions(""+i);
      sleep();
    }

    logger.info(getName() + " is DONE! ");
  }

  private void waitForSocket() {
    boolean waitLonger = true;

    while(waitLonger) {
      logger.info("Waiting for socket connection");
      SocketSessionHandler handler = SocketSessionHandler.getInstance();
      waitLonger = !handler.hasConnectedSessions();

      if(!waitLonger) {
        logger.info("A wild web socket client appears!");
        break;
      }

      sleep();
    }
  }

  private void sleep() {
    try {
      Thread.sleep(1000);
    } catch (Exception e) {
      logger.warn(e.getMessage());
    }
  }
}
