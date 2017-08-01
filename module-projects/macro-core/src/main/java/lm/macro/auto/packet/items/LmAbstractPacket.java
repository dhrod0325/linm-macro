package lm.macro.auto.packet.items;

import lm.macro.auto.manager.device.LmConnectedDeviceHolder;

import java.util.HashMap;
import java.util.Map;

public abstract class LmAbstractPacket implements LmBasePacket {
    public static int PACKET_NOT_FOUND = -9999;

    protected Map<Integer, Map<String, Integer>> valueMap = new HashMap<>();

    @Override
    public void onHandleServer(LmConnectedDeviceHolder holder, int opcode, byte[] byteData, byte[] data, int srcPort, int dstPort) {
        if (holder.getDevice() != null) {
            Map<String, Integer> packetData = getDataMapByPort(holder.getDevice().getPort());
            _handlePacket(data, holder, opcode, byteData, packetData, srcPort, dstPort);
        }
    }

    @Override
    public void onHandleClient(LmConnectedDeviceHolder holder, int opcode, byte[] byteData, byte[] data, int srcPort, int dstPort) {
    }

    public Map<String, Integer> getDataMapByPort(int port) {
        valueMap.computeIfAbsent(port, k -> new HashMap<>());

        return valueMap.get(port);
    }

    public void putValue(Map<String, Integer> map, String key, int value) {
        if (value != PACKET_NOT_FOUND) {
            map.put(key, value);
        }
    }

    public abstract int getItemCount(byte[] byteData, byte... searchByte);

    public Map<Integer, Map<String, Integer>> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<Integer, Map<String, Integer>> valueMap) {
        this.valueMap = valueMap;
    }

    public abstract void _handlePacket(byte[] data, LmConnectedDeviceHolder holder, int opcode, byte[] byteData, Map<String, Integer> packetData, int srcPort, int dstPort);


}
