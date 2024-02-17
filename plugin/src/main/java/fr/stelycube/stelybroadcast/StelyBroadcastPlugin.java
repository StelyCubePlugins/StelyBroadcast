package fr.stelycube.stelybroadcast;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.stelycube.stelybroadcast.command.CommandLoader;
import fr.stelycube.stelybroadcast.config.ConfigLoader;
import fr.stelycube.stelybroadcast.config.MessageConfig;
import fr.stelycube.stelybroadcast.task.TaskData;
import fr.stelycube.stelybroadcast.task.TaskInitializer;
import fr.stelycube.stelybroadcast.task.TaskLoader;
import net.md_5.bungee.api.plugin.Plugin;

public class StelyBroadcastPlugin extends Plugin {


    @Override
    public void onEnable() {
        final Logger logger = getLogger();
        final File dataFolder = getDataFolder();
        final ConfigLoader configLoader = new ConfigLoader();

        if (!configLoader.checkDataFolder(dataFolder)) {
            logger.log(Level.WARNING, "Can not write in the directory : " + dataFolder.getAbsolutePath());
            logger.log(Level.WARNING, "Disable the plugin");
            return;
        }

        final TaskLoader taskLoader = new TaskLoader();
        final List<TaskData> tasksData = taskLoader.load(this);
        final TaskInitializer taskInitializer = new TaskInitializer();
        taskInitializer.start(this, tasksData);

        final MessageConfig messageConfig = new MessageConfig();
        messageConfig.load(configLoader, this, "messages.yml");
        messageConfig.loadPrefix();

        final CommandLoader commandLoader = new CommandLoader();
        commandLoader.load(this, messageConfig);;
    }

    @Override
    public void onDisable() {
        getProxy().getPluginManager().unregisterCommands(this);
        getProxy().getScheduler().cancel(this);
    }

}

