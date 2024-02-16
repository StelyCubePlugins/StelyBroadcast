package fr.stelycube.stelybroadcast.task;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.NotNull;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.TaskScheduler;

public class TaskInitializer {

    public void start(@NotNull Plugin plugin, @NotNull List<TaskData> tasksData) {
        final TaskScheduler scheduler = plugin.getProxy().getScheduler();
        for (final TaskData taskData : tasksData) {
            final MessageData messageData = taskData.message();
            final Runnable sendTask = messageData.servers().length == 0 ? new GlobalSendTask(messageData.message()) : new SpecificSendTask(messageData);
            scheduler.schedule(plugin, sendTask, taskData.delay(), taskData.timer(), TimeUnit.SECONDS);
        }
    }

}
