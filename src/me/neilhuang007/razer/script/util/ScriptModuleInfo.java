package me.neilhuang007.razer.script.util;

import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import org.lwjgl.input.Keyboard;

import java.lang.annotation.Annotation;

/**
 * @author Strikeless
 * @since 15.05.2022
 */
public final class ScriptModuleInfo implements ModuleInfo {

    private final String name, description;

    public ScriptModuleInfo(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public Category category() {
        return Category.SCRIPT;
    }

    @Override
    public int keyBind() {
        return Keyboard.KEY_NONE;
    }

    @Override
    public boolean autoEnabled() {
        return false;
    }

    @Override
    public boolean allowDisable() {
        return true;
    }

    @Override
    public boolean hidden() {
        return false;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return ScriptModuleInfo.class;
    }
}
