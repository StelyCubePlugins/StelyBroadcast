package fr.stelycube.stelybroadcast.command;

import org.jetbrains.annotations.NotNull;

import fr.stelycube.stelybroadcast.config.MessageConfig;
import fr.stelycube.stelybroadcast.task.TaskInitializer;
import fr.stelycube.stelybroadcast.task.TaskLoader;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class ReloadCommand extends Command {

    private final static String NAME = "stelybroadcastreload";
    private final static String PERMISSION = "stelybroadcast.command.reload";
    private final static String[] ALIASES = new String[]{"sbrl"};

    private final Plugin plugin;
    private final BaseComponent successMessage;

    public ReloadCommand(@NotNull Plugin plugin, @NotNull MessageConfig messageConfig) {
        super(NAME, PERMISSION, ALIASES); 
        this.plugin = plugin;
        setPermissionMessage(messageConfig.getMessage("command.dont-have-permission"));
        this.successMessage = messageConfig.getComponentMessage("command.reload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        plugin.getProxy().getScheduler().cancel(plugin);
        final TaskLoader taskLoader = new TaskLoader();
        final TaskInitializer taskInitializer = new TaskInitializer();
        taskInitializer.start(plugin, taskLoader.load(plugin));
        if (successMessage != null) {
            sender.sendMessage(successMessage);
        }
    }

}

