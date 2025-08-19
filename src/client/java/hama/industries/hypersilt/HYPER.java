package hama.industries.hypersilt;

import it.unimi.dsi.fastutil.objects.Object2ReferenceArrayMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class HYPER {

    private static final Map<Integer, List<HyperState>> afterImages = new Object2ReferenceArrayMap<>();

    public static class HyperState {
        public final Vec3d pos;
        public final int color;
        public int age;
        public final float bodyYaw;
        public final float headYaw;
        public final float pitch;
        public final float progress;
        public final float amplitude;


        public HyperState(Vec3d pos, int age, int color, float bodyYaw, float headYaw, float pitch, float progress, float amplitude) {
            this.pos = pos;
            this.age = age;
            this.color = color;
            this.bodyYaw = bodyYaw;
            this.headYaw = headYaw;
            this.pitch = pitch;
            this.progress = progress;
            this.amplitude = amplitude;
        }
    }

    private static final int[] colors = new int[100];

    static {
        for (int i = 0; i < colors.length; i++) {
            float h = (float) i / colors.length;
            colors[i] = Color.HSBtoRGB(h, 1, 1);
        }
    }

    public static <T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>>
    void renderTAIL(LivingEntityRenderer<T, S, M> renderer, S state, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int packedLight) {
        IHYPER hyper = ((IHYPER) state);
        if (hyper.hyper_silt$FIRED()) return;
        var entry = afterImages.get(hyper.hyper_silt$id());
        if (!hyper.hyper_silt$HYPER() && (entry == null || afterImages.isEmpty())) return;

        hyper.hyper_silt$FIRE(true);
        List<HyperState> images = afterImages.computeIfAbsent(hyper.hyper_silt$id(), i -> new ReferenceArrayList<>());
        Vec3d anchor = hyper.hyper_silt$ANCHOR();
        int lastColor = images.isEmpty() ? 0 : images.getLast().color;

        if (hyper.hyper_silt$HYPER()) {
            images.add(new HyperState(anchor, 10, (lastColor + 1) % colors.length, state.bodyYaw, state.relativeHeadYaw, state.pitch, state.limbSwingAnimationProgress, state.limbSwingAmplitude));
        }

        for (HyperState hyperState : images) {
            Vec3d pos = hyperState.pos;
            Vec3d offset = pos.subtract(anchor);
            if (offset.lengthSquared() < 0.1) continue;
            matrices.push();
            {
                matrices.translate(offset);
                hyper.hyper_silt$setColor(colors[hyperState.color]);
                setupState(hyperState, state);
                renderer.render(state, matrices, vertexConsumerProvider, packedLight);
            }
            matrices.pop();
        }
        hyper.hyper_silt$setColor(0xffffff);
        hyper.hyper_silt$FIRE(false);
    }

    public static void setupState(HyperState hyperState, LivingEntityRenderState renderState) {
        renderState.bodyYaw = hyperState.bodyYaw;
        renderState.relativeHeadYaw = hyperState.headYaw;
        renderState.pitch = hyperState.pitch;
        renderState.limbSwingAnimationProgress = hyperState.progress;
        renderState.limbSwingAmplitude = hyperState.amplitude;
        if (renderState instanceof PlayerEntityRenderState playerState) {
            playerState.playerName = null;
            playerState.displayName = null;
        }
    }

    public static void clean(ClientWorld world) {
        List<Integer> toRemove = new ReferenceArrayList<>();
        for (int id : afterImages.keySet()) {
            if (afterImages.get(id).isEmpty()) {
                toRemove.add(id);
            } else if (world.getEntityById(id) instanceof LivingEntity living && !living.isRemoved() && !living.isDead()) {
                List<HyperState> subRemovals = new ReferenceArrayList<>();
                for (HyperState state : afterImages.get(id)) {
                    state.age -= 1;
                    if (state.age <= 0) {
                        subRemovals.add(state);
                    }
                }
                for (HyperState state : subRemovals) afterImages.get(id).remove(state);
            } else {
                toRemove.add(id);
            }
        }
        for (int id : toRemove) afterImages.remove(id);
    }

    public static int color(LivingEntityRenderState state, int old) {
        if (!((IHYPER) state).hyper_silt$FIRED()) return old;
        return ((IHYPER) state).hyper_silt$color();
    }
}
