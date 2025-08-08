package hama.industries.hypersilt.mixin;

import hama.industries.hypersilt.HyperSilt;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlock {
    @Inject(
            method = "onBreak",
            at = @At("HEAD")
    ) public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<BlockState> cir) {
        if (state.isOf(Blocks.CLAY) && world.getRandom().nextFloat() < 0.00001) {
            world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), HyperSilt.HYPER_SILT.getDefaultStack()));
        }
    }

}
