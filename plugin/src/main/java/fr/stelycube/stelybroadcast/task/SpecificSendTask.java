package fr.stelycube.stelybroadcast.task;

import org.jetbrains.annotations.NotNull;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SpecificSendTask implements Runnable {

    private final MessageData message;

    public SpecificSendTask(@NotNull MessageData message) {
        this.message = message;
    }

    @Override
    public void run() {
        final ProxyServer proxyServer = ProxyServer.getInstance();
         for (String server : message.servers()) {
            final ServerInfo serverInfo = proxyServer.getServerInfo(server);
            if (serverInfo == null) {
                continue;
            }
            for (ProxiedPlayer player : serverInfo.getPlayers()) {
                player.sendMessage(message.message());
            }
        }    
    }

}

