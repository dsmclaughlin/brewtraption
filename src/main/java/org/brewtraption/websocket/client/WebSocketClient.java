package org.brewtraption.websocket.client;

import org.brewtraption.websocket.EventSocket;
import org.eclipse.jetty.util.component.LifeCycle;

import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class WebSocketClient {

  public static int lastNumber = 0;

  public static void main(final String[] args) {
    URI uri = URI.create("ws://192.168.0.101:8083/ws/");

    try {
      WebSocketContainer container = ContainerProvider.getWebSocketContainer();

      try {
        Session session = container.connectToServer(EventSocket.class, uri);
        session.getBasicRemote().sendText("Hello");

        while (true) {
          Thread.sleep(200);

          if (lastNumber > 99) {
            System.out.println("Breaking out!");
            break;
          }
        }
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
