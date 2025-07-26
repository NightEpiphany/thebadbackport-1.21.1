package com.moigferdsrte.pale_forest.world.biome;

import com.moigferdsrte.pale_forest.ThePaleForest;
import net.minecraft.client.util.RawTextureDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.io.IOException;

public class DeadwoodColormapResourceSupplier extends SinglePreparationResourceReloader<int[]> {

    private static final Identifier GRASS_COLORMAP_LOC = Identifier.of(ThePaleForest.MOD_ID, "textures/colormap/dry_foliage.png");
    @Override
    protected int[] prepare(ResourceManager manager, Profiler profiler) {
        try {
            return RawTextureDataLoader.loadRawTextureData(manager, GRASS_COLORMAP_LOC);
        } catch (IOException var4) {
            throw new IllegalStateException("Failed to load grass color texture", var4);
        }
    }

    @Override
    protected void apply(int[] prepared, ResourceManager manager, Profiler profiler) {
        DeadwoodColors.setColorMap(prepared);
    }
}
