package org.brewtraption.websocket;

import org.brewtraption.threads.CurrentTempUpdateThread;
import org.slf4j.LoggerFactory;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.sun.media.jfxmedia.logging.Logger;

@ClientEndpoint
@ServerEndpoint(value = "/ws/")
public class EventSocket {

  private static org.slf4j.Logger logger = LoggerFactory.getLogger(EventSocket.class);

  @OnOpen
  public void onWebSocketConnect(final Session sess) {
    logger.info("A wild WebSocket Client Appears: " + sess.getId());
    SocketSessionHandler.getInstance().addSession(sess);
  }

  @OnMessage
  public void onWebSocketText(final String message) {
    if (message.equals("Hello")) {
      System.out.println("Received TEXT message: " + message);
    } else {
      System.out.println("Received TEXT message: " + message);
    }
  }

  @OnClose
  public void onWebSocketClose(final Session sess, CloseReason reason) {
    logger.info("WebSocket Client Disconnected: " + sess.getId() + " " + reason);
    SocketSessionHandler.getInstance().removeSession(sess);
  }

  @OnError
  public void onWebSocketError(final Throwable cause) {
    cause.printStackTrace(System.err);
  }
}
