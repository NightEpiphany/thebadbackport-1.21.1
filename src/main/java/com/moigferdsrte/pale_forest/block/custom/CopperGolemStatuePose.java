package com.moigferdsrte.pale_forest.block.custom;

import net.minecraft.util.StringIdentifiable;

public enum CopperGolemStatuePose implements StringIdentifiable {
    STANDING("standing"),
    SITTING("sitting"),
    RUNNING("running"),
    STAR("star");


    private final String name;

    CopperGolemStatuePose(final String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
