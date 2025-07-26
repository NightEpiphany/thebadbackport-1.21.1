package com.moigferdsrte.pale_forest.particles;

import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.ColorHelper;

public class FireflyParticle implements ParticleEffect {

    private final ParticleType<FireflyParticle> type;
    private final int color;

    public FireflyParticle(ParticleType<FireflyParticle> type, int color) {
        this.type = type;
        this.color = color;
    }

    public static MapCodec<FireflyParticle> createCodec(ParticleType<FireflyParticle> type) {
        return Codecs.ARGB.<FireflyParticle>xmap(color -> new FireflyParticle(type, color), fireflyParticle -> fireflyParticle.color).fieldOf("color");
    }

    public static PacketCodec<? super ByteBuf, FireflyParticle> createPacketCodec(ParticleType<FireflyParticle> type) {
        return PacketCodecs.INTEGER.xmap(color -> new FireflyParticle(type, color), particleEffect -> particleEffect.color);
    }

    @Override
    public ParticleType<?> getType() {
        return this.type;
    }

    public float getRed() {
        return (float) ColorHelper.Argb.getRed(this.color) / 255.0F;
    }

    public float getGreen() {
        return (float)ColorHelper.Argb.getGreen(this.color) / 255.0F;
    }

    public float getBlue() {
        return (float)ColorHelper.Argb.getBlue(this.color) / 255.0F;
    }

    public float getAlpha() {
        return (float)ColorHelper.Argb.getAlpha(this.color) / 255.0F;
    }

    public static FireflyParticle create(ParticleType<FireflyParticle> type, int color) {
        return new FireflyParticle(type, color);
    }
}
