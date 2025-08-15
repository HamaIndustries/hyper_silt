package hama.industries.hypersilt;
import it.unimi.dsi.fastutil.objects.Object2ReferenceArrayMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HYPER {

    private static final Map<Integer, List<Pair<Vec3d, Integer>>> afterImages = new Object2ReferenceArrayMap<>();

//    private static final List<Pair<Vec3d, Integer>> afterimages = new ArrayList<>();

    public static <T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>>
    void renderHEAD(LivingEntityRenderer<T, S, M> renderer, S state, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int packedLight) {

    }

    public static <T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>>
    void renderTAIL(LivingEntityRenderer<T, S, M> renderer, S state, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int packedLight) {
        IHYPER hyper = ((IHYPER)state);
        if (hyper.hyper_silt$FIRED()) return;

        if (!hyper.hyper_silt$HYPER() && afterImages.get(hyper.hyper_silt$id()) == null) return;

        hyper.hyper_silt$FIRE(true);
        List<Pair<Vec3d, Integer>> images = afterImages.computeIfAbsent(hyper.hyper_silt$id(), i -> new ReferenceArrayList<>());
        Vec3d anchor = hyper.hyper_silt$ANCHOR();
        for (Pair<Vec3d, Integer> pair : images) {
            Vec3d pos = pair.getLeft();
            matrices.push();
            matrices.translate(pos.subtract(anchor));
            renderer.render(state, matrices, vertexConsumerProvider, packedLight);
            matrices.pop();
        }
        if ((int)state.age %1 == 0 && hyper.hyper_silt$HYPER()) images.add(new Pair<>(anchor, 10));
        hyper.hyper_silt$FIRE(false);
    }

    public static void clean(ClientWorld world) {
        List<Integer> toRemove = new ReferenceArrayList<>();
        for (int id : afterImages.keySet()) {
            if (world.getEntityById(id) instanceof LivingEntity living && !living.isRemoved() && !living.isDead()) {
                List<Pair< Vec3d, Integer >> subRemovals = new ReferenceArrayList<>();
                for (Pair< Vec3d, Integer > pair : afterImages.get(id)) {
                    pair.setRight(pair.getRight()-1);
                    if (pair.getRight() <= 0) {
                        subRemovals.add(pair);
                    }
                }
                for (Pair< Vec3d, Integer > pair : subRemovals) afterImages.get(id).remove(pair);
            } else {
                toRemove.add(id);
            }
        }
        for (int id : toRemove) afterImages.remove(id);
    }
}
