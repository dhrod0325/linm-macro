package lm.macro.pcap;

import lm.macro.pcap.util.PcapUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.TcpPacket;

import java.util.ArrayList;
import java.util.List;

public class PacketHandler {
    private final List<PcapHandleListener> listeners = new ArrayList<>();
    private PcapHandle pcapHandle;

    public void addListener(PcapHandleListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void removeListener(PcapHandleListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    public void run() throws Exception {
        PcapNetworkInterface nif = PcapUtils.getLocalLanPcapNetworkInterface();

        if (nif != null) {
            pcapHandle = nif.openLive(52277, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10);
            pcapHandle.loop(-1, this::handle);
        }
    }

    public void stop() throws NotOpenException {
        if (pcapHandle != null) {
            pcapHandle.close();
        }
    }

    public void restart() throws Exception {
        stop();

        run();
    }


    private int packetValidCheck(org.pcap4j.packet.Packet packet) {
        byte[] data = getData(packet);
        return data == null ? -1 : data[0] & 0xff;
    }

    private void handle(org.pcap4j.packet.Packet packet) {
        if (packet.contains(TcpPacket.class)) {
            TcpPacket tcpPkt = packet.get(TcpPacket.class);

            int op = packetValidCheck(tcpPkt);

            if (op == -1)
                return;

            int srcPort = tcpPkt.getHeader().getSrcPort().valueAsInt();
            int dstPort = tcpPkt.getHeader().getDstPort().valueAsInt();

            byte[] data = getData(packet);

            for (PcapHandleListener listener : listeners) {
                listener.listen(tcpPkt, data, srcPort, dstPort);
            }
        }
    }

    private byte[] getData(org.pcap4j.packet.Packet packet) {
        TcpPacket tcpPkt = packet.get(TcpPacket.class);

        if (tcpPkt.getPayload() == null)
            return null;

        ChannelBuffer readBuffer = ChannelBuffers.copiedBuffer(tcpPkt.getPayload().getRawData());
        int size = readBuffer.readableBytes();
        byte[] data = new byte[size];
        readBuffer.readBytes(data);

        return data;
    }
}