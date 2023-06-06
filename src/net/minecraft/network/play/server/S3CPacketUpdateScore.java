package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;

import java.io.IOException;

public class S3CPacketUpdateScore implements Packet<INetHandlerPlayClient> {
    private String name = "";
    private String objective = "";
    private int value;
    private Action action;

    public S3CPacketUpdateScore() {
    }

    public S3CPacketUpdateScore(final Score scoreIn) {
        this.name = scoreIn.getPlayerName();
        this.objective = scoreIn.getObjective().getName();
        this.value = scoreIn.getScorePoints();
        this.action = Action.CHANGE;
    }

    public S3CPacketUpdateScore(final String nameIn) {
        this.name = nameIn;
        this.objective = "";
        this.value = 0;
        this.action = Action.REMOVE;
    }

    public S3CPacketUpdateScore(final String nameIn, final ScoreObjective objectiveIn) {
        this.name = nameIn;
        this.objective = objectiveIn.getName();
        this.value = 0;
        this.action = Action.REMOVE;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(final PacketBuffer buf) throws IOException {
        this.name = buf.readStringFromBuffer(40);
        this.action = buf.readEnumValue(Action.class);
        this.objective = buf.readStringFromBuffer(16);

        if (this.action != Action.REMOVE) {
            this.value = buf.readVarIntFromBuffer();
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(final PacketBuffer buf) throws IOException {
        buf.writeString(this.name);
        buf.writeEnumValue(this.action);
        buf.writeString(this.objective);

        if (this.action != Action.REMOVE) {
            buf.writeVarIntToBuffer(this.value);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(final INetHandlerPlayClient handler) {
        handler.handleUpdateScore(this);
    }

    public String getPlayerName() {
        return this.name;
    }

    public String getObjectiveName() {
        return this.objective;
    }

    public int getScoreValue() {
        return this.value;
    }

    public Action getScoreAction() {
        return this.action;
    }

    public enum Action {
        CHANGE,
        REMOVE
    }
}
