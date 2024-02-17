package fr.stelycube.stelybroadcast.command;

import org.jetbrains.annotations.NotNull;

import fr.stelycube.stelybroadcast.config.MessageConfig;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class CommandLoader {

    public void load(@NotNull Plugin plugin, @NotNull MessageConfig messageConfig) {
        final PluginManager pluginManager = plugin.getProxy().getPluginManager();
        pluginManager.registerCommand(plugin, new ReloadCommand(plugin, messageConfig));
    }

}

