package org.brewtraption.websocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.Session;

public class SocketSessionHandler {

  private final Set<Session> sessions = new HashSet<>();

  private static class SocketSessionHandlerholder {
    public static final SocketSessionHandler INSTANCE = new SocketSessionHandler();
  }

  public static SocketSessionHandler getInstance() {
    return SocketSessionHandlerholder.INSTANCE;
  }

  public void addSession(final Session session) {
    sessions.add(session);
  }

  public void removeSession(final Session session) {
    sessions.remove(session);
  }

  public void sendToAllConnectedSessions(final String message) {
    for (Session session : sessions) {
      sendToSession(session, message);
    }
  }

  private void sendToSession(final Session session, final String message) {
    try {
      session.getBasicRemote().sendText(message);
    } catch (IOException ex) {
      sessions.remove(session);
    }
  }

  public boolean hasConnectedSessions() {
    return sessions.size() > 0;
  }
}
