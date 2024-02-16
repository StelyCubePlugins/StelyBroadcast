package fr.stelycube.stelybroadcast.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.chat.ComponentSerializer;

public class MessageRegistryLoader {

    private File file;

    public void load(@NotNull ConfigLoader configLoader, @NotNull Plugin plugin, @NotNull String fileName) {
        file = configLoader.initFile(plugin.getDataFolder(), Objects.requireNonNull(plugin.getResourceAsStream(fileName)), fileName);
    }

    @NotNull
    public Map<String, BaseComponent[]> getMessages(@NotNull Logger logger) {
        final List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            logger.log(Level.WARNING, "Can not read the message registry", e);
            return Collections.emptyMap();
        }
        final Map<String, BaseComponent[]> registry = new HashMap<>();
        for (String line : lines) {
            line = line.trim();
            if (line.length() == 0 || line.charAt(0) == '#') {
                continue;
            }
            final String[] parts = line.split("=", 2);
            if (parts.length != 2) {
                logger.warning("Invalid line in message registry : '" + line + "'");
                continue;
            }
            final String id = parts[0];
            final String rawComponent = parts[1];
            final BaseComponent[] components = ComponentSerializer.parse(rawComponent);
            registry.put(id, components);
        }
        return registry;
    }

}

