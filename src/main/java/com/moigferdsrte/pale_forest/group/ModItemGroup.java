package com.moigferdsrte.pale_forest.group;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.block.ModBlocks;
import com.moigferdsrte.pale_forest.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup TAB1 = Registry.register(Registries.ITEM_GROUP, Identifier.of(ThePaleForest.MOD_ID,"original"),
            ItemGroup.create(null,-1).displayName(Text.translatable("itemGroup.pale_forest.item_tab"))
                    .icon(()->new ItemStack(ModBlocks.RESIN_CLUMP))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModItems.RESIN_CLUMP);
                        entries.add(ModItems.RESIN_BRICK);
                        entries.add(ModBlocks.RESIN_BLOCK);
                        entries.add(ModBlocks.RESIN_BRICKS);
                        entries.add(ModBlocks.RESIN_BRICKS_SLAB);
                        entries.add(ModBlocks.RESIN_BRICKS_STAIRS);
                        entries.add(ModBlocks.RESIN_BRICKS_WALL);
                        entries.add(ModBlocks.CHISELED_RESIN_BRICKS);
                        entries.add(ModBlocks.PALE_OAK_LOG);
                        entries.add(ModBlocks.STRIPPED_PALE_OAK_LOG);
                        entries.add(ModBlocks.PALE_OAK_WOOD);
                        entries.add(ModBlocks.STRIPPED_PALE_OAK_WOOD);
                        entries.add(ModBlocks.PALE_OAK_PLANKS);
                        entries.add(ModBlocks.PALE_OAK_LEAVES);
                        entries.add(ModBlocks.PALE_OAK_SLAB);
                        entries.add(ModBlocks.PALE_OAK_STAIRS);
                        entries.add(ModBlocks.PALE_OAK_PRESSURE_PLATE);
                        entries.add(ModBlocks.PALE_OAK_BUTTON);
                        entries.add(ModBlocks.PALE_OAK_FENCE);
                        entries.add(ModBlocks.PALE_OAK_FENCE_GATE);
                        entries.add(ModBlocks.PALE_OAK_DOOR);
                        entries.add(ModBlocks.PALE_OAK_TRAPDOOR);
                        if (FabricLoader.getInstance().isModLoaded("friendsandfoes")) {
                            entries.add(ModBlocks.PALE_OAK_BEEHIVE);
                            entries.add(ModBlocks.PALE_OAK_CAMPFIRE);
                            entries.add(ModBlocks.PALE_OAK_SOUL_CAMPFIRE);
                        }
                        if (FabricLoader.getInstance().isModLoaded("nemos-mossy-blocks")) {
                            entries.add(ModBlocks.MOSSY_PALE_OAK_LOG);
                            entries.add(ModBlocks.MOSSY_PALE_OAK_WOOD);
                            entries.add(ModBlocks.MOSSY_PALE_OAK_PLANKS);
                            entries.add(ModBlocks.MOSSY_PALE_OAK_SLAB);
                            entries.add(ModBlocks.MOSSY_PALE_OAK_STAIRS);
                            entries.add(ModBlocks.MOSSY_PALE_OAK_PRESSURE_PLATE);
                            entries.add(ModBlocks.MOSSY_PALE_OAK_BUTTON);
                            entries.add(ModBlocks.MOSSY_PALE_OAK_FENCE);
                            entries.add(ModBlocks.MOSSY_PALE_OAK_FENCE_GATE);
                            entries.add(ModBlocks.MOSSY_PALE_OAK_DOOR);
                            entries.add(ModBlocks.MOSSY_PALE_OAK_TRAPDOOR);
                        }
                        entries.add(ModItems.PALE_OAK_SIGN);
                        entries.add(ModItems.PALE_OAK_HANGING_SIGN);
                        entries.add(ModItems.PALE_OAK_BOAT);
                        entries.add(ModItems.PALE_OAK_CHEST_BOAT);
                        entries.add(ModBlocks.PALE_OAK_SAPLING);
                        entries.add(ModItems.PALE_OAK_STICK);
                        entries.add(ModBlocks.PALE_IVY);
                        entries.add(ModBlocks.PALE_IVY_PLANT);
                        entries.add(ModBlocks.PALE_GRASS);
                        entries.add(ModBlocks.PALE_TALL_GRASS);
                        entries.add(ModBlocks.PALE_TWIST_FERN);
                        entries.add(ModBlocks.PALE_MOSS_BLOCK);
                        entries.add(ModBlocks.PALE_MOSS_CARPET);
                        entries.add(ModBlocks.OPEN_EYEBLOSSOM);
                        entries.add(ModBlocks.CLOSED_EYEBLOSSOM);
                        entries.add(ModItems.PALE_AXE);
                        entries.add(ModItems.PALE_SHOVEL);
                        entries.add(ModItems.PALE_SWORD);
                        entries.add(ModItems.PALE_PICKAXE);
                        entries.add(ModItems.PALE_HOE);
                        entries.add(ModItems.CONJUGATE_APPLE);
                        entries.add(ModItems.CREAKING_SPAWN_EGG);
                        entries.add(ModBlocks.CREAKING_HEART);
                        entries.add(ModBlocks.WILDFLOWER);
                        entries.add(ModBlocks.LEAF_LITTER);
                        entries.add(ModBlocks.FIREFLY_BUSH);
                        entries.add(ModItems.BUSH);
                        entries.add(ModBlocks.SHORT_DRY_GRASS);
                        entries.add(ModBlocks.TALL_DRY_GRASS);
                        entries.add(ModBlocks.CACTUS_FLOWER);
                        entries.add(Items.EGG);
                        entries.add(ModItems.BLUE_EGG);
                        entries.add(ModItems.BROWN_EGG);
                        entries.add(ModBlocks.DRIED_GHAST);
                        entries.add(ModItems.HAPPY_GHAST_SPAWN_EGG);
                        entries.add(ModItems.WHITE_HARNESS);
                        entries.add(ModItems.RED_HARNESS);
                        entries.add(ModItems.PURPLE_HARNESS);
                        entries.add(ModItems.PINK_HARNESS);
                        entries.add(ModItems.MAGENTA_HARNESS);
                        entries.add(ModItems.LIME_HARNESS);
                        entries.add(ModItems.LIGHT_BLUE_HARNESS);
                        entries.add(ModItems.CYAN_HARNESS);
                        entries.add(ModItems.GREEN_HARNESS);
                        entries.add(ModItems.BLUE_HARNESS);
                        entries.add(ModItems.BLACK_HARNESS);
                        entries.add(ModItems.LIGHT_GRAY_HARNESS);
                        entries.add(ModItems.GRAY_HARNESS);
                        entries.add(ModItems.BROWN_HARNESS);
                        entries.add(ModItems.ORANGE_HARNESS);
                        entries.add(ModItems.YELLOW_HARNESS);
                        entries.add(ModItems.MUSIC_DISC_TEARS);
                        entries.add(ModItems.MUSIC_DISC_LAVA_CHICKEN);
                    })).build());

    public static final ItemGroup TAB2 = Registry.register(Registries.ITEM_GROUP, Identifier.of(ThePaleForest.MOD_ID,"another"),
            ItemGroup.create(null,-1).displayName(Text.translatable("itemGroup.pale_forest.item_tab"))
                    .icon(()->new ItemStack(ModBlocks.COPPER_GOLEM_STATUE))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModItems.COPPER_HORSE_ARMOR);
                        entries.add(ModBlocks.COPPER_GOLEM_STATUE);
                        entries.add(ModBlocks.EXPOSED_COPPER_GOLEM_STATUE);
                        entries.add(ModBlocks.WEATHERED_COPPER_GOLEM_STATUE);
                        entries.add(ModBlocks.OXIDIZED_COPPER_GOLEM_STATUE);
                        entries.add(ModBlocks.WAXED_COPPER_GOLEM_STATUE);
                        entries.add(ModBlocks.WAXED_EXPOSED_COPPER_GOLEM_STATUE);
                        entries.add(ModBlocks.WAXED_WEATHERED_COPPER_GOLEM_STATUE);
                        entries.add(ModBlocks.WAXED_OXIDIZED_COPPER_GOLEM_STATUE);
                        entries.add(ModBlocks.OAK_SHELF);
                        entries.add(ModBlocks.ACACIA_SHELF);
                        entries.add(ModBlocks.DARK_OAK_SHELF);
                        entries.add(ModBlocks.PALE_OAK_SHELF);
                        entries.add(ModBlocks.BIRCH_SHELF);
                        entries.add(ModBlocks.JUNGLE_SHELF);
                        entries.add(ModBlocks.CHERRY_SHELF);
                        entries.add(ModBlocks.BAMBOO_SHELF);
                        entries.add(ModBlocks.SPRUCE_SHELF);
                        entries.add(ModBlocks.MANGROVE_SHELF);
                        entries.add(ModBlocks.CRIMSON_SHELF);
                        entries.add(ModBlocks.WARPED_SHELF);

                    })).build());

    private static RegistryKey<net.minecraft.item.ItemGroup> register(String id) {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of( ThePaleForest.MOD_ID, id));
    }

    private static void registerAdvance(FabricItemGroupEntries entries) {
        entries.add(ModBlocks.TEST_ACCEPT);
        entries.add(ModBlocks.TEST_FAIL);
        entries.add(ModBlocks.TEST_START);
        entries.add(ModBlocks.TEST_LOG);
        entries.add(ModBlocks.TEST_INSTANCE);
    }

    private static void registerMissingEggs(FabricItemGroupEntries entries) {
        entries.add(Items.ENDER_DRAGON_SPAWN_EGG);
        entries.add(Items.WITHER_SPAWN_EGG);
    }

    private static void registerMissingEggs2(FabricItemGroupEntries entries) {
        entries.addAfter(Items.COW_SPAWN_EGG, ModItems.CREAKING_SPAWN_EGG);
        entries.addBefore(Items.HOGLIN_SPAWN_EGG, ModItems.HAPPY_GHAST_SPAWN_EGG);
        entries.addAfter(Items.SHEEP_SPAWN_EGG, ModItems.JEB_SHEEP_SPAWN_EGG);
        entries.addAfter(Items.MOOSHROOM_SPAWN_EGG, ModItems.BROWN_MOOSHROOM_SPAWN_EGG);
        entries.addAfter(Items.CREEPER_SPAWN_EGG, ModItems.CHARGED_CREEPER_SPAWN_EGG);
        entries.addAfter(Items.GOAT_SPAWN_EGG, ModItems.SCREAMING_GOAT_SPAWN_EGG);
        entries.addAfter(Items.PANDA_SPAWN_EGG, ModItems.BROWN_PANDA_SPAWN_EGG);
        entries.addBefore(Items.FOX_SPAWN_EGG, ModItems.SNOW_FOX_SPAWN_EGG);
        entries.addAfter(Items.RABBIT_SPAWN_EGG, ModItems.TOAST_RABBIT_SPAWN_EGG);
        entries.addAfter(Items.SNOW_GOLEM_SPAWN_EGG, ModItems.SHEARED_SNOW_GOLEM_SPAWN_EGG);
        entries.addAfter(Items.BOGGED_SPAWN_EGG, ModItems.SHEARED_BOGGED_SPAWN_EGG);
    }

    private static void registerDeco(FabricItemGroupEntries entries) {
        entries.add(ModItems.WHITE_HARNESS);
        entries.add(ModItems.RED_HARNESS);
        entries.add(ModItems.PURPLE_HARNESS);
        entries.add(ModItems.PINK_HARNESS);
        entries.add(ModItems.MAGENTA_HARNESS);
        entries.add(ModItems.LIME_HARNESS);
        entries.add(ModItems.LIGHT_BLUE_HARNESS);
        entries.add(ModItems.CYAN_HARNESS);
        entries.add(ModItems.GREEN_HARNESS);
        entries.add(ModItems.BLUE_HARNESS);
        entries.add(ModItems.BLACK_HARNESS);
        entries.add(ModItems.LIGHT_GRAY_HARNESS);
        entries.add(ModItems.GRAY_HARNESS);
        entries.add(ModItems.BROWN_HARNESS);
        entries.add(ModItems.ORANGE_HARNESS);
        entries.add(ModItems.YELLOW_HARNESS);
        entries.add(ModBlocks.OAK_SHELF);
        entries.add(ModBlocks.ACACIA_SHELF);
        entries.add(ModBlocks.DARK_OAK_SHELF);
        entries.add(ModBlocks.PALE_OAK_SHELF);
        entries.add(ModBlocks.BIRCH_SHELF);
        entries.add(ModBlocks.JUNGLE_SHELF);
        entries.add(ModBlocks.CHERRY_SHELF);
        entries.add(ModBlocks.BAMBOO_SHELF);
        entries.add(ModBlocks.SPRUCE_SHELF);
        entries.add(ModBlocks.MANGROVE_SHELF);
        entries.add(ModBlocks.CRIMSON_SHELF);
        entries.add(ModBlocks.WARPED_SHELF);
    }


    public static void registerItemGroup() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR).register(ModItemGroup::registerAdvance);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR).register(ModItemGroup::registerMissingEggs);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(ModItemGroup::registerMissingEggs2);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(ModItemGroup::registerDeco);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(e -> {
            e.addAfter(Items.MUSIC_DISC_PIGSTEP, ModItems.MUSIC_DISC_TEARS);
            e.addAfter(Items.MUSIC_DISC_PIGSTEP, ModItems.MUSIC_DISC_LAVA_CHICKEN);
        });
        ThePaleForest.LOGGER.info("Registering Item Groups");
    }
}
