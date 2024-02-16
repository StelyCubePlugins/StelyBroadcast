package fr.stelycube.stelybroadcast.task;

import org.jetbrains.annotations.NotNull;

public record TaskData(int delay, int timer, @NotNull MessageData message) {
}

