package net.enorme.NER.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * JellyBlock: solid when an entity sprints/runs fast/jumps into it,
 * passable (with light drag) otherwise. Self-illuminating like glowstone.
 * The cyan "hologram" border is NOT done here — it's baked into the block
 * model JSON (nested shell element) + its own border texture, see
 * assets/mymod/models/block/jelly_block.json.
 */
public class JellyBlock extends Block {

    private static final double SPRINT_HORIZONTAL_SPEED_THRESHOLD = 0.15D;
    private static final double JUMP_VERTICAL_VELOCITY_THRESHOLD = 0.10D;
    private static final VoxelShape SOLID_SHAPE = Shapes.block();

    public JellyBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public static BlockBehaviour.Properties defaultProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_LIGHT_GREEN)
                .noOcclusion()
                .noCollission()               // base state; real collision decided per-entity below
                .strength(-1.0F, 3600000.0F)
                .sound(SoundType.SLIME_BLOCK)
                .pushReaction(PushReaction.PUSH_ONLY)
                .isSuffocating((state, level, pos) -> false);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Entity entity = (context instanceof EntityCollisionContext ecc) ? ecc.getEntity() : null;
        if (entity == null) {
            return SOLID_SHAPE;
        }
        return shouldBeSolidFor(entity) ? SOLID_SHAPE : Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SOLID_SHAPE; // keeps the selection/outline box looking like a real block always
    }

    private boolean shouldBeSolidFor(Entity entity) {
        Vec3 v = entity.getDeltaMovement();
        boolean sprinting = entity.isSprinting();
        double horizontalSpeedSq = v.x * v.x + v.z * v.z;
        boolean runningFast = horizontalSpeedSq > SPRINT_HORIZONTAL_SPEED_THRESHOLD * SPRINT_HORIZONTAL_SPEED_THRESHOLD;
        boolean jumping = v.y > JUMP_VERTICAL_VELOCITY_THRESHOLD && !entity.onGround();
        return sprinting || runningFast || jumping;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!shouldBeSolidFor(entity)) {
            Vec3 v = entity.getDeltaMovement();
            entity.setDeltaMovement(v.x * 0.9D, Math.max(v.y * 0.6D, -0.15D), v.z * 0.9D);
            entity.fallDistance = 0.0F;
        }
        super.entityInside(state, level, pos, entity);
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return type == PathComputationType.AIR;
    }
}