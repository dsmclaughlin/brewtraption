package org.brewtraption.websocket.client;

import org.eclipse.jetty.util.component.LifeCycle;
import org.brewtraption.websocket.EventSocket;

import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class WebSocketClient {

  public static void main(final String[] args) {
    URI uri = URI.create("ws://localhost:8080/ws/");

    try {
      WebSocketContainer container = ContainerProvider.getWebSocketContainer();

      try {
        Session session = container.connectToServer(EventSocket.class, uri);
        session.getBasicRemote().sendText("Hello");
        session.close();
      } finally {
        if (container instanceof LifeCycle) {
          ((LifeCycle) container).stop();
        }
      }
    } catch (Throwable t) {
      t.printStackTrace(System.err);
    }
  }
}
