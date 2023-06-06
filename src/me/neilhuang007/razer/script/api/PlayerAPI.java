package me.neilhuang007.razer.script.api;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.component.impl.player.ItemDamageComponent;
import me.neilhuang007.razer.component.impl.player.PacketlessDamageComponent;
import me.neilhuang007.razer.component.impl.player.RotationComponent;
import me.neilhuang007.razer.component.impl.player.SlotComponent;
import me.neilhuang007.razer.component.impl.player.rotationcomponent.MovementFix;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.other.TickEvent;
import me.neilhuang007.razer.script.api.wrapper.impl.ScriptEntity;
import me.neilhuang007.razer.script.api.wrapper.impl.ScriptEntityLiving;
import me.neilhuang007.razer.script.api.wrapper.impl.ScriptItemStack;
import me.neilhuang007.razer.script.api.wrapper.impl.vector.ScriptVector2;
import me.neilhuang007.razer.script.api.wrapper.impl.vector.ScriptVector3;
import me.neilhuang007.razer.util.player.DamageUtil;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.util.player.PlayerUtil;
import me.neilhuang007.razer.util.rotation.RotationUtil;
import me.neilhuang007.razer.util.vector.Vector2f;
import me.neilhuang007.razer.util.vector.Vector3d;

/**
 * @author Strikeless
 * @since 11.06.2022
 */
public class PlayerAPI extends ScriptEntityLiving {

    public PlayerAPI() {
        super(MC.thePlayer);

        Client.INSTANCE.getEventBus().register(this);
    }

    public String getName() {
        return MC.getSession().getUsername();
    }

    public String getPlayerID() {
        return MC.getSession().getPlayerID();
    }

    public boolean isOnGround() {
        return MC.thePlayer.onGround;
    }

    public boolean isMoving() {
        return MoveUtil.isMoving();
    }

    public void jump() {
        MC.thePlayer.jump();
    }

    public void strafe() {
        MoveUtil.strafe();
    }

    public void strafe(final double speed) {
        MoveUtil.strafe(speed);
    }

    public void stop() {
        MoveUtil.stop();
    }

    public void setPosition(final double posX, final double posY, final double posZ) {
        MC.thePlayer.setPosition(posX, posY, posZ);
    }

    public void setPosition(final ScriptVector3 vector) {
        this.setPosition(vector.getX().doubleValue(), vector.getY().doubleValue(), vector.getZ().doubleValue());
    }

    public void setMotion(final double motionX, final double motionY, final double motionZ) {
        MC.thePlayer.motionX = motionX;
        MC.thePlayer.motionY = motionY;
        MC.thePlayer.motionZ = motionZ;
    }

    public void setMotion(final ScriptVector3 vector) {
        this.setMotion(vector.getX().doubleValue(), vector.getY().doubleValue(), vector.getZ().doubleValue());
    }

    public void leftClick() {
        MC.clickMouse();
    }

    public void rightClick() {
        MC.rightClickMouse();
    }

    public void attackEntity(final ScriptEntityLiving target) {
        MC.playerController.attackEntity(MC.thePlayer, MC.theWorld.getEntityByID(target.getEntityId()));
    }

    public void swingItem() {
        MC.thePlayer.swingItem();
    }

    public void message(String message) {
        if (MC.thePlayer != null && MC.theWorld != null) MC.thePlayer.sendChatMessage(message);
    }

    public void setRotation(ScriptVector2 rotations, double rotationSpeed, boolean movementFix) {
        RotationComponent.setRotations(new Vector2f(rotations.getX().floatValue(), rotations.getY().floatValue()), rotationSpeed, movementFix ? MovementFix.NORMAL : MovementFix.OFF);
    }

    public void setHeldItem(int slot, boolean render) {
        SlotComponent.setSlot(slot, render);
    }

    public void setHeldItem(int slot) {
        SlotComponent.setSlot(slot, false);
    }

    public ScriptItemStack getHeldItemStack() {
        return new ScriptItemStack(SlotComponent.getItemStack());
    }

    public ScriptItemStack getClientHeldItemStack() {
        return new ScriptItemStack(MC.thePlayer.getHeldItem());
    }

    public int getClientHeldItemSlot() {
        return MC.thePlayer.inventory.currentItem;
    }

    public void itemDamage() {
        ItemDamageComponent.damage(true);
    }

    public void damage(boolean packet, float timer) {
        if (!packet) PacketlessDamageComponent.setActive(timer);
        else DamageUtil.damagePlayer(0.5);
    }

    public void damage(boolean packet) {
        damage(packet, 1);
    }

    public void fakeDamage() {
        PlayerUtil.fakeDamage();
    }

    public ScriptVector2 calculateRotations(ScriptVector3 to) {
        Vector2f calculated = RotationUtil.calculate(new Vector3d(to.getX().doubleValue(), to.getY().doubleValue(), to.getZ().doubleValue()));
        return new ScriptVector2(calculated.x, calculated.y);
    }

    public ScriptVector2 calculateRotations(ScriptEntity to) {
        return calculateRotations(to.getPosition());
    }

    @EventLink()
    public final Listener<TickEvent> onTick = event -> {
        if (this.wrapped == null) {
            this.wrapped = this.wrappedLiving = MC.thePlayer;
        }
    };
}
