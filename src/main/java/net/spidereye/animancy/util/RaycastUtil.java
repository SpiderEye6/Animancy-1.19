package net.spidereye.animancy.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;

public class RaycastUtil {
    public HitResult raycast() {
        MinecraftClient client = MinecraftClient.getInstance();
        return client.crosshairTarget;
    }

    public static HitResult ensureTargetInRange(HitResult hitResult, Vec3d cameraPos, double interactionRange) {
        Vec3d vec3d = hitResult.getPos();
        if (!vec3d.isInRange(cameraPos, interactionRange)) {
            Vec3d vec3d2 = hitResult.getPos();
            Direction direction = Direction.getFacing(vec3d2.x - cameraPos.x, vec3d2.y - cameraPos.y, vec3d2.z - cameraPos.z);
            return BlockHitResult.createMissed(vec3d2, direction, new BlockPos(vec3d2));
        } else {
            return hitResult;
        }
    }
    public static HitResult raycast(Entity camera, double maxDistance, float tickDelta) {
        double d = maxDistance;
        double e = MathHelper.square(maxDistance);
        Vec3d vec3d = camera.getCameraPosVec(tickDelta);
        HitResult hitResult = camera.raycast(d, tickDelta, false);
        double f = hitResult.getPos().squaredDistanceTo(vec3d);
        if (hitResult.getType() != HitResult.Type.MISS) {
            e = f;
            d = Math.sqrt(f);
        }

        Vec3d vec3d2 = camera.getRotationVec(tickDelta);
        Vec3d vec3d3 = vec3d.add(vec3d2.x * d, vec3d2.y * d, vec3d2.z * d);
        float g = 1.0F;
        Box box = camera.getBoundingBox().stretch(vec3d2.multiply(d)).expand(1.0, 1.0, 1.0);
        EntityHitResult entityHitResult = ProjectileUtil.raycast(camera, vec3d, vec3d3, box, entity -> !entity.isSpectator() && entity.canHit(), e);
        return entityHitResult != null && entityHitResult.getPos().squaredDistanceTo(vec3d) < f
                ? ensureTargetInRange(entityHitResult, vec3d, maxDistance)
                : ensureTargetInRange(hitResult, vec3d, maxDistance);
    }

    public static Entity raycastEntity() {
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;

        if (hit.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHit = (EntityHitResult) hit;
            return entityHit.getEntity();
        } else {
            return null;
        }
    }

    public static Entity raycastEntity(Entity camera, double maxDistance, float tickDelta) {
        HitResult hit = raycast(camera, maxDistance, tickDelta);

        if (hit.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHit = (EntityHitResult) hit;
            return entityHit.getEntity();
        } else {
            return null;
        }
    }

    public static Block raycastBlock() {
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;

        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockPos blockPos = blockHit.getBlockPos();
            BlockState blockState = client.world.getBlockState(blockPos);
            return blockState.getBlock();
        } else {
            return null;
        }
    }

    public static Block raycastBlock(Entity camera, double maxDistance, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = raycast(camera, maxDistance, tickDelta);

        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockPos blockPos = blockHit.getBlockPos();
            BlockState blockState = client.world.getBlockState(blockPos);
            return blockState.getBlock();
        } else {
            return null;
        }
    }
}
