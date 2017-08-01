package lm.macro.auto.packet.items;

import lm.macro.auto.manager.device.LmConnectedDeviceHolder;

public interface LmBasePacket {
    void onHandleServer(LmConnectedDeviceHolder holder, int opcode, byte[] byteData, byte[] data, int srcPort, int dstPort);

    void onHandleClient(LmConnectedDeviceHolder holder, int opcode, byte[] byteData, byte[] data, int srcPort, int dstPort);
}
