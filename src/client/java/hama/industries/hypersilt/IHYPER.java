package hama.industries.hypersilt;

import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Unique;

public interface IHYPER {
    boolean hyper_silt$HYPER();
    Vec3d hyper_silt$ANCHOR();
    void hyper_silt$PRIME(boolean primed, Vec3d anchor, int id);
    void hyper_silt$FIRE(boolean set);
    boolean hyper_silt$FIRED();
    int hyper_silt$id();
    void hyper_silt$setColor(int color);
    int hyper_silt$color();
}
