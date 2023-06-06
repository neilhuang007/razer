package me.neilhuang007.razer.packetlog;


import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;


/**
 * @author Alan
 * @since 10/19/2021
 */
@Getter
@Setter
public abstract class Check implements InstanceAccess {
    public abstract boolean run();
}