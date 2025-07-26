package com.moigferdsrte.pale_forest.api.optimize.arisu_cleaner;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

import static com.moigferdsrte.pale_forest.ThePaleForest.ARISU_STYLE;


public class ClearItemsCommand {

    public static int executeWithOneArg(CommandContext<ServerCommandSource> context) {
        Integer clearTicks = context.getArgument("clearTicks", Integer.class);
        if (clearTicks == null || clearTicks < 1) {
            context.getSource().sendFeedback(() ->
                    Text.literal("[ArisuCleaner] Coefficient clearTicks must be positive integer")
                            .withColor(Colors.RED), false);
            return 0;
        }
        var tipsTicks = (int) (clearTicks * 0.1); // Default to 10% * clearTicks
        execute(clearTicks, tipsTicks, context);
        return 1;
    }

    public static int executeWithTwoArg(CommandContext<ServerCommandSource> context) {
        Integer clearTicks = context.getArgument("clearTicks", Integer.class);
        Integer tipsTicks = context.getArgument("tipsTicks", Integer.class);
        if (tipsTicks == null || tipsTicks < 1) {
            context.getSource().sendFeedback(() ->
                    Text.literal("[ArisuCleaner] Coefficient tipsTicks must be positive integer")
                            .withColor(Colors.RED), false);
            return 0;
        }
        if (clearTicks == null || clearTicks < 1) {
            context.getSource().sendFeedback(() ->
                    Text.literal("[ArisuCleaner] Coefficient clearTicks must be positive integer")
                            .withColor(Colors.RED), false);
            return 0;
        }
        if (tipsTicks > clearTicks) {
            context.getSource().sendFeedback(() ->
                    Text.literal("[ArisuCleaner] Coefficient tipsTicks must be less than or equal to clearTicks")
                            .withColor(Colors.RED), false);
            return 0;
        }
        execute(clearTicks, tipsTicks, context);
        return 1;
    }

    private static void execute(int clearPerTicks, int tipsTicks, CommandContext<ServerCommandSource> context) {
        TaskManager.INSTANCE.startClearItemsTask(clearPerTicks, tipsTicks);
        context.getSource().sendFeedback(() ->
                Text.literal("[ArisuCleaner] Clear task execute per %d Ticks, Notify per %d Ticks."
                                .formatted(clearPerTicks, tipsTicks))
                        .setStyle(ARISU_STYLE), true);
    }
}
