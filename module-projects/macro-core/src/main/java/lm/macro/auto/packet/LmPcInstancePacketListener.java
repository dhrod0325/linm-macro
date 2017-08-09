package lm.macro.auto.packet;

import lm.macro.auto.data.model.netstat.LmNetstatProcessHolder;
import lm.macro.auto.log.LmLog;
import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
import lm.macro.auto.manager.device.LmConnectedDeviceManager;
import lm.macro.auto.object.instance.LmPcInstance;
import lm.macro.auto.packet.items.LmBasePacket;
import lm.macro.auto.utils.LmTimeChecker;
import lm.macro.auto.utils.cmd.Netstat;
import lm.macro.pcap.Packet;
import lm.macro.pcap.PcapHandleListener;
import org.pcap4j.packet.TcpPacket;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LmPcInstancePacketListener implements PcapHandleListener {
    public static final int SNIFF_PORT = 12000;

    private LmTimeChecker netstatRefreshTimeChecker = new LmTimeChecker();

    private LmLog logger = new LmLog(getClass());

    private LmConnectedDeviceManager connectedDeviceManager;

    private Map<Integer, LmNetstatProcessHolder> netstatProcessHolderMap = new HashMap<>();

    private List<LmBasePacket> packetList = new ArrayList<>();

    public void addPacket(LmBasePacket packet) {
        packetList.add(packet);
    }

    @Resource
    public void setConnectedDeviceManager(LmConnectedDeviceManager connectedDeviceManager) {
        this.connectedDeviceManager = connectedDeviceManager;
    }

    @Override
    public void listen(TcpPacket tcpPkt, byte[] data, int srcPort, int dstPort) {
        Packet clientPacket = new Packet().data(data, data.length);

        int packetLength;
        byte[] byteData;
        int opcode;

        try {
            packetLength = clientPacket.readD();
            opcode = clientPacket.readD();
            byteData = clientPacket.getCurrentReadableBytes();

            if (opcode == 3)
                return;
        } catch (Exception e) {
            return;
        }

        if (dstPort == SNIFF_PORT || srcPort == SNIFF_PORT) {
            if (srcPort == SNIFF_PORT) {
                if (connectedDeviceManager != null) {
                    for (LmConnectedDeviceHolder holder : connectedDeviceManager.getConnectedDeviceList()) {
                        if (holder.getAdbProcess() != null) {
                            boolean isRefreshTime = !netstatRefreshTimeChecker.isWaitTime(System.currentTimeMillis(), 3);
                            if (holder.getNetstatProcessHolder() == null || isRefreshTime) {
                                holder.loadNetstat();
                            }

                            List<Netstat> netstatList = holder.getNetstatProcessHolder().getNetstatList();

                            if (netstatList == null) {
                                return;
                            }

                            for (Netstat netstat : netstatList) {
                                if (netstat == null) {
                                    continue;
                                }

                                try {
                                    URI uri = new URI("lm://" + netstat.getSrc());

                                    if (uri.getPort() == dstPort) {
                                        packetHandle(holder, opcode, packetLength, byteData, data, srcPort, dstPort);
                                    }
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }

            //클라에서 보낸패킷

        }
    }

    private void packetHandle(LmConnectedDeviceHolder holder, int opcode, int packetLength, byte[] byteData, byte[] data, int srcPort, int dstPort) {
        for (LmBasePacket packet : packetList) {
            if (srcPort == SNIFF_PORT) {
                packet.onHandleServer(holder, opcode, byteData, data, srcPort, dstPort);
            }

            if (dstPort == SNIFF_PORT) {
                packet.onHandleClient(holder, opcode, byteData, data, srcPort, dstPort);
            }
        }

//        if (opcode == 12) {
//            if (packetLength >= 65
//                    && packetLength <= 75
//                    && byteData.length > 60
//                    && byteData.length < 70) {
//                logger.debug("HP,MP 패킷 수신 1");
//                calcHpAndMp(byteData, 12, srcPort, dstPort);
//                return;
//            }
//
//            if (byteData.length >= 165 && byteData.length <= 177) {
//                logger.debug("HP,MP 패킷 수신 2");
//                calcHpAndMp(byteData, 68, srcPort, dstPort);
//            }
//        }
    }

    public void calcHpAndMp(LmPcInstance instance) {
//        try {
//            if (hpAndMpPacketBean != null) {
//                int hp = (int) ((double) hpAndMpPacketBean.hp / (double) hpAndMpPacketBean.maxHp * 100);
//                int mp = (int) ((double) hpAndMpPacketBean.mp / (double) hpAndMpPacketBean.maxMp * 100);
//
//                instance.setHp(hp);
//                instance.setMp(mp);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) {
        byte[] data = new byte[]{-3, -48, -106, -6};
        Packet p = new Packet().data(data);
    }
}
