package hama.industries.hypersilt.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import hama.industries.hypersilt.HyperSilt;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    private final Vec3d hs$siltFactor = new Vec3d(0, 0, -1);

    @WrapOperation(
            method = "travelMidAir",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasNoDrag()Z")
    )
    public boolean blj(LivingEntity instance, Operation<Boolean> original, @Local(ordinal = 0) Vec3d movementInput) {
        return original.call(instance) || (
                ((Object) this) instanceof ClientPlayerEntity playerEntity &&
                        playerEntity.getStackInHand(Hand.MAIN_HAND).isOf(HyperSilt.HYPER_SILT) &&
                        movementInput.dotProduct(hs$siltFactor) > 0.7 &&
                        !instance.isOnGround()
        );
    }
}
