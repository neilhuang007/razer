package net.minecraft.client.network;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LanServerDetector {
    private static final AtomicInteger field_148551_a = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();

    public static class LanServer {
        private final String lanServerMotd;
        private final String lanServerIpPort;
        private long timeLastSeen;

        public LanServer(final String motd, final String address) {
            this.lanServerMotd = motd;
            this.lanServerIpPort = address;
            this.timeLastSeen = Minecraft.getSystemTime();
        }

        public String getServerMotd() {
            return this.lanServerMotd;
        }

        public String getServerIpPort() {
            return this.lanServerIpPort;
        }

        public void updateLastSeen() {
            this.timeLastSeen = Minecraft.getSystemTime();
        }
    }

    public static class LanServerList {
        private final List<LanServer> listOfLanServers = Lists.newArrayList();
        boolean wasUpdated;

        public synchronized boolean getWasUpdated() {
            return this.wasUpdated;
        }

        public synchronized void setWasNotUpdated() {
            this.wasUpdated = false;
        }

        public synchronized List<LanServer> getLanServers() {
            return Collections.unmodifiableList(this.listOfLanServers);
        }

        public synchronized void func_77551_a(final String p_77551_1_, final InetAddress p_77551_2_) {
            final String s = ThreadLanServerPing.getMotdFromPingResponse(p_77551_1_);
            String s1 = ThreadLanServerPing.getAdFromPingResponse(p_77551_1_);

            if (s1 != null) {
                s1 = p_77551_2_.getHostAddress() + ":" + s1;
                boolean flag = false;

                for (final LanServer lanserverdetector$lanserver : this.listOfLanServers) {
                    if (lanserverdetector$lanserver.getServerIpPort().equals(s1)) {
                        lanserverdetector$lanserver.updateLastSeen();
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    this.listOfLanServers.add(new LanServer(s, s1));
                    this.wasUpdated = true;
                }
            }
        }
    }

    public static class ThreadLanServerFind extends Thread {
        private final LanServerList localServerList;
        private final InetAddress broadcastAddress;
        private final MulticastSocket socket;

        public ThreadLanServerFind(final LanServerList p_i1320_1_) throws IOException {
            super("LanServerDetector #" + LanServerDetector.field_148551_a.incrementAndGet());
            this.localServerList = p_i1320_1_;
            this.setDaemon(true);
            this.socket = new MulticastSocket(4445);
            this.broadcastAddress = InetAddress.getByName("224.0.2.60");
            this.socket.setSoTimeout(5000);
            this.socket.joinGroup(this.broadcastAddress);
        }

        public void run() {
            final byte[] abyte = new byte[1024];

            while (!this.isInterrupted()) {
                final DatagramPacket datagrampacket = new DatagramPacket(abyte, abyte.length);

                try {
                    this.socket.receive(datagrampacket);
                } catch (final SocketTimeoutException var5) {
                    continue;
                } catch (final IOException ioexception) {
                    LanServerDetector.logger.error("Couldn't ping server", ioexception);
                    break;
                }

                final String s = new String(datagrampacket.getData(), datagrampacket.getOffset(), datagrampacket.getLength());
                LanServerDetector.logger.debug(datagrampacket.getAddress() + ": " + s);
                this.localServerList.func_77551_a(s, datagrampacket.getAddress());
            }

            try {
                this.socket.leaveGroup(this.broadcastAddress);
            } catch (final IOException var4) {
            }

            this.socket.close();
        }
    }
}
