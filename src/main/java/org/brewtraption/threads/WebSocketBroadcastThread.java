package org.brewtraption.threads;

import org.brewtraption.control.BreweryController;
import org.brewtraption.dto.HltDTO;
import org.brewtraption.util.BrewProps;
import org.brewtraption.util.Constants;
import org.brewtraption.websocket.SocketSessionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketBroadcastThread extends Thread {

  private static Logger logger = LoggerFactory.getLogger(WebSocketBroadcastThread.class);

  public WebSocketBroadcastThread(String str) {
    super(str);
  }

  private static HltDTO last = null;

  public void run() {
    waitForSocket();

    while (true) {
      Double currentTemp = readFIle();
      BrewProps.writeValue(Constants.HLT_CURRENT_TEMP, currentTemp.toString());

      HltDTO hltDTO = BreweryController.getHTLInfo();

      SocketSessionHandler handler = SocketSessionHandler.getInstance();
      //TODO also send when new client connects
      if (handler.hasConnectedSessions()) {
        if (!hltDTO.equals(last)) {
          handler.sendToAllConnectedSessions(hltDTO);
          last = hltDTO;
        }
      } else {
        waitForSocket();
      }

      sleep();
    }
  }

  private Double readFIle() {
    return BrewProps.lookupDouble(Constants.HLT_CURRENT_TEMP);
  }

  private void waitForSocket() {
    boolean waitLonger = true;

    logger.info("Waiting for socket connection");

    while(waitLonger) {
      SocketSessionHandler handler = SocketSessionHandler.getInstance();
      waitLonger = !handler.hasConnectedSessions();

      if(!waitLonger) {
        logger.info("A wild web socket client appears!");
        waitLonger = false;
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
