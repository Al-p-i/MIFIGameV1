package network.handlers;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.Player;
import network.packets.PacketAuthFail;
import network.packets.PacketAuthOk;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandAuth;
import utils.JSONDeserializationException;
import utils.JSONHelper;

import java.io.IOException;

public class PacketHandlerAuth {
  public PacketHandlerAuth(@NotNull Session session, @NotNull String json) {
    CommandAuth commandAuth;
    try {
      commandAuth = JSONHelper.fromJSON(json, CommandAuth.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }
    if (!accountserver.api.Authentication.validateToken(commandAuth.getToken())) {
      try {
        new PacketAuthFail(commandAuth.getLogin(), commandAuth.getToken(), "Invalid user or password").write(session);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        new PacketAuthOk().write(session);
        Player player = new Player(commandAuth.getLogin());
        ApplicationContext.get().get(MatchMaker.class).joinGame(player);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
