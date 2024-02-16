package fr.stelycube.stelybroadcast;

import java.util.List;

import fr.stelycube.stelybroadcast.task.TaskData;
import fr.stelycube.stelybroadcast.task.TaskInitializer;
import fr.stelycube.stelybroadcast.task.TaskLoader;
import net.md_5.bungee.api.plugin.Plugin;

public class StelyBroadcastPlugin extends Plugin {


    @Override
    public void onEnable() {
        final TaskLoader taskLoader = new TaskLoader();
        final List<TaskData> tasksData = taskLoader.load(this);
        final TaskInitializer taskInitializer = new TaskInitializer();
        taskInitializer.start(this, tasksData);
    }

}

