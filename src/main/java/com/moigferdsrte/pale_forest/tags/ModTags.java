package com.moigferdsrte.pale_forest.tags;

import com.moigferdsrte.pale_forest.ThePaleForest;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {

    public static final TagKey<Block> EYEBLOSSOM=of("eyeblossom");

    public static final TagKey<Item> SPAWN_EGG=ofItem("spawn_egg");

    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(ThePaleForest.MOD_ID,id));
    }

    private static TagKey<Item> ofItem(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(ThePaleForest.MOD_ID,id));
    }

    public static void register(){

    }
}
