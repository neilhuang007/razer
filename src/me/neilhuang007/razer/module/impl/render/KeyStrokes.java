package me.neilhuang007.razer.module.impl.render;

import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.module.impl.render.keystrokes.KeyStroke;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.render.Render2DEvent;
import me.neilhuang007.razer.util.render.RenderUtil;
import me.neilhuang007.razer.util.vector.Vector2d;
import me.neilhuang007.razer.util.vector.Vector2f;
import me.neilhuang007.razer.value.impl.BooleanValue;
import me.neilhuang007.razer.value.impl.DragValue;

import java.util.ArrayList;

@ModuleInfo(name = "module.render.keystrokes.name", description = "module.render.keystrokes.description", category = Category.RENDER)
public final class KeyStrokes extends Module {

    private final DragValue position = new DragValue("Position", this, new Vector2d(100, 100), false);
    private final BooleanValue space = new BooleanValue("Space", this, true);
    private boolean lastSpace;
    private final int gap = 3;

    private ArrayList<KeyStroke> keyStrokes = new ArrayList<KeyStroke>();

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (mc.currentScreen != null) {
            if (lastSpace != space.getValue()) {
                keyStrokes = new ArrayList<KeyStroke>() {{
                    add(new KeyStroke(new Vector2f(RenderUtil.GENERIC_SCALE + gap, 0), mc.gameSettings.keyBindForward));
                    add(new KeyStroke(new Vector2f(0, RenderUtil.GENERIC_SCALE + gap), mc.gameSettings.keyBindLeft));
                    add(new KeyStroke(new Vector2f(RenderUtil.GENERIC_SCALE * 2 + gap * 2, RenderUtil.GENERIC_SCALE + gap), mc.gameSettings.keyBindRight));
                    add(new KeyStroke(new Vector2f(RenderUtil.GENERIC_SCALE + gap, RenderUtil.GENERIC_SCALE + gap), mc.gameSettings.keyBindBack));
                    if (space.getValue()) {
                        add(new KeyStroke(new Vector2f(RenderUtil.GENERIC_SCALE * 3 + gap * 2, RenderUtil.GENERIC_SCALE), new Vector2f(0, (RenderUtil.GENERIC_SCALE + gap) * 2), "Space", mc.gameSettings.keyBindJump));
                    }
                }};
            }

            lastSpace = space.getValue();
        }

        // Setting scale for draggable element
        position.setScale(new Vector2d(RenderUtil.GENERIC_SCALE * 3 + gap * 2, RenderUtil.GENERIC_SCALE * 3 + gap * 2));

        NORMAL_RENDER_RUNNABLES.add(() -> keyStrokes.forEach(keyStroke -> keyStroke.render(position.position)));
        NORMAL_BLUR_RUNNABLES.add(() -> keyStrokes.forEach(keyStroke -> keyStroke.blur(position.position)));
        NORMAL_POST_BLOOM_RUNNABLES.add(() -> keyStrokes.forEach(keyStroke -> keyStroke.bloom(position.position)));
    };
}