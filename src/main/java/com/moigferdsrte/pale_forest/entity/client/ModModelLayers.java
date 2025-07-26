package com.moigferdsrte.pale_forest.entity.client;

import com.moigferdsrte.pale_forest.ThePaleForest;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {

    public static final EntityModelLayer CREAKING =
            new EntityModelLayer(Identifier.of(ThePaleForest.MOD_ID, "creaking"), "main");
}
