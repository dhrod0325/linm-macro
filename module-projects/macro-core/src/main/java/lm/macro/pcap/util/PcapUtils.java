package lm.macro.pcap.util;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;

public class PcapUtils {
    public static void main(String[] args) throws Exception {
        List<PcapNetworkInterface> nifs = Pcaps.findAllDevs();
//        PcapNetworkInterface nif = nifs.get(1);
//        if (nif != null) {
//            PcapHandle pcapHandle = nif.openLive(52277, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10);
//            pcapHandle.loop(-1, new PacketListener() {
//                @Override
//                public void gotPacket(Packet packet) {
//                    TcpPacket tcpPkt = packet.get(TcpPacket.class);
//
//                    if (tcpPkt != null) {
//                        int srcPort = tcpPkt.getHeader().getSrcPort().valueAsInt();
//                        int dstPort = tcpPkt.getHeader().getDstPort().valueAsInt();
//                        System.out.println(srcPort + "," + dstPort);
//                    }
//                }
//            });
//        }

        for(PcapNetworkInterface pcapNetworkInterface : nifs){
            System.out.println(getLocalHostLANAddress());
        }
    }

    public static PcapNetworkInterface getLocalLanPcapNetworkInterface() throws Exception {
        String localAddress = getLocalHostLANAddress().toString();
        List<PcapNetworkInterface> nifs = Pcaps.findAllDevs();

        for (PcapNetworkInterface nif : nifs) {
            List<PcapAddress> addresses = nif.getAddresses();
            for (PcapAddress address : addresses) {
                String pcapNifAddress = address.getAddress().toString();

                if (pcapNifAddress.equals(localAddress)) {
                    return nif;
                }
            }
        }

        if (nifs.size() > 0) {
            return nifs.get(0);
        }

        return null;
    }

    public static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

}
