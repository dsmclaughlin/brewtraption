package org.brewtraption.websocket;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ClientEndpoint
@ServerEndpoint(value = "/ws/")
public class EventSocket {

  @OnOpen
  public void onWebSocketConnect(final Session sess) {
    System.out.println("Socket Connected: " + sess);
  }

  @OnMessage
  public void onWebSocketText(final String message) {
    System.out.println("Received TEXT message: " + message);
  }

  @OnClose
  public void onWebSocketClose(final CloseReason reason) {
    System.out.println("Socket Closed: " + reason);
  }

  @OnError
  public void onWebSocketError(final Throwable cause) {
    cause.printStackTrace(System.err);
  }
}
