package lm.macro.pcap;

import org.pcap4j.packet.TcpPacket;

/**
 * Created by dhrod0325 on 2017-07-09.
 */
public interface PcapHandleListener {
    void listen(TcpPacket tcpPkt, byte[] data, int srcPort, int dstPort);
}
