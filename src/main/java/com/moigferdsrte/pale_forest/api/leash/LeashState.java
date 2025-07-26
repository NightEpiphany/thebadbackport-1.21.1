package com.moigferdsrte.pale_forest.api.leash;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class LeashState {
    public Vec3d offset = Vec3d.ZERO;
    public Vec3d start = Vec3d.ZERO;
    public Vec3d end = Vec3d.ZERO;
    public int startBlockLight = 0;
    public int endBlockLight = 0;
    public int startSkyLight = 15;
    public int endSkyLight = 15;
    public boolean slack = true;
}