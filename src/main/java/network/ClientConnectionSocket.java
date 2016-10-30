package network;

import com.google.gson.JsonObject;
import main.MasterServer;
import network.handlers.PacketHandlerAuth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.jetbrains.annotations.NotNull;
import protocol.CommandAuth;
import protocol.CommandLeaderBoard;
import protocol.CommandResetLevel;
import protocol.CommandUpdateCells;
import utils.JSONHelper;

import java.util.Arrays;

public class ClientConnectionSocket extends WebSocketAdapter {
  private final static @NotNull Logger log = LogManager.getLogger(ClientConnectionSocket.class);

  @Override
  public void onWebSocketConnect(@NotNull Session sess) {
    super.onWebSocketConnect(sess);
    log.info("Socket connected: " + sess);
  }

  @Override
  public void onWebSocketText(@NotNull String message) {
    super.onWebSocketText(message);
    if (getSession().isOpen()) {
      handlePacket(message);
    }
    log.info("Received packet: " + message);
  }

  @Override
  public void onWebSocketClose(int statusCode, @NotNull String reason) {
    super.onWebSocketClose(statusCode, reason);
    log.info("Socket closed: [" + statusCode + "] " + reason);
  }

  @Override
  public void onWebSocketError(@NotNull Throwable cause) {
    super.onWebSocketError(cause);
    cause.printStackTrace(System.err);
  }

  public void handlePacket(@NotNull String msg) {
    JsonObject json = JSONHelper.getJSONObject(msg);
    String name = json.get("command").getAsString();
    switch (name) {
      case CommandAuth.NAME:
        new PacketHandlerAuth(getSession(), msg);
        break;
    }
  }
}
