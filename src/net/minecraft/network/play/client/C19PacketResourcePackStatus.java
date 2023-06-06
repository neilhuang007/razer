package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.io.IOException;

public class C19PacketResourcePackStatus implements Packet<INetHandlerPlayServer> {
    private String hash;
    private Action status;

    public C19PacketResourcePackStatus() {
    }

    public C19PacketResourcePackStatus(String hashIn, final Action statusIn) {
        if (hashIn.length() > 40) {
            hashIn = hashIn.substring(0, 40);
        }

        this.hash = hashIn;
        this.status = statusIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(final PacketBuffer buf) throws IOException {
        this.hash = buf.readStringFromBuffer(40);
        this.status = buf.readEnumValue(Action.class);
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(final PacketBuffer buf) throws IOException {
        buf.writeString(this.hash);
        buf.writeEnumValue(this.status);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(final INetHandlerPlayServer handler) {
        handler.handleResourcePackStatus(this);
    }

    public enum Action {
        SUCCESSFULLY_LOADED,
        DECLINED,
        FAILED_DOWNLOAD,
        ACCEPTED
    }
}
