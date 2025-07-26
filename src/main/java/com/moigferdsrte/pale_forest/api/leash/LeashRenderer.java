package com.moigferdsrte.pale_forest.api.leash;

import com.moigferdsrte.pale_forest.mixin.extra.access.EntityRendererAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Leashable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class LeashRenderer<T extends Entity> {
    private final EntityRenderDispatcher dispatcher;
    @Nullable private List<LeashState> leashStates;

    public LeashRenderer(EntityRenderDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public boolean shouldRender(T entity, Frustum camera, boolean isVisible, boolean fallback) {
        if (!isVisible) {
                Box entityBox = entity.getVisibilityBoundingBox().expand(0.5);
            if (entityBox.isNaN() || entityBox.getAverageSideLength() == 0.0) {
                entityBox = new Box(entity.getX() - 2.0, entity.getY() - 2.0, entity.getZ() - 2.0, entity.getX() + 2.0, entity.getY() + 2.0, entity.getZ() + 2.0);
            }

            if (camera.isVisible(entityBox)) {
                return true;
            } else if (entity instanceof Leashable leashable) {
                Entity holder = leashable.getLeashHolder();
                if (holder != null) {
                    Box holderBox = holder.getVisibilityBoundingBox();
                    return camera.isVisible(holderBox) || camera.isVisible(entityBox.union(holderBox));
                }
            }
        }

        return fallback;
    }

    public boolean shouldRender(T entity, Frustum camera, boolean isVisible) {
        return this.shouldRender(entity, camera, isVisible, isVisible);
    }

    public void render(T entity, float partialTick, MatrixStack poseStack, VertexConsumerProvider buffer) {
        this.setupLeashRendering(entity, partialTick);

        if (this.leashStates != null) {
            for (LeashState state : this.leashStates) {
                this.renderLeash(poseStack, buffer, state);
            }
        }
    }

    private void renderLeash(MatrixStack stack, VertexConsumerProvider buffer, LeashState state) {
        float deltaX = (float) (state.end.x - state.start.x);
        float deltaY = (float) (state.end.y - state.start.y);
        float deltaZ = (float) (state.end.z - state.start.z);

        float scaleFactor = MathHelper.inverseSqrt(deltaX * deltaX + deltaZ * deltaZ) * 0.05F / 2.0F;

        float offsetZ = deltaZ * scaleFactor;
        float offsetX = deltaX * scaleFactor;

        stack.push();
        stack.translate(state.offset.x, state.offset.y, state.offset.z);

        VertexConsumer vertices = buffer.getBuffer(RenderLayer.getLeash());
        Matrix4f matrices = stack.peek().getPositionMatrix();

        for (int segment = 0; segment <= 24; segment++) {
            addVertexPair(vertices, matrices, deltaX, deltaY, deltaZ, 0.05F, 0.05F, offsetZ, offsetX, segment, false, state);
        }

        for (int segment = 24; segment >= 0; segment--) {
            addVertexPair(vertices, matrices, deltaX, deltaY, deltaZ, 0.05F, 0.0F, offsetZ, offsetX, segment, true, state);
        }

        stack.pop();
    }

    private static void addVertexPair(
        VertexConsumer vertices, Matrix4f matrices,
        float deltaX, float deltaY, float deltaZ,
        float thickness1, float thickness2,
        float offsetZ, float offsetX,
        int segment, boolean isInnerFace, LeashState state
    ) {
        float progress = (float) segment / 24.0f;

        int blockLight = (int) MathHelper.lerp(progress, state.startBlockLight, state.endBlockLight);
        int skyLight = (int) MathHelper.lerp(progress, state.startSkyLight, state.endSkyLight);
        int packedLight = LightmapTextureManager.pack(blockLight, skyLight);

        float colorModifier = segment % 2 == (isInnerFace ? 1 : 0) ? 0.7f : 1.0f;
        float red = 0.5f * colorModifier;
        float green = 0.4f * colorModifier;
        float blue = 0.3f * colorModifier;

        float posX = deltaX * progress;
        float posZ = deltaZ * progress;
        float posY = state.slack
            ? (deltaY > 0.0f
            ? deltaY * progress * progress
            : deltaY - deltaY * (1.0f - progress) * (1.0f - progress))
            : deltaY * progress;

        vertices.vertex(matrices, posX - offsetZ, posY + thickness2, posZ + offsetX).color(red, green, blue, 1.0f).light(packedLight);
        vertices.vertex(matrices, posX + offsetZ, posY + thickness1 - thickness2, posZ - offsetX).color(red, green, blue, 1.0f).light(packedLight);
    }

    private void setupLeashRendering(T entity, float partialTicks) {
        if (!(entity instanceof Leashable leashable)) {
            this.leashStates = null;
            return;
        }

        LeashExtension extension = (LeashExtension) leashable;

        Entity leashHolder = leashable.getLeashHolder();

        if (leashHolder != null) {
            float entityRotation = LeashExtension.getPreciseBodyRotation(entity, partialTicks) * ((float) Math.PI / 180);
            Vec3d leashOffset = entity.getLeashOffset(partialTicks);

            BlockPos entityPos = BlockPos.ofFloored(entity.getCameraPosVec(partialTicks));
            BlockPos holderPos = BlockPos.ofFloored(leashHolder.getCameraPosVec(partialTicks));
            int entityBlockLight = this.getBlockLightLevel(entity, entityPos);
            int holderBlockLight = this.getBlockLightLevel(leashHolder, holderPos);
            int entitySkyLight = entity.getWorld().getLightLevel(LightType.SKY, entityPos);
            int holderSkyLight = entity.getWorld().getLightLevel(LightType.SKY, holderPos);

            boolean handleHolderQuadLeash = leashHolder instanceof LeashExtension ext && ext.supportQuadLeashAsHolder();
            boolean handleQuadLeash = extension.supportQuadLeash();
            boolean useQuadLeash = handleHolderQuadLeash && handleQuadLeash;
            int leashCount = useQuadLeash ? 4 : 1;

            if (this.leashStates == null || this.leashStates.size() != leashCount) {
                this.leashStates = new ArrayList<>(leashCount);
                for (int i = 0; i < leashCount; i++) {
                    this.leashStates.add(new LeashState());
                }
            }

            if (useQuadLeash) {
                float holderRotation = LeashExtension.getPreciseBodyRotation(leashHolder, partialTicks) * ((float) Math.PI / 180);
                Vec3d holderPosition = leashHolder.getLerpedPos(partialTicks);
                Vec3d[] entityOffsets = extension.getQuadLeashOffsets();
                Vec3d[] holderOffsets = ((LeashExtension) leashHolder).getQuadLeashHolderOffsets();

                for (int i = 0; i < leashCount; i++) {
                    LeashState leashState = this.leashStates.get(i);
                    leashState.offset = entityOffsets[i].rotateY(-entityRotation);
                    leashState.start = entity.getLerpedPos(partialTicks).add(leashState.offset);
                    leashState.end = holderPosition.add(holderOffsets[i].rotateY(-holderRotation));
                    leashState.startBlockLight = entityBlockLight;
                    leashState.endBlockLight = holderBlockLight;
                    leashState.startSkyLight = entitySkyLight;
                    leashState.endSkyLight = holderSkyLight;
                    leashState.slack = false;
                }
            } else {
                Vec3d rotatedOffset = leashOffset.rotateY(-entityRotation);
                LeashState leashState = this.leashStates.getFirst();
                leashState.offset = rotatedOffset;
                leashState.start = entity.getLerpedPos(partialTicks).add(rotatedOffset);
                leashState.end = leashHolder.getLeashPos(partialTicks);
                leashState.startBlockLight = entityBlockLight;
                leashState.endBlockLight = holderBlockLight;
                leashState.startSkyLight = entitySkyLight;
                leashState.endSkyLight = holderSkyLight;
            }
        } else {
            this.leashStates = null;
        }
    }

    private int getBlockLightLevel(Entity entity, BlockPos pos) {
        return ((EntityRendererAccessor) this.dispatcher.getRenderer(entity)).callGetBlockLight(entity, pos);
    }
}