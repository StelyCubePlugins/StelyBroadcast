package fr.stelycube.stelybroadcast.config;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;

import fr.stelycube.stelybroadcast.task.MessageData;
import fr.stelycube.stelybroadcast.task.TaskData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Config {

    private Configuration configuration;

    public void load(@NotNull ConfigLoader configLoader, @NotNull Plugin plugin, @NotNull String fileName) {
        final File configFile = configLoader.initFile(plugin.getDataFolder(), Objects.requireNonNull(plugin.getResourceAsStream(fileName)), fileName);
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public List<TaskData> loadTasksData(@NotNull Logger logger, @NotNull Map<String, BaseComponent[]> messageRegistry) {
        final List<TaskData> tasksData = new LinkedList<>();
        for (String key : configuration.getKeys()) {
            final Optional<TaskData> taskData = loadTaskData(key, logger, messageRegistry);
            if(taskData.isEmpty()) {
                continue;
            }
            tasksData.add(taskData.get());
        }
        return tasksData;
    }

    @NotNull
    private Optional<TaskData> loadTaskData(@NotNull String key, @NotNull Logger logger, @NotNull Map<String, BaseComponent[]> messageRegistry) {
        final Configuration taskSection = configuration.getSection(key);
        if (taskSection == null) {
            logger.warning("'" + key + "' is not a section");
            return Optional.empty();
        }
        final int delay = Math.max(0, taskSection.getInt("delay", 0));
        final int timer = Math.max(1, taskSection.getInt("timer", 20*60));
        final String[] servers = taskSection.getStringList("servers").toArray(new String[0]);
        BaseComponent[] message = null;
        final String messageId = taskSection.getString("message-id", null);
        if (messageId != null) {
            message = messageRegistry.get(messageId);
            if(message == null) {
                logger.warning("'" + key + "' message-id does not exist in message-registry.lang");
            }
        }
        final String rawMessage = taskSection.getString("message", null);
        if (rawMessage != null) {
            message = new BaseComponent[]{TextComponent.fromLegacy(ChatColor.translateAlternateColorCodes('&', rawMessage))};
        }
        if(message == null) {
            logger.warning("'" + key + "' does not provide any message");
            return Optional.empty();
        }
        return Optional.of(new TaskData(delay, timer, new MessageData(servers, message)));
    }

}

