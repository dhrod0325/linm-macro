package lm.macro.auto.packet.items;

import com.google.common.primitives.Bytes;
import lm.macro.auto.common.LmCommon;
import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

import static lm.macro.auto.packet.LmPacketUtils.read2ByteData;

@Component
public class LmBagPacket extends LmAbstractPacket {
    @Override
    public int getItemCount(byte[] byteData, byte... searchByte) {
        if (searchByte.length == 0)
            return PACKET_NOT_FOUND;

        int pos = Bytes.indexOf(byteData, searchByte);

        if (pos != -1) {
            int search = pos + searchByte.length;
            byte a = byteData[search];
            return read2ByteData(new byte[]{a, 1});
        }

        return PACKET_NOT_FOUND;
    }

    @Override
    public void _handlePacket(byte[] data, LmConnectedDeviceHolder holder, int opcode, byte[] byteData, Map<String, Integer> packetData, int srcPort, int dstPort) {
        int 가방무게 = getItemCount(data, new byte[]{16, 2, 24});

        if (가방무게 != PACKET_NOT_FOUND) {
            putValue(packetData, LmCommon.가방무게, 가방무게);
        }
    }

    @Override
    public void onHandleClient(LmConnectedDeviceHolder holder, int opcode, byte[] byteData, byte[] data, int srcPort, int dstPort) {

    }
}
