package com.moigferdsrte.pale_forest.world.tree;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.world.ModConfiguredFeatures;
import net.minecraft.block.SaplingGenerator;

import java.util.Optional;

public class ModTreeGenerator {
    public static final SaplingGenerator PALE_OAK_TREE = new SaplingGenerator(
            ThePaleForest.MOD_ID + ":pale_oak_tree",
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.PALE_OAK_TREE_KEY),
            Optional.empty()
    );
}
