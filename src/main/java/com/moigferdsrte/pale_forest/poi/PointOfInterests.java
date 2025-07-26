package com.moigferdsrte.pale_forest.poi;

import com.google.common.collect.ImmutableList;
import com.moigferdsrte.pale_forest.block.ModBlocks;
import com.moigferdsrte.pale_forest.mixin.PoiTypesAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PointOfInterests {
    public static void init() {
        Map<BlockState, RegistryEntry<PointOfInterestType>> poiStatesToType = PoiTypesAccessor
                .getPointOfInterestStatesToType();

        RegistryEntry<PointOfInterestType> beehiveEntry = Registries.POINT_OF_INTEREST_TYPE
                .getEntry(PointOfInterestTypes.BEEHIVE).get();

        PointOfInterestType beehivePoiType = Registries.POINT_OF_INTEREST_TYPE.get(PointOfInterestTypes.BEEHIVE);

        List<BlockState> beehiveBlockStates = new ArrayList<>(beehivePoiType.blockStates());

        ImmutableList<BlockState> blockStates = ModBlocks.PALE_OAK_BEEHIVE.getStateManager().getStates();

        for (BlockState blockState : blockStates) {
            poiStatesToType.putIfAbsent(blockState, beehiveEntry);
        }

        beehiveBlockStates.addAll(blockStates);

        //beehivePoiType.blockStates().addAll(beehiveBlockStates);
    }
}
