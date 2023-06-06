package me.neilhuang007.razer.script.api.wrapper;

import lombok.AllArgsConstructor;
import me.neilhuang007.razer.script.api.API;

/**
 * @author Strikeless
 * @since 23.06.2022
 */
@AllArgsConstructor
public abstract class ScriptWrapper<T> extends API {

    protected T wrapped;
}
