package com.moigferdsrte.pale_forest.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.moigferdsrte.pale_forest.ThePaleForest;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityFlipMixinV0<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> {

    @Shadow
    protected M model;

    @Shadow
    @Final
    protected final List<FeatureRenderer<T, M>> features = Lists.newArrayList();

    protected LivingEntityFlipMixinV0(EntityRendererFactory.Context ctx, M model, float shadowRadius) {
        super(ctx);
        this.model = model;
        this.shadowRadius = shadowRadius;
    }

    @Unique
    private static final Map<AxolotlEntity.Variant, Identifier> AXOLOTL = Util.make(Maps.<AxolotlEntity.Variant, Identifier>newHashMap(), variants -> {
        for (AxolotlEntity.Variant variant : AxolotlEntity.Variant.values()) {
            variants.put(variant, Identifier.ofVanilla(String.format(Locale.ROOT, "textures/entity/axolotl/axolotl_%s.png", variant.getName())));
        }
    });

    @Unique
    private static final Map<MooshroomEntity.Type, Identifier> MOOSHROOM = Util.make(Maps.<MooshroomEntity.Type, Identifier>newHashMap(), map -> {
        map.put(MooshroomEntity.Type.BROWN, Identifier.ofVanilla("textures/entity/cow/brown_mooshroom.png"));
        map.put(MooshroomEntity.Type.RED, Identifier.ofVanilla("textures/entity/cow/red_mooshroom.png"));
    });

    @Unique
    private static final Map<PandaEntity.Gene, Identifier> PANDA = Util.make(Maps.newEnumMap(PandaEntity.Gene.class), map -> {
        map.put(PandaEntity.Gene.NORMAL, Identifier.ofVanilla("textures/entity/panda/panda.png"));
        map.put(PandaEntity.Gene.LAZY, Identifier.ofVanilla("textures/entity/panda/lazy_panda.png"));
        map.put(PandaEntity.Gene.WORRIED, Identifier.ofVanilla("textures/entity/panda/worried_panda.png"));
        map.put(PandaEntity.Gene.PLAYFUL, Identifier.ofVanilla("textures/entity/panda/playful_panda.png"));
        map.put(PandaEntity.Gene.BROWN, Identifier.ofVanilla("textures/entity/panda/brown_panda.png"));
        map.put(PandaEntity.Gene.WEAK, Identifier.ofVanilla("textures/entity/panda/weak_panda.png"));
        map.put(PandaEntity.Gene.AGGRESSIVE, Identifier.ofVanilla("textures/entity/panda/aggressive_panda.png"));
    });

    @Unique
    private static final Map<EntityType<?>, Identifier> DONKEY_AND_MULE = Maps.newHashMap(
            ImmutableMap.of(
                    EntityType.DONKEY, Identifier.ofVanilla("textures/entity/horse/donkey.png"), EntityType.MULE, Identifier.ofVanilla("textures/entity/horse/mule.png")
            )
    );

    @Unique
    private static final Map<HorseColor, Identifier> HORSE = Util.make(Maps.newEnumMap(HorseColor.class), map -> {
        map.put(HorseColor.WHITE, Identifier.ofVanilla("textures/entity/horse/horse_white.png"));
        map.put(HorseColor.CREAMY, Identifier.ofVanilla("textures/entity/horse/horse_creamy.png"));
        map.put(HorseColor.CHESTNUT, Identifier.ofVanilla("textures/entity/horse/horse_chestnut.png"));
        map.put(HorseColor.BROWN, Identifier.ofVanilla("textures/entity/horse/horse_brown.png"));
        map.put(HorseColor.BLACK, Identifier.ofVanilla("textures/entity/horse/horse_black.png"));
        map.put(HorseColor.GRAY, Identifier.ofVanilla("textures/entity/horse/horse_gray.png"));
        map.put(HorseColor.DARK_BROWN, Identifier.ofVanilla("textures/entity/horse/horse_darkbrown.png"));
    });

    @Unique
    public Identifier getTexture(T entity) {

        if (entity instanceof PiglinEntity)
            return Identifier.ofVanilla("textures/entity/piglin/piglin.png");
        if (entity instanceof PiglinBruteEntity)
            return Identifier.ofVanilla("textures/entity/piglin/piglin_brute.png");
        if (entity instanceof ZombifiedPiglinEntity)
            return Identifier.ofVanilla("textures/entity/piglin/zombified_piglin.png");
        if (entity instanceof HoglinEntity)
            return Identifier.ofVanilla("textures/entity/holing/hoglin.png");
        if (entity instanceof ZoglinEntity)
            return Identifier.ofVanilla("textures/entity/hoglin/zoglin.png");
        if (entity instanceof PigEntity)
            return Identifier.ofVanilla("textures/entity/pig/pig.png");
        if (entity instanceof MooshroomEntity mooshroomEntity)
            return MOOSHROOM.get(mooshroomEntity.getVariant());
        if (entity instanceof CowEntity)
            return Identifier.ofVanilla("textures/entity/cow/cow.png");
        if (entity instanceof SheepEntity)
            return Identifier.ofVanilla("textures/entity/sheep/sheep.png");
        if (entity instanceof GoatEntity)
            return Identifier.ofVanilla("textures/entity/goat/goat.png");
        if (entity instanceof ChickenEntity)
            return Identifier.ofVanilla("textures/entity/chicken.png");
        if (entity instanceof AxolotlEntity axolotlEntity)
            return AXOLOTL.get(axolotlEntity.getVariant());
        if (entity instanceof LlamaEntity llama)
            return switch (llama.getVariant()) {
                case CREAMY -> Identifier.ofVanilla("textures/entity/llama/creamy.png");
                case WHITE -> Identifier.ofVanilla("textures/entity/llama/white.png");
                case BROWN -> Identifier.ofVanilla("textures/entity/llama/brown.png");
                case GRAY -> Identifier.ofVanilla("textures/entity/llama/gray.png");
            };
        if (entity instanceof AllayEntity)
            return Identifier.ofVanilla("textures/entity/allay/allay.png");
        if (entity instanceof BeeEntity)
            return Identifier.ofVanilla("textures/entity/bee/bee.png");
        if (entity instanceof PandaEntity pandaEntity)
            return PANDA.getOrDefault(pandaEntity.getProductGene(), (Identifier)PANDA.get(PandaEntity.Gene.NORMAL));
        if (entity instanceof FoxEntity foxEntity) {
            if (foxEntity.getVariant() == FoxEntity.Type.RED) {
                return foxEntity.isSleeping() ? Identifier.ofVanilla("textures/entity/fox/fox_sleep.png") : Identifier.ofVanilla("textures/entity/fox/fox.png");
            } else {
                return foxEntity.isSleeping() ? Identifier.ofVanilla("textures/entity/fox/snow_fox_sleep.png") : Identifier.ofVanilla("textures/entity/fox/snow_fox.png");
            }
        }
        if (entity instanceof PolarBearEntity)
            return Identifier.ofVanilla("textures/entity/bear/polarbear.png");
        if (entity instanceof RabbitEntity rabbitEntity)
            return switch (rabbitEntity.getVariant()) {
                case BROWN -> Identifier.ofVanilla("textures/entity/rabbit/brown.png");
                case WHITE -> Identifier.ofVanilla("textures/entity/rabbit/white.png");
                case BLACK -> Identifier.ofVanilla("textures/entity/rabbit/black.png");
                case GOLD -> Identifier.ofVanilla("textures/entity/rabbit/gold.png");
                case SALT -> Identifier.ofVanilla("textures/entity/rabbit/salt.png");
                case WHITE_SPLOTCHED -> Identifier.ofVanilla("textures/entity/rabbit/white_splotched.png");
                case EVIL -> Identifier.ofVanilla("textures/entity/rabbit/caerbannog.png");
            };
        if (entity instanceof ArmadilloEntity)
            return Identifier.ofVanilla("textures/entity/armadillo.png");
        if (entity instanceof WolfEntity wolfEntity)
            return wolfEntity.getTextureId();
        if (entity instanceof  CatEntity catEntity)
            return catEntity.getTexture();
        if (entity instanceof OcelotEntity)
            return Identifier.ofVanilla("textures/entity/cat/ocelot.png");
        if (entity instanceof ParrotEntity parrotEntity)
            return ParrotEntityRenderer.getTexture(parrotEntity.getVariant());
        if (entity instanceof SkeletonHorseEntity)
            return Identifier.ofVanilla("textures/entity/horse/horse_skeleton.png");
        if (entity instanceof ZombieHorseEntity)
            return Identifier.ofVanilla("textures/entity/horse/horse_zombie.png");
        if (entity instanceof AbstractDonkeyEntity abstractDonkeyEntity)
            return DONKEY_AND_MULE.get(abstractDonkeyEntity.getType());
        if (entity instanceof HorseEntity horseEntity)
            return HORSE.get(horseEntity.getVariant());
        if (entity instanceof CamelEntity)
            return Identifier.ofVanilla("textures/entity/camel/camel.png");
        if (entity instanceof SnifferEntity)
            return Identifier.ofVanilla("textures/entity/sniffer/sniffer.png");
        if (entity instanceof FrogEntity frogEntity)
            return frogEntity.getVariant().value().texture();
        if (entity instanceof SlimeEntity)
            return Identifier.ofVanilla("textures/entity/slime/slime.png");
        if (entity instanceof MagmaCubeEntity)
            return Identifier.ofVanilla("textures/entity/slime/magmacube.png");
        if (entity instanceof StriderEntity striderEntity)
            return striderEntity.isCold() ? Identifier.ofVanilla("textures/entity/strider/strider_cold.png") : Identifier.ofVanilla("textures/entity/strider/strider.png");;
        if (entity instanceof TurtleEntity)
            return Identifier.ofVanilla("textures/entity/turtle/big_sea_turtle.png");
        if (entity instanceof BlazeEntity)
            return Identifier.ofVanilla("textures/entity/blaze.png");
        if (entity instanceof GhastEntity ghastEntity)
            return ghastEntity.isShooting() ? Identifier.ofVanilla("textures/entity/ghast/ghast_shooting.png") : Identifier.ofVanilla("textures/entity/ghast/ghast.png");
        if (entity instanceof BreezeEntity)
            return Identifier.ofVanilla("textures/entity/breeze/breeze.png");
        if (entity instanceof GuardianEntity)
            return Identifier.ofVanilla("textures/entity/guardian.png");
        if (entity instanceof ElderGuardianEntity)
            return Identifier.ofVanilla("textures/entity/guardian_elder.png");
        if (entity instanceof BatEntity)
            return Identifier.ofVanilla("textures/entity/bat.png");
        if (entity instanceof SilverfishEntity)
            return Identifier.ofVanilla("textures/entity/silverfish.png");
        if (entity instanceof EndermiteEntity)
            return Identifier.ofVanilla("textures/entity/endermite.png");
        if (entity instanceof DolphinEntity)
            return Identifier.ofVanilla("textures/entity/dolphin.png");

        if (entity instanceof VillagerEntity)
            return Identifier.ofVanilla("textures/entity/villager/villager.png");
        if (entity instanceof PillagerEntity)
            return Identifier.ofVanilla("textures/entity/illager/pillager.png");
        if (entity instanceof EvokerEntity)
            return Identifier.ofVanilla("textures/entity/illager/evoker.png");
        if (entity instanceof VexEntity)
            return Identifier.ofVanilla("textures/entity/illager/vex.png");
        if (entity instanceof IllusionerEntity)
            return Identifier.ofVanilla("textures/entity/illager/illusioner.png");
        if (entity instanceof RavagerEntity)
            return Identifier.ofVanilla("textures/entity/illager/ravager.png");
        if (entity instanceof VindicatorEntity)
            return Identifier.ofVanilla("textures/entity/illager/vindicator.png");
        if (entity instanceof IronGolemEntity)
            return Identifier.ofVanilla("textures/entity/iron_golem/iron_golem.png");
        if (entity instanceof SnowGolemEntity)
            return Identifier.ofVanilla("textures/entity/snow_golem.png");
        if (entity instanceof WitchEntity)
            return Identifier.ofVanilla("textures/entity/witch.png");

        if (entity instanceof HuskEntity)
            return Identifier.ofVanilla("textures/entity/zombie/husk.png");
        if (entity instanceof DrownedEntity)
            return Identifier.ofVanilla("textures/entity/zombie/drowned.png");
        if (entity instanceof ZombieVillagerEntity)
            return Identifier.ofVanilla("textures/entity/zombie_villager/zombie_villager.png");
        if (entity instanceof ZombieEntity)
            return Identifier.ofVanilla("textures/entity/zombie/zombie.png");
        if (entity instanceof BoggedEntity)
            return Identifier.ofVanilla("textures/entity/skeleton/bogged.png");
        if (entity instanceof StrayEntity)
            return Identifier.ofVanilla("textures/entity/skeleton/stray.png");
        if (entity instanceof WitherSkeletonEntity)
            return Identifier.ofVanilla("textures/entity/skeleton/wither_skeleton.png");
        if (entity instanceof SkeletonEntity)
            return Identifier.ofVanilla("textures/entity/skeleton/skeleton.png");
        if (entity instanceof CaveSpiderEntity)
            return Identifier.ofVanilla("textures/entity/spider/cave_spider.png");
        if (entity instanceof SpiderEntity)
            return Identifier.ofVanilla("textures/entity/spider/spider.png");
        if (entity instanceof CreeperEntity)
            return Identifier.ofVanilla("textures/entity/creeper/creeper.png");
        if (entity instanceof EndermanEntity)
            return Identifier.ofVanilla("textures/entity/enderman/enderman.png");
        if (entity instanceof PhantomEntity)
            return Identifier.ofVanilla("textures/entity/phantom.png");

        if (entity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity)
            return abstractClientPlayerEntity.getSkinTextures().texture();

        return Identifier.of(ThePaleForest.MOD_ID, "textures/entity/empty.png");
    }

    @Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
    private void render(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (
                shouldFlip(livingEntity) && livingEntity instanceof PiglinEntity
                || shouldFlip(livingEntity) && livingEntity instanceof PiglinBruteEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof ZombifiedPiglinEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof HoglinEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof ZoglinEntity

                        || shouldFlip(livingEntity) && livingEntity instanceof PigEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof CowEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof SheepEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof GoatEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof ChickenEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof AxolotlEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof LlamaEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof AllayEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof MooshroomEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof BeeEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof PandaEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof FoxEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof PolarBearEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof RabbitEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof ArmadilloEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof WolfEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof CatEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof OcelotEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof HorseEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof FrogEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof SlimeEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof MagmaCubeEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof StriderEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof TurtleEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof BlazeEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof GhastEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof BreezeEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof GuardianEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof ElderGuardianEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof BatEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof SilverfishEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof EndermiteEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof DolphinEntity


                        || shouldFlip(livingEntity) && livingEntity instanceof VillagerEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof VindicatorEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof IllusionerEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof EvokerEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof PillagerEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof WanderingTraderEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof RavagerEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof VexEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof IronGolemEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof SnowGolemEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof WitchEntity

                        || shouldFlip(livingEntity) && livingEntity instanceof ZombieEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof ZombieVillagerEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof DrownedEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof HuskEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof SkeletonEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof StrayEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof BoggedEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof WitherSkeletonEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof SpiderEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof CreeperEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof EndermanEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof PhantomEntity

                        || shouldFlip(livingEntity) && livingEntity instanceof SkeletonHorseEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof ZombieHorseEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof SnifferEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof ParrotEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof CamelEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof DonkeyEntity
                        || shouldFlip(livingEntity) && livingEntity instanceof MuleEntity

                        || shouldFlip(livingEntity) && livingEntity instanceof PlayerEntity
        ) {
            matrixStack.push();
            this.model.handSwingProgress = this.getHandSwingProgress(livingEntity, g);
            this.model.riding = livingEntity.hasVehicle();
            this.model.child = livingEntity.isBaby();
            float h = MathHelper.lerpAngleDegrees(g, livingEntity.prevBodyYaw, livingEntity.bodyYaw);
            float j = MathHelper.lerpAngleDegrees(g, livingEntity.prevHeadYaw, livingEntity.headYaw);
            float k = j - h;
            if (livingEntity.hasVehicle() && livingEntity.getVehicle() instanceof LivingEntity livingEntity2) {
                h = MathHelper.lerpAngleDegrees(g, livingEntity2.prevBodyYaw, livingEntity2.bodyYaw);
                k = j - h;
                float l = MathHelper.wrapDegrees(k);
                if (l < -85.0F) {
                    l = -85.0F;
                }

                if (l >= 85.0F) {
                    l = 85.0F;
                }

                h = j - l;
                if (l * l > 2500.0F) {
                    h += l * 0.2F;
                }

                k = j - h;
            }

            float m = MathHelper.lerp(g, livingEntity.prevPitch, livingEntity.getPitch());

            //flip
            m *= -1.0F;
            k *= -1.0F;

            k = MathHelper.wrapDegrees(k);
            if (livingEntity.isInPose(EntityPose.SLEEPING)) {
                Direction direction = livingEntity.getSleepingDirection();
                if (direction != null) {
                    float n = livingEntity.getEyeHeight(EntityPose.STANDING) - 0.1F;
                    matrixStack.translate((float)(-direction.getOffsetX()) * n, 0.0F, (float)(-direction.getOffsetZ()) * n);
                }
            }

            float lx = livingEntity.getScale();
            matrixStack.scale(lx, lx, lx);
            float n = this.getAnimationProgress(livingEntity, g);
            this.setupTransforms(livingEntity, matrixStack, n, h, g, lx);
            matrixStack.scale(-1.0F, -1.0F, 1.0F);
            this.scale(livingEntity, matrixStack, g);
            matrixStack.translate(0.0F, -1.501F, 0.0F);
            float o = 0.0F;
            float p = 0.0F;
            if (!livingEntity.hasVehicle() && livingEntity.isAlive()) {
                o = livingEntity.limbAnimator.getSpeed(g);
                p = livingEntity.limbAnimator.getPos(g);
                if (livingEntity.isBaby()) {
                    p *= 3.0F;
                }

                if (o > 1.0F) {
                    o = 1.0F;
                }
            }

            this.model.animateModel(livingEntity, p, o, g);
            this.model.setAngles(livingEntity, p, o, n, k, m);
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            boolean bl = this.isVisible(livingEntity);
            boolean bl2 = !bl && !livingEntity.isInvisibleTo(minecraftClient.player);
            boolean bl3 = minecraftClient.hasOutline(livingEntity);
            RenderLayer renderLayer = this.getRenderLayer(livingEntity, bl, bl2, bl3);
            if (renderLayer != null) {
                VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
                int q = getOverlay(livingEntity, this.getAnimationCounter(livingEntity, g));
                this.model.render(matrixStack, vertexConsumer, i, q, bl2 ? 654311423 : -1);
            }

            if (!livingEntity.isSpectator()) {
                for (FeatureRenderer<T, M> featureRenderer : this.features) {
                    featureRenderer.render(matrixStack, vertexConsumerProvider, i, livingEntity, p, o, g, n, k, m);
                }
            }

            matrixStack.pop();
            ci.cancel();
        }
    }

    @Unique
    @Nullable
    protected RenderLayer getRenderLayer(T entity, boolean showBody, boolean translucent, boolean showOutline) {
        Identifier identifier = this.getTexture(entity);
        if (translucent) {
            return RenderLayer.getItemEntityTranslucentCull(identifier);
        } else if (showBody) {
            return this.model.getLayer(identifier);
        } else {
            return showOutline ? RenderLayer.getOutline(identifier) : null;
        }
    }

    @Unique
    private static int getOverlay(LivingEntity entity, float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(entity.hurtTime > 0 || entity.deathTime > 0));
    }

    @Unique
    protected boolean isVisible(T entity) {
        return !entity.isInvisible();
    }

    @Unique
    protected boolean isShaking(T entity) {
        return entity.isFrozen();
    }

    @Unique
    protected void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {
        if (this.isShaking(entity)) {
            bodyYaw += (float)(Math.cos((double)entity.age * 3.25) * Math.PI * 0.4F);
        }

        if (!entity.isInPose(EntityPose.SLEEPING)) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - bodyYaw));
        }

        if (entity.deathTime > 0) {
            float f = ((float)entity.deathTime + tickDelta - 1.0F) / 20.0F * 1.6F;
            f = MathHelper.sqrt(f);
            if (f > 1.0F) {
                f = 1.0F;
            }

            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(f * this.getLyingAngle(entity)));
        } else if (entity.isUsingRiptide()) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F - entity.getPitch()));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(((float)entity.age + tickDelta) * -75.0F));
        } else if (entity.isInPose(EntityPose.SLEEPING)) {
            Direction direction = entity.getSleepingDirection();
            float g = direction != null ? getYaw(direction) : bodyYaw;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(g));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(this.getLyingAngle(entity)));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270.0F));
        }

        else if (rightFlip(entity)) {
            matrices.translate(1.0F, (entity.getWidth() - 0.2F) / scale, 0.0F);
            if (entity instanceof GhastEntity) matrices.scale(4.5F, 4.5F, 4.5F);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0F));
        }
        else if (leftFlip(entity)) {
            matrices.translate(-1.0F, (entity.getWidth() - 0.2F) / scale, 0.0F);
            if (entity instanceof GhastEntity) matrices.scale(4.5F, 4.5F, 4.5F);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90.0F));
        }
    }

    @Unique
    private static float getYaw(Direction direction) {
        return switch (direction) {
            case SOUTH -> 90.0F;
            case NORTH -> 270.0F;
            case EAST -> 180.0F;
            default -> 0.0F;
        };
    }

    @Unique
    protected float getLyingAngle(T entity) {
        return 90.0F;
    }

    @Unique
    protected float getAnimationCounter(T entity, float tickDelta) {
        return 0.0F;
    }

    @Unique
    protected void scale(T entity, MatrixStack matrices, float amount) {
    }

    @Unique
    protected float getAnimationProgress(T entity, float tickDelta) {
        return (float)entity.age + tickDelta;
    }

    @Unique
    protected float getHandSwingProgress(T entity, float tickDelta) {
        return entity.getHandSwingProgress(tickDelta);
    }

    @Unique
    private static boolean shouldFlip(LivingEntity entity) {
        if (entity instanceof PlayerEntity || entity.hasCustomName()) {
            return rightFlip(entity) || leftFlip(entity);
        }
        return false;
    }

    @Unique
    private static boolean rightFlip(LivingEntity entity) {
        if ("Lunchbone".equals(Formatting.strip(entity.getName().getString()))) {
            return !(entity instanceof PlayerEntity) || ((PlayerEntity)entity).isPartVisible(PlayerModelPart.CAPE);
        }
        return false;
    }

    @Unique
    private static boolean leftFlip(LivingEntity entity) {
        if ("Breakfastbone".equals(Formatting.strip(entity.getName().getString()))) {
            return !(entity instanceof PlayerEntity) || ((PlayerEntity)entity).isPartVisible(PlayerModelPart.CAPE);
        }
        return false;
    }
}
