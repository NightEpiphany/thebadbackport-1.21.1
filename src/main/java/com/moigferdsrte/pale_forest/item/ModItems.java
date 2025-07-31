package com.moigferdsrte.pale_forest.item;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.block.ModBlocks;
import com.moigferdsrte.pale_forest.entity.ModBoats;
import com.moigferdsrte.pale_forest.entity.ModEntities;
import com.moigferdsrte.pale_forest.group.ModItemGroup;
import com.moigferdsrte.pale_forest.item.custom.BlueEggItem;
import com.moigferdsrte.pale_forest.item.custom.BrownEggItem;
import com.moigferdsrte.pale_forest.item.custom.HarnessItem;
import com.moigferdsrte.pale_forest.item.custom.PaleSwordItem;
import com.moigferdsrte.pale_forest.item.custom.egg.*;
import com.moigferdsrte.pale_forest.poi.BlockItemPlacement;
import com.moigferdsrte.pale_forest.sound.ModJukeboxSongs;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.*;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {


    public static final Item PALE_OAK_SIGN = registerItems("pale_oak_sign", new SignItem(new Item.Settings().maxCount(16), ModBlocks.PALE_OAK_SIGN, ModBlocks.PALE_OAK_WALL_SIGN));

    public static final Item PALE_OAK_HANGING_SIGN = registerItems("pale_oak_hanging_sign", new HangingSignItem(ModBlocks.PALE_OAK_HANGING_SIGN, ModBlocks.PALE_OAK_WALL_HANGING_SIGN, new Item.Settings().maxCount(16)));

    public static final Item PALE_OAK_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.PALE_OAK_BOAT, ModBoats.PALE_OAK_BOAT_KEY, false);

    public static final Item PALE_OAK_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.PALE_OAK_CHEST_BOAT, ModBoats.PALE_OAK_BOAT_KEY, true);

    public static final Item CREAKING_SPAWN_EGG = registerItems("creaking_spawn_egg", new SpawnEggItem(ModEntities.CREAKING, 0x71656C, 0x43373E, new Item.Settings()));

    public static final Item HAPPY_GHAST_SPAWN_EGG = registerItems("happy_ghast_spawn_egg", new SpawnEggItem(ModEntities.HAPPY_GHAST, 16382457, 12369084, new Item.Settings()));

    public static final Item PALE_SWORD = registerItems(
            "pale_sword", new PaleSwordItem(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.WOOD, 3, -2.4F)))
    );
    public static final Item PALE_SHOVEL = registerItems(
            "pale_shovel",
            new ShovelItem(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ToolMaterials.WOOD, 1.4F, -3.0F)))
    );
    public static final Item PALE_PICKAXE = registerItems(
            "pale_pickaxe",
            new PickaxeItem(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.WOOD, 1.0F, -2.8F)))
    );
    public static final Item PALE_AXE = registerItems(
            "pale_axe", new AxeItem(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ToolMaterials.WOOD, 6.0F, -3.2F)))
    );
    public static final Item PALE_HOE = registerItems(
            "pale_hoe", new HoeItem(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ToolMaterials.WOOD, 0.0F, -3.0F)))
    );

    public static final Item COPPER_HORSE_ARMOR = registerItems("copper_horse_armor",
            new AnimalArmorItem(ModArmorMaterials.COPPER, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1)));

    public static final Item BLUE_HARNESS = registerItems("blue_harness", new HarnessItem(DyeColor.BLUE, false, new Item.Settings().maxCount(1)));

    public static final Item GREEN_HARNESS = registerItems("green_harness", new HarnessItem(DyeColor.GREEN, false, new Item.Settings().maxCount(1)));

    public static final Item CYAN_HARNESS = registerItems("cyan_harness", new HarnessItem(DyeColor.CYAN, false, new Item.Settings().maxCount(1)));

    public static final Item LIGHT_BLUE_HARNESS = registerItems("light_blue_harness", new HarnessItem(DyeColor.LIGHT_BLUE, false, new Item.Settings().maxCount(1)));

    public static final Item LIME_HARNESS = registerItems("lime_harness", new HarnessItem(DyeColor.LIME, false, new Item.Settings().maxCount(1)));

    public static final Item MAGENTA_HARNESS = registerItems("magenta_harness", new HarnessItem(DyeColor.MAGENTA, false, new Item.Settings().maxCount(1)));

    public static final Item PINK_HARNESS = registerItems("pink_harness", new HarnessItem(DyeColor.PINK, false, new Item.Settings().maxCount(1)));

    public static final Item PURPLE_HARNESS = registerItems("purple_harness", new HarnessItem(DyeColor.PURPLE, false, new Item.Settings().maxCount(1)));

    public static final Item RED_HARNESS = registerItems("red_harness", new HarnessItem(DyeColor.RED, false, new Item.Settings().maxCount(1)));

    public static final Item WHITE_HARNESS = registerItems("white_harness", new HarnessItem(DyeColor.WHITE, false, new Item.Settings().maxCount(1)));

    public static final Item ORANGE_HARNESS = registerItems("orange_harness", new HarnessItem(DyeColor.ORANGE, false, new Item.Settings().maxCount(1)));

    public static final Item LIGHT_GRAY_HARNESS = registerItems("light_gray_harness", new HarnessItem(DyeColor.LIGHT_GRAY, false, new Item.Settings().maxCount(1)));

    public static final Item GRAY_HARNESS = registerItems("gray_harness", new HarnessItem(DyeColor.GRAY, false, new Item.Settings().maxCount(1)));

    public static final Item BROWN_HARNESS = registerItems("brown_harness", new HarnessItem(DyeColor.BROWN, false, new Item.Settings().maxCount(1)));

    public static final Item BLACK_HARNESS = registerItems("black_harness", new HarnessItem(DyeColor.BLACK, false, new Item.Settings().maxCount(1)));

    public static final Item YELLOW_HARNESS = registerItems("yellow_harness", new HarnessItem(DyeColor.YELLOW, false, new Item.Settings().maxCount(1)));

    public static final Item RESIN_BRICK = registerItems("resin_brick", new Item(new Item.Settings()));

    public static final Item PALE_OAK_STICK = registerItems("pale_oak_stick", new Item(new Item.Settings()));

    public static final Item RESIN_CLUMP = registerItems("resin_clump", new AliasedBlockItem(ModBlocks.RESIN_CLUMP, new Item.Settings()));

    public static final Item BUSH = register(ModBlocks.BUSH);

    public static final Item BLUE_EGG = registerItems("blue_egg", new BlueEggItem(new Item.Settings().maxCount(16)));

    public static final Item BROWN_EGG = registerItems("brown_egg", new BrownEggItem(new Item.Settings().maxCount(16)));

    public static final Item MUSIC_DISC_TEARS = registerItems(
            "music_disc_tears", new BlockItemPlacement(ModBlocks.MUSIC_DISC_TEARS, new Item.Settings().maxCount(1).rarity(Rarity.RARE).jukeboxPlayable(ModJukeboxSongs.TEARS))
    );

    public static final Item MUSIC_DISC_LAVA_CHICKEN = registerItems(
            "music_disc_lava_chicken", new BlockItemPlacement(ModBlocks.MUSIC_DISC_LAVA_CHICKEN, new Item.Settings().maxCount(1).rarity(Rarity.RARE).jukeboxPlayable(ModJukeboxSongs.LAVA_CHICKEN))
    );

    public static Item JEB_SHEEP_SPAWN_EGG = registerItems("jeb_sheep_spawn_egg", new SheepDyeableSpawnEgg(DyeColor.WHITE, new Item.Settings(), true));

    public static Item BROWN_MOOSHROOM_SPAWN_EGG = registerItems("brown_mooshroom_spawn_egg", new BrownMooshroomSpawnEgg(new Item.Settings()));

    public static Item CHARGED_CREEPER_SPAWN_EGG = registerItems("charged_creeper_spawn_egg", new ChargedCreeperSpawnEgg(new Item.Settings()));

    public static Item SCREAMING_GOAT_SPAWN_EGG = registerItems("screaming_goat_spawn_egg", new ScreamingGoatSpawnEgg(new Item.Settings()));

    public static Item BROWN_PANDA_SPAWN_EGG = registerItems("brown_panda_spawn_egg", new BrownPandaSpawnEgg(new Item.Settings()));

    public static Item SNOW_FOX_SPAWN_EGG = registerItems("snow_fox_spawn_egg", new SnowFoxSpawnEgg(new Item.Settings()));

    public static Item SHEARED_BOGGED_SPAWN_EGG = registerItems("sheared_bogged_spawn_egg", new ShearedEntitySpawnEgg(EntityType.BOGGED, new Item.Settings()));

    public static Item SHEARED_SNOW_GOLEM_SPAWN_EGG = registerItems("sheared_snow_golem_spawn_egg", new ShearedEntitySpawnEgg(EntityType.SNOW_GOLEM, new Item.Settings()));

    public static Item TOAST_RABBIT_SPAWN_EGG = registerItems("toast_rabbit_spawn_egg", new RabbitSpawnEgg(RabbitEntity.RabbitType.BROWN, new Item.Settings(), true));

    static {
        for (var i : DyeColor.values()) {
           final Item HARNESS = registerItems(i.getName() + "_festive_harness", new HarnessItem(i, true, new Item.Settings().maxCount(1)));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(e -> e.add(HARNESS));
        }


        for (var i : DyeColor.values()) {
            final Item SHULKER_EGG = registerItems(i.getName() + "_shulker_spawn_egg", new ShulkerDyeableSpawnEgg(i, new Item.Settings()));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(e -> e.addAfter(Items.SHULKER_SPAWN_EGG, SHULKER_EGG));
            if (i == DyeColor.WHITE) continue;
            final Item SHEEP_EGG = registerItems(i.getName() + "_sheep_spawn_egg", new SheepDyeableSpawnEgg(i, new Item.Settings(), false));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(e -> e.addAfter(Items.SHEEP_SPAWN_EGG, SHEEP_EGG));
        }

        for (var i : AxolotlEntity.Variant.values()) {
            if (i == AxolotlEntity.Variant.LUCY) continue;
            final Item AXOLOTL_EGG = registerItems(i.getName() + "_axolotl_spawn_egg", new AxolotlSpawnEgg(i, new Item.Settings()));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(e -> e.addAfter(Items.AXOLOTL_SPAWN_EGG, AXOLOTL_EGG));
        }

        for (var i : CatSpawnEgg.CatIdentifiers.values()) {
            if (i == CatSpawnEgg.CatIdentifiers.JELLIE) continue;
            final Item CAT_EGG = registerItems(i.getName() + "_cat_spawn_egg", new CatSpawnEgg(i, new Item.Settings()));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(e -> e.addAfter(Items.CAT_SPAWN_EGG, CAT_EGG));
        }

        for (var i : WolfSpawnEgg.WolfIdentifiers.values()) {
            if (i == WolfSpawnEgg.WolfIdentifiers.PALE) continue;
            final Item WOLF_EGG = registerItems(i.getName() + "_wolf_spawn_egg", new WolfSpawnEgg(i, new Item.Settings()));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(e -> e.addAfter(Items.WOLF_SPAWN_EGG, WOLF_EGG));
        }

        for (var i : FrogSpawnEgg.FrogIdentifier.values()) {
            if (i == FrogSpawnEgg.FrogIdentifier.TEMPERATE) continue;
            final Item FROG_EGG = registerItems(i.getName() + "_frog_spawn_egg", new FrogSpawnEgg(i, new Item.Settings()));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(e -> e.addAfter(Items.FROG_SPAWN_EGG, FROG_EGG));
        }

        for (var i : SingleHornOrNoneGoatSpawnEgg.GoatHornCondition.values()) {
            final Item GOAT_EGG = registerItems(i.getName() + "_horn_goat_spawn_egg", new SingleHornOrNoneGoatSpawnEgg(i, new Item.Settings()));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(e -> e.addAfter(Items.GOAT_SPAWN_EGG, GOAT_EGG));
        }

        for (var i : ParrotEntity.Variant.values()) {
            if (i == ParrotEntity.Variant.RED_BLUE) continue;
            final Item PARROT_EGG = registerItems(i.asString() + "_parrot_spawn_egg", new ParrotSpawnEgg(i, new Item.Settings()));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(e -> e.addAfter(Items.PARROT_SPAWN_EGG, PARROT_EGG));
        }

        for (var i : HorseColor.values()) {
            if (i == HorseColor.CREAMY) continue;
            final Item HORSE_EGG = registerItems(i.asString() + "_horse_spawn_egg", new HorseSpawnEgg(i, new Item.Settings()));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(e -> e.addAfter(Items.HORSE_SPAWN_EGG, HORSE_EGG));
        }

        for (var i : LlamaEntity.Variant.values()) {
            if (i == LlamaEntity.Variant.CREAMY) continue;
            final Item LLAMA_EGG = registerItems(i.asString() + "_llama_spawn_egg", new LlamaSpawnEgg(EntityType.LLAMA, i, new Item.Settings()));
            final Item TRADER_LLAMA_EGG = registerItems(i.asString() + "_trader_llama_spawn_egg", new LlamaSpawnEgg(EntityType.TRADER_LLAMA, i, new Item.Settings()));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(e -> {
                e.addAfter(Items.LLAMA_SPAWN_EGG, LLAMA_EGG);
                e.addAfter(Items.TRADER_LLAMA_SPAWN_EGG, TRADER_LLAMA_EGG);
            });
        }

        for (var i : RabbitEntity.RabbitType.values()) {
            if (i == RabbitEntity.RabbitType.BROWN) continue;
            final Item RABBIT_EGG = registerItems(i.asString() + "_rabbit_spawn_egg", new RabbitSpawnEgg(i, new Item.Settings(), false));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(e -> e.addAfter(Items.RABBIT_SPAWN_EGG, RABBIT_EGG));
        }

        for (var i : PreviewMobSpawnEgg.BiomeIdentifier.values()) {
            final Item CHICKEN_EGG = registerItems(i.getName() + "_chicken_spawn_egg", new PreviewMobSpawnEgg(EntityType.CHICKEN, new Item.Settings()));
            final Item COW_EGG = registerItems(i.getName() + "_cow_spawn_egg", new PreviewMobSpawnEgg(EntityType.COW, new Item.Settings()));
            final Item PIG_EGG = registerItems(i.getName() + "_pig_spawn_egg", new PreviewMobSpawnEgg(EntityType.PIG, new Item.Settings()));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(e -> {
                e.addBefore(Items.PIG_SPAWN_EGG, PIG_EGG);
                e.addBefore(Items.COW_SPAWN_EGG, COW_EGG);
                e.addBefore(Items.CHICKEN_SPAWN_EGG, CHICKEN_EGG);
            });
        }
    }

    public static final FoodComponent CONJUGATE_APPLE_COMPONENT = new FoodComponent.Builder()
            .nutrition(4)
            .saturationModifier(1.2F)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0), 1.0F)
            .statusEffect(new StatusEffectInstance(ThePaleForest.PALE_EROSION, 120000, 2), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 300, 1), 1.0F)
            .alwaysEdible()
            .build();


    public static final Item CONJUGATE_APPLE = registerItems("conjugate_apple", new Item(new Item.Settings().food(CONJUGATE_APPLE_COMPONENT)));

    private static Item registerItems(String id, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(ThePaleForest.MOD_ID,id), item);
    }
    public static Item register(Block block) {
        return registerItems(Registries.BLOCK.getId(block).getPath(), new BlockItem(block, new Item.Settings()));
    }
    public static void registerModItems(){
        ThePaleForest.LOGGER.info("Registering Items");
    }
}
