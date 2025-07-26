package com.moigferdsrte.pale_forest.entity;

import com.moigferdsrte.pale_forest.ThePaleForest;
import com.moigferdsrte.pale_forest.block.ModBlocks;
import com.moigferdsrte.pale_forest.item.ModItems;
import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModBoats {
    public static final Identifier PALE_OAK_BOAT = Identifier.of(ThePaleForest.MOD_ID, "pale_oak_boat");

    public static final Identifier PALE_OAK_CHEST_BOAT = Identifier.of(ThePaleForest.MOD_ID, "pale_oak_chest_boat");

    public static final RegistryKey<TerraformBoatType> PALE_OAK_BOAT_KEY = TerraformBoatTypeRegistry.createKey(PALE_OAK_BOAT);

    public static void registerModBoats() {
        TerraformBoatType PALE_OAK_BOAT = new TerraformBoatType.Builder()
                .item(ModItems.PALE_OAK_BOAT)
                .chestItem(ModItems.PALE_OAK_CHEST_BOAT)
                .planks(ModBlocks.PALE_OAK_PLANKS.asItem())
                .build();

        Registry.register(TerraformBoatTypeRegistry.INSTANCE, PALE_OAK_BOAT_KEY, PALE_OAK_BOAT);
    }
}
