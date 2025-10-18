package com.createsurvival.createsurvival.content.block;

import com.createsurvival.createsurvival.content.item.WeatherproofingCompoundItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * A rugged casing block that protects kinetic contraptions from the elements. It weathers when left exposed to rain,
 * producing rust flakes that can be recycled into useful resources.
 */
public class WeatherproofCasingBlock extends Block {
    public static final IntegerProperty WEATHERING = IntegerProperty.create("weathering", 0, 3);
    private static final int MAX_STAGE = 3;
    private static final VoxelShape SHAPE = Shapes.box(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.9375f, 0.9375f);

    public WeatherproofCasingBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(3.5f, 9.0f)
                .sound(SoundType.COPPER)
                .randomTicks());
        registerDefaultState(stateDefinition.any().setValue(WEATHERING, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{WEATHERING});
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) {
            return;
        }
        if (!isProtected(level, pos) && level.isRainingAt(pos.above())) {
            int stage = state.getValue(WEATHERING);
            if (stage < MAX_STAGE) {
                level.setBlock(pos, state.setValue(WEATHERING, stage + 1), Block.UPDATE_ALL);
            } else {
                popResource(level, pos, new ItemStack(com.createsurvival.createsurvival.content.CSItems.RUST_FLAKES.get()));
                level.levelEvent(1043, pos, 0); // copper oxidation sound
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.getItem() instanceof WeatherproofingCompoundItem compound) {
            if (!level.isClientSide) {
                int stage = state.getValue(WEATHERING);
                if (stage > 0) {
                    level.setBlock(pos, state.setValue(WEATHERING, 0), Block.UPDATE_ALL);
                    if (!player.isCreative()) {
                        compound.consumeCompound(heldItem, player);
                    }
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    private boolean isProtected(LevelReader level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWN) {
                continue;
            }
            BlockState neighbour = level.getBlockState(pos.relative(direction));
            if (!neighbour.getMaterial().isSolid()) {
                return false;
            }
        }
        return true;
    }
}
