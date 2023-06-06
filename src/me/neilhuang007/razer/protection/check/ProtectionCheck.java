package me.neilhuang007.razer.protection.check;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.neilhuang007.razer.protection.check.api.McqBFVadWB;

/**
 * @author Strikeless
 * @since 25.03.2022
 */
@Getter
@RequiredArgsConstructor
public abstract class ProtectionCheck {

    private final McqBFVadWB trigger;
    private final boolean exemptDev;

    public abstract boolean check() throws Throwable;
}
