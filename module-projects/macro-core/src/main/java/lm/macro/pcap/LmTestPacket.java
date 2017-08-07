package lm.macro.pcap;

import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
import lm.macro.auto.packet.items.LmBasePacket;

import java.util.Arrays;

public class LmTestPacket implements LmBasePacket {
    @Override
    public void onHandleServer(LmConnectedDeviceHolder holder, int opcode, byte[] byteData, byte[] data, int srcPort, int dstPort) {
    }

    @Override
    public void onHandleClient(LmConnectedDeviceHolder holder, int opcode, byte[] byteData, byte[] data, int srcPort, int dstPort) {

    }
}
