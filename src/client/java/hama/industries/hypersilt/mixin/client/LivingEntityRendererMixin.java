package hama.industries.hypersilt.mixin.client;

import hama.industries.hypersilt.HYPER;
import hama.industries.hypersilt.HyperSilt;
import hama.industries.hypersilt.IHYPER;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @Inject(
            method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V",
            at =@At("HEAD")
    ) public <T extends LivingEntity, S extends LivingEntityRenderState> void PRIME(T livingEntity, S livingEntityRenderState, float f, CallbackInfo ci) {
        if (!((IHYPER)livingEntityRenderState).hyper_silt$FIRED())
            ((IHYPER)livingEntityRenderState).hyper_silt$PRIME(livingEntity.hasAttached(HyperSilt.HYPER), livingEntity.getPos(), livingEntity.getId());
    }

    @Inject(
            method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
             at = @At("HEAD")
    ) public <S extends LivingEntityRenderState> void renderHead(S livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        HYPER.renderHEAD(((LivingEntityRenderer) (Object)this), livingEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }

    @Inject(
            method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
             at = @At("TAIL")
    ) public <S extends LivingEntityRenderState> void renderTail(S livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        HYPER.renderTAIL(((LivingEntityRenderer) (Object)this), livingEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }
}
