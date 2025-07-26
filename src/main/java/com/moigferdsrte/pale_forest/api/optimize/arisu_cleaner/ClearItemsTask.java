package com.moigferdsrte.pale_forest.api.optimize.arisu_cleaner;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;

import static com.moigferdsrte.pale_forest.ThePaleForest.ARISU_STYLE;


public class ClearItemsTask {
    private int elapsedTicks = 0;
    private int tipsTicks = DEFAULT_TIPS_TICKS;
    private int clearTicks = DEFAULT_CLEAR_TICKS;
    private boolean isTipSent = false;

    public final static int DEFAULT_TIPS_TICKS = 20 * 30;
    public final static int DEFAULT_CLEAR_TICKS = 20 * 60 * 10;

    public ClearItemsTask(int tipsTicks, int clearTicks) {
        setTipsTicks(tipsTicks);
        setClearTicks(clearTicks);
    }

    public void setClearTicks(int clearTicks) {
        if (clearTicks < 1) {
            throw new IllegalArgumentException("Coefficient clearTicks must be positive integer");
        }
        this.clearTicks = clearTicks;
    }

    public void setTipsTicks(int tipsTicks) {
        if (tipsTicks < 1) {
            throw new IllegalArgumentException("Coefficient tipsTicks must be positive integer");
        }
        this.tipsTicks = tipsTicks;
    }

    public void execute(MinecraftServer server) {
        elapsedTicks += 1;

        if (elapsedTicks >= MathHelper.clamp(clearTicks - tipsTicks, 1, Integer.MAX_VALUE) && (!isTipSent)) {
            isTipSent = true;
            server.getRateLimit();
            float tipsSeconds = (float) (tipsTicks) / server.getTickManager().getTickRate();
            server.getPlayerManager().broadcast(
                    Text.literal("[ArisuCleaner] %.2f seconds till all item entity will be cleared!".formatted(tipsSeconds))
                            .setStyle(ARISU_STYLE.withBold(true)),
                    false);
        }

        if (elapsedTicks >= clearTicks) {
            var itemEntitiesInAllWorlds = new ArrayList<ItemEntity>();
            server.getWorlds().forEach(world -> {
                var itemEntities = world.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), itemEntity -> true);
                itemEntitiesInAllWorlds.addAll(itemEntities);
            });
            int count = itemEntitiesInAllWorlds.size();
            if (count > 0) {
                // If 1.21.5
//                for (ItemEntity item : itemEntitiesInAllWorlds) {
//                    item.kill(null);
//                }
                // If 1.20.1
                itemEntitiesInAllWorlds.forEach(Entity::kill);
            }
            server.getPlayerManager().broadcast(
                    Text.literal("[ArisuCleaner] %d item entity cleared!".formatted(count))
                            .setStyle(ARISU_STYLE.withBold(true)),
                    false);
            isTipSent = false;
            elapsedTicks = 0;
        }
    }
}
