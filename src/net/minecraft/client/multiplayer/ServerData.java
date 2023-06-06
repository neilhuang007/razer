package net.minecraft.client.multiplayer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class ServerData {
    public String serverName;
    public String serverIP;

    /**
     * the string indicating number of players on and capacity of the server that is shown on the server browser (i.e.
     * "5/20" meaning 5 slots used out of 20 slots total)
     */
    public String populationInfo;

    /**
     * (better variable name would be 'hostname') server name as displayed in the server browser's second line (grey
     * text)
     */
    public String serverMOTD;

    /**
     * last server ping that showed up in the server browser
     */
    public long pingToServer;
    public int version = 47;

    /**
     * Game version for this server.
     */
    public String gameVersion = "1.8.9";
    public boolean field_78841_f;
    public String playerList;
    private ServerResourceMode resourceMode = ServerResourceMode.PROMPT;
    private String serverIcon;
    private boolean field_181042_l;

    public ServerData(final String serverName, final String serverIp, final boolean isLan) {
        this.serverName = serverName;
        this.serverIP = serverIp;
        this.field_181042_l = isLan;
    }

    /**
     * Returns an NBTTagCompound with the server's name, IP and maybe acceptTextures.
     */
    public NBTTagCompound getNBTCompound() {
        final NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setString("name", this.serverName);
        nbttagcompound.setString("ip", this.serverIP);

        if (this.serverIcon != null) {
            nbttagcompound.setString("icon", this.serverIcon);
        }

        if (this.resourceMode == ServerResourceMode.ENABLED) {
            nbttagcompound.setBoolean("acceptTextures", true);
        } else if (this.resourceMode == ServerResourceMode.DISABLED) {
            nbttagcompound.setBoolean("acceptTextures", false);
        }

        return nbttagcompound;
    }

    public ServerResourceMode getResourceMode() {
        return this.resourceMode;
    }

    public void setResourceMode(final ServerResourceMode mode) {
        this.resourceMode = mode;
    }

    /**
     * Takes an NBTTagCompound with 'name' and 'ip' keys, returns a ServerData instance.
     */
    public static ServerData getServerDataFromNBTCompound(final NBTTagCompound nbtCompound) {
        final ServerData serverdata = new ServerData(nbtCompound.getString("name"), nbtCompound.getString("ip"), false);

        if (nbtCompound.hasKey("icon", 8)) {
            serverdata.setBase64EncodedIconData(nbtCompound.getString("icon"));
        }

        if (nbtCompound.hasKey("acceptTextures", 1)) {
            if (nbtCompound.getBoolean("acceptTextures")) {
                serverdata.setResourceMode(ServerResourceMode.ENABLED);
            } else {
                serverdata.setResourceMode(ServerResourceMode.DISABLED);
            }
        } else {
            serverdata.setResourceMode(ServerResourceMode.PROMPT);
        }

        return serverdata;
    }

    /**
     * Returns the base-64 encoded representation of the server's icon, or null if not available
     */
    public String getBase64EncodedIconData() {
        return this.serverIcon;
    }

    public void setBase64EncodedIconData(final String icon) {
        this.serverIcon = icon;
    }

    public boolean func_181041_d() {
        return this.field_181042_l;
    }

    public void copyFrom(final ServerData serverDataIn) {
        this.serverIP = serverDataIn.serverIP;
        this.serverName = serverDataIn.serverName;
        this.setResourceMode(serverDataIn.getResourceMode());
        this.serverIcon = serverDataIn.serverIcon;
        this.field_181042_l = serverDataIn.field_181042_l;
    }

    public enum ServerResourceMode {
        ENABLED("enabled"),
        DISABLED("disabled"),
        PROMPT("prompt");

        private final IChatComponent motd;

        ServerResourceMode(final String p_i1053_3_) {
            this.motd = new ChatComponentTranslation("addServer.resourcePack." + p_i1053_3_);
        }

        public IChatComponent getMotd() {
            return this.motd;
        }
    }
}
