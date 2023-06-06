package me.neilhuang007.razer.util.shader.base;

import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;

import java.util.List;

@Getter
@Setter
public abstract class RiseShader implements InstanceAccess {
    private boolean active;

    public abstract void run(ShaderRenderType type, float partialTicks, List<Runnable> runnable);

    public abstract void update();
}
