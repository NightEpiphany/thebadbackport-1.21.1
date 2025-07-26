package com.moigferdsrte.pale_forest.api.optimize.arisu_cleaner;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import static com.moigferdsrte.pale_forest.api.optimize.arisu_cleaner.ClearItemsTask.DEFAULT_CLEAR_TICKS;
import static com.moigferdsrte.pale_forest.api.optimize.arisu_cleaner.ClearItemsTask.DEFAULT_TIPS_TICKS;
import static com.mojang.text2speech.Narrator.LOGGER;

public class TaskManager {

    public static final TaskManager INSTANCE = new TaskManager();

    private final ClearItemsTask clearItemsTask = new ClearItemsTask(DEFAULT_CLEAR_TICKS, DEFAULT_TIPS_TICKS);
    public static boolean isClearItemsTaskRegistered = false;

    public void startClearItemsTask(int clearPerTicks, int tipsTicks) {
        clearItemsTask.setClearTicks(clearPerTicks);
        clearItemsTask.setTipsTicks(tipsTicks);
        if (!isClearItemsTaskRegistered) {
            ServerTickEvents.END_SERVER_TICK.register(clearItemsTask::execute);
            isClearItemsTaskRegistered = true;
            LOGGER.info("ClearItemsTask is registered");
        }
    }
}