package fr.stelycube.stelybroadcast.task;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;

import fr.stelycube.stelybroadcast.config.Config;
import fr.stelycube.stelybroadcast.config.ConfigLoader;
import fr.stelycube.stelybroadcast.config.MessageRegistryLoader;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.plugin.Plugin;

public class TaskLoader {

    @NotNull
    public List<TaskData> load(@NotNull Plugin plugin) {
        final Logger logger = plugin.getLogger();
        final File dataFolder = plugin.getDataFolder();
        final ConfigLoader configLoader = new ConfigLoader();

        if (!configLoader.checkDataFolder(dataFolder)) {
            logger.log(Level.WARNING, "Can not write in the directory : " + dataFolder.getAbsolutePath());
            logger.log(Level.WARNING, "Disable plugin functionnalities");
            return Collections.emptyList();
        }

        final MessageRegistryLoader messageRegistryLoader = new MessageRegistryLoader();
        messageRegistryLoader.load(configLoader, plugin, "message-registry.lang");
        final Map<String, BaseComponent[]> messageRegistry = messageRegistryLoader.getMessages(logger);
        final Config config = new Config();
        config.load(configLoader, plugin, "config.yml");
        return config.loadTasksData(logger, messageRegistry);
    }

}

