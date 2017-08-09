package lm.macro.pcap;

import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
import lm.macro.auto.packet.items.LmBasePacket;

public class LmTestPacket implements LmBasePacket {
    private int OPCODE_MOVE = 150;
    private int OPCODE_ITEM_USE = 12;
    private int OPCODE_MONSTER_ATTACK = 402;

    private Integer[] IGNORE_PACKET = {106, 103, 116, 150};

    @Override
    public void onHandleServer(LmConnectedDeviceHolder holder, int opcode, byte[] byteData, byte[] data, int srcPort, int dstPort) {
    }

    @Override
    public void onHandleClient(LmConnectedDeviceHolder holder, int opcode, byte[] byteData, byte[] data, int srcPort, int dstPort) {

    }
}
