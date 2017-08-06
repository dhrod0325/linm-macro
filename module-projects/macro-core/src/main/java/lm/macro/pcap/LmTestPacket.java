package lm.macro.pcap;

import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
import lm.macro.auto.packet.items.LmBasePacket;

import java.util.Arrays;

public class LmTestPacket implements LmBasePacket {
    @Override
    public void onHandleServer(LmConnectedDeviceHolder holder, int opcode, byte[] byteData, byte[] data, int srcPort, int dstPort) {
        if (opcode != 106 && opcode != 402) {
            if (opcode == 12) {
                System.out.println(opcode + "," + Arrays.toString(byteData));
            }

        }
    }

    @Override
    public void onHandleClient(LmConnectedDeviceHolder holder, int opcode, byte[] byteData, byte[] data, int srcPort, int dstPort) {

    }
}
