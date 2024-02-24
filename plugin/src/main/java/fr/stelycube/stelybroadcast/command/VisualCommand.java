package fr.stelycube.stelybroadcast.command;

import org.jetbrains.annotations.NotNull;

import fr.stelycube.stelybroadcast.config.MessageConfig;
import fr.stelycube.stelybroadcast.task.TaskLoader;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class VisualCommand extends Command {

    private final static String NAME = "stelybroadcastvisual";
    private final static String PERMISSION = "stelybroadcast.command.visual";
    private final static String[] ALIASES = new String[] { "sbvs" };

    private final Plugin plugin;

    public VisualCommand(@NotNull Plugin plugin, @NotNull MessageConfig messageConfig) {
        super(NAME, PERMISSION, ALIASES);
        this.plugin = plugin;
        setPermissionMessage(messageConfig.getMessage("command.dont-have-permission"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final TaskLoader taskLoader = new TaskLoader();
        final var tasksData = taskLoader.load(plugin);
        for (var taskData : tasksData) {
            sender.sendMessage(taskData.message().message());
        }
    }

}
