package fr.stelycube.stelybroadcast.task;

import org.jetbrains.annotations.NotNull;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class GlobalSendTask implements Runnable {

    private final BaseComponent[] message;

    public GlobalSendTask(@NotNull BaseComponent[] message) {
        this.message = message;
    }

    @Override
    public void run() {
        final ProxyServer proxyServer = ProxyServer.getInstance();
         for (ServerInfo serverInfo : proxyServer.getServers().values()) {
            for (ProxiedPlayer player : serverInfo.getPlayers()) {
                player.sendMessage(message);
            }
        }    
    }

}

