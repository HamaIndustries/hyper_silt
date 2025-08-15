package hama.industries.hypersilt.mixin.client;

import hama.industries.hypersilt.IHYPER;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public abstract class LivingEntityRenderStateMixin implements IHYPER {
    @Unique
    private boolean hyper_silt$HYPER = false;

    @Unique
    private boolean hyper_silt$FIRED = false;

    @Unique
    private Vec3d hyper_silt$anchor = Vec3d.ZERO;

    @Unique
    private int hyper_silt$id;

    @Unique
    private int hyper_silt$color;

    @Override
    public boolean hyper_silt$HYPER() {
        return hyper_silt$HYPER;
    }

    @Override
    public Vec3d hyper_silt$ANCHOR() {
        return hyper_silt$anchor;
    }

    @Override
    public void hyper_silt$FIRE(boolean set) {
        hyper_silt$FIRED = set;
    }

    @Override
    public boolean hyper_silt$FIRED() {
        return hyper_silt$FIRED;
    }

    @Override
    public void hyper_silt$PRIME(boolean primed, Vec3d anchor, int id) {
        hyper_silt$HYPER = primed;
        hyper_silt$anchor = anchor;
        hyper_silt$id = id;
    }

    @Override
    public int hyper_silt$id() {
        return hyper_silt$id;
    }

    @Override
    public void hyper_silt$setColor(int color) {
        hyper_silt$color = color;
    }

    @Override
    public int hyper_silt$color() {
        return hyper_silt$color;
    }
}
