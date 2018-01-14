package org.brewtraption.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.brewtraption.dto.HltDTO;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.Session;

public class SocketSessionHandler {
  ObjectMapper mapper = new ObjectMapper();

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

  public void sendToAllConnectedSessions(final HltDTO hltDTO) {
    for (Session session : sessions) {
      sendToSession(session, hltDTO);
    }
  }

  private void sendToSession(final Session session, final HltDTO hltDTO) {
    try {
      session.getBasicRemote().sendText(mapper.writeValueAsString(hltDTO));
    } catch (IOException ex) {
      sessions.remove(session);
    }
  }

  public boolean hasConnectedSessions() {
    return sessions.size() > 0;
  }
}
