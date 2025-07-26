package com.moigferdsrte.pale_forest.item;

import com.moigferdsrte.pale_forest.block.ModBlocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

public class ModCompostableItems extends ComposterBlock {
    public ModCompostableItems(Settings settings) {
        super(settings);
    }

    public static void registerDefaultCompostableItems() {
        ITEM_TO_LEVEL_INCREASE_CHANCE.defaultReturnValue(-1.0F);

        registerCompostableItem(0.3F, Items.JUNGLE_LEAVES);
        registerCompostableItem(0.3F, Items.OAK_LEAVES);
        registerCompostableItem(0.3F, Items.SPRUCE_LEAVES);
        registerCompostableItem(0.3F, Items.DARK_OAK_LEAVES);
        registerCompostableItem(0.3F, Items.ACACIA_LEAVES);
        registerCompostableItem(0.3F, Items.CHERRY_LEAVES);
        registerCompostableItem(0.3F, Items.BIRCH_LEAVES);
        registerCompostableItem(0.3F, Items.AZALEA_LEAVES);
        registerCompostableItem(0.3F, Items.MANGROVE_LEAVES);
        registerCompostableItem(0.3F, Items.OAK_SAPLING);
        registerCompostableItem(0.3F, Items.SPRUCE_SAPLING);
        registerCompostableItem(0.3F, Items.BIRCH_SAPLING);
        registerCompostableItem(0.3F, Items.JUNGLE_SAPLING);
        registerCompostableItem(0.3F, Items.ACACIA_SAPLING);
        registerCompostableItem(0.3F, Items.CHERRY_SAPLING);
        registerCompostableItem(0.3F, Items.DARK_OAK_SAPLING);
        registerCompostableItem(0.3F, Items.MANGROVE_PROPAGULE);
        registerCompostableItem(0.3F, Items.BEETROOT_SEEDS);
        registerCompostableItem(0.3F, Items.DRIED_KELP);
        registerCompostableItem(0.3F, Items.SHORT_GRASS);
        registerCompostableItem(0.3F, Items.KELP);
        registerCompostableItem(0.3F, Items.MELON_SEEDS);
        registerCompostableItem(0.3F, Items.PUMPKIN_SEEDS);
        registerCompostableItem(0.3F, Items.SEAGRASS);
        registerCompostableItem(0.3F, Items.SWEET_BERRIES);
        registerCompostableItem(0.3F, Items.GLOW_BERRIES);
        registerCompostableItem(0.3F, Items.WHEAT_SEEDS);
        registerCompostableItem(0.3F, Items.MOSS_CARPET);
        registerCompostableItem(0.3F, Items.PINK_PETALS);
        registerCompostableItem(0.3F, Items.SMALL_DRIPLEAF);
        registerCompostableItem(0.3F, Items.HANGING_ROOTS);
        registerCompostableItem(0.3F, Items.MANGROVE_ROOTS);
        registerCompostableItem(0.3F, Items.TORCHFLOWER_SEEDS);
        registerCompostableItem(0.3F, Items.PITCHER_POD);
        registerCompostableItem(0.5F, Items.DRIED_KELP_BLOCK);
        registerCompostableItem(0.5F, Items.TALL_GRASS);
        registerCompostableItem(0.5F, Items.FLOWERING_AZALEA_LEAVES);
        registerCompostableItem(0.5F, Items.CACTUS);
        registerCompostableItem(0.5F, Items.SUGAR_CANE);
        registerCompostableItem(0.5F, Items.VINE);
        registerCompostableItem(0.5F, Items.NETHER_SPROUTS);
        registerCompostableItem(0.5F, Items.WEEPING_VINES);
        registerCompostableItem(0.5F, Items.TWISTING_VINES);
        registerCompostableItem(0.5F, Items.MELON_SLICE);
        registerCompostableItem(0.5F, Items.GLOW_LICHEN);
        registerCompostableItem(0.65F, Items.SEA_PICKLE);
        registerCompostableItem(0.65F, Items.LILY_PAD);
        registerCompostableItem(0.65F, Items.PUMPKIN);
        registerCompostableItem(0.65F, Items.CARVED_PUMPKIN);
        registerCompostableItem(0.65F, Items.MELON);
        registerCompostableItem(0.65F, Items.APPLE);
        registerCompostableItem(0.65F, Items.BEETROOT);
        registerCompostableItem(0.65F, Items.CARROT);
        registerCompostableItem(0.65F, Items.COCOA_BEANS);
        registerCompostableItem(0.65F, Items.POTATO);
        registerCompostableItem(0.65F, Items.WHEAT);
        registerCompostableItem(0.65F, Items.BROWN_MUSHROOM);
        registerCompostableItem(0.65F, Items.RED_MUSHROOM);
        registerCompostableItem(0.65F, Items.MUSHROOM_STEM);
        registerCompostableItem(0.65F, Items.CRIMSON_FUNGUS);
        registerCompostableItem(0.65F, Items.WARPED_FUNGUS);
        registerCompostableItem(0.65F, Items.NETHER_WART);
        registerCompostableItem(0.65F, Items.CRIMSON_ROOTS);
        registerCompostableItem(0.65F, Items.WARPED_ROOTS);
        registerCompostableItem(0.65F, Items.SHROOMLIGHT);
        registerCompostableItem(0.65F, Items.DANDELION);
        registerCompostableItem(0.65F, Items.POPPY);
        registerCompostableItem(0.65F, Items.BLUE_ORCHID);
        registerCompostableItem(0.65F, Items.ALLIUM);
        registerCompostableItem(0.65F, Items.AZURE_BLUET);
        registerCompostableItem(0.65F, Items.RED_TULIP);
        registerCompostableItem(0.65F, Items.ORANGE_TULIP);
        registerCompostableItem(0.65F, Items.WHITE_TULIP);
        registerCompostableItem(0.65F, Items.PINK_TULIP);
        registerCompostableItem(0.65F, Items.OXEYE_DAISY);
        registerCompostableItem(0.65F, Items.CORNFLOWER);
        registerCompostableItem(0.65F, Items.LILY_OF_THE_VALLEY);
        registerCompostableItem(0.65F, Items.WITHER_ROSE);
        registerCompostableItem(0.65F, Items.FERN);
        registerCompostableItem(0.65F, Items.SUNFLOWER);
        registerCompostableItem(0.65F, Items.LILAC);
        registerCompostableItem(0.65F, Items.ROSE_BUSH);
        registerCompostableItem(0.65F, Items.PEONY);
        registerCompostableItem(0.65F, Items.LARGE_FERN);
        registerCompostableItem(0.65F, Items.SPORE_BLOSSOM);
        registerCompostableItem(0.65F, Items.AZALEA);
        registerCompostableItem(0.65F, Items.MOSS_BLOCK);
        registerCompostableItem(0.65F, Items.BIG_DRIPLEAF);
        registerCompostableItem(0.85F, Items.HAY_BLOCK);
        registerCompostableItem(0.85F, Items.BROWN_MUSHROOM_BLOCK);
        registerCompostableItem(0.85F, Items.RED_MUSHROOM_BLOCK);
        registerCompostableItem(0.85F, Items.NETHER_WART_BLOCK);
        registerCompostableItem(0.85F, Items.WARPED_WART_BLOCK);
        registerCompostableItem(0.85F, Items.FLOWERING_AZALEA);
        registerCompostableItem(0.85F, Items.BREAD);
        registerCompostableItem(0.85F, Items.BAKED_POTATO);
        registerCompostableItem(0.85F, Items.COOKIE);
        registerCompostableItem(0.85F, Items.TORCHFLOWER);
        registerCompostableItem(0.85F, Items.PITCHER_PLANT);
        registerCompostableItem(1.0F, Items.CAKE);
        registerCompostableItem(1.0F, Items.PUMPKIN_PIE);

        registerCompostableItem(0.3F, ModItems.BUSH);
        registerCompostableItem(0.3F, ModBlocks.CACTUS_FLOWER);
        registerCompostableItem(0.3F, ModBlocks.FIREFLY_BUSH);
        registerCompostableItem(0.3F, ModBlocks.LEAF_LITTER);
        registerCompostableItem(0.3F, ModBlocks.SHORT_DRY_GRASS);
        registerCompostableItem(0.3F, ModBlocks.TALL_DRY_GRASS);
        registerCompostableItem(0.3F, ModBlocks.WILDFLOWER);
        registerCompostableItem(0.3F, ModBlocks.PALE_MOSS_CARPET);
        registerCompostableItem(0.3F, ModBlocks.PALE_IVY);
        registerCompostableItem(0.3F, Items.EGG);
        registerCompostableItem(0.35F, ModItems.BROWN_EGG);
        registerCompostableItem(0.25F, ModItems.BLUE_EGG);
    }

    private static void registerCompostableItem(float levelIncreaseChance, ItemConvertible item) {
        ITEM_TO_LEVEL_INCREASE_CHANCE.put(item.asItem(), levelIncreaseChance);
    }
}
