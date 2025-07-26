package com.moigferdsrte.pale_forest.mixin.extra;

import com.moigferdsrte.pale_forest.api.leash.LeashDataExtension;
import net.minecraft.entity.Leashable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Leashable.LeashData.class)
public class LeashDataMixin implements LeashDataExtension {

    @Unique private double angularMomentum;

    @Override
    public double angularMomentum() {
        return this.angularMomentum;
    }

    @Override
    public void setAngularMomentum(double angularMomentum) {
        this.angularMomentum = angularMomentum;
    }
}

