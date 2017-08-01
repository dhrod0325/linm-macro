package lm.macro.auto.packet.items;

import com.google.common.primitives.Bytes;
import lm.macro.auto.manager.device.LmConnectedDeviceHolder;

import java.util.Arrays;
import java.util.Map;

import static lm.macro.auto.packet.LmPacketUtils.read2ByteData;

/**
 * Created by dhrod0325 on 2017-07-20.
 */
public class LmHpMpPacket extends LmAbstractPacket {
    @Override
    public int getItemCount(byte[] byteData, byte... searchByte) {
        return 0;
    }

    @Override
    public void _handlePacket(byte[] data, LmConnectedDeviceHolder holder, int opcode, byte[] byteData, Map<String, Integer> packetData, int srcPort, int dstPort) {
    }

    @Override
    public void onHandleServer(LmConnectedDeviceHolder holder, int opcode, byte[] byteData, byte[] data, int srcPort, int dstPort) {
        super.onHandleServer(holder, opcode, byteData, data, srcPort, dstPort);

        if (opcode == 12) {
            if (data.length > 65 && data.length < 80)
                calcHpAndMp(data);
        }
    }

    public void calcHpAndMp(byte[] byteData) {
        byte[] b3 = Arrays.copyOfRange(byteData, 0, byteData.length);

        int hp;
        int mp;
        int maxHp;
        int maxMp;

        int off = Bytes.indexOf(b3, new byte[]{40}) - 2;
        int maxHpOff = Bytes.indexOf(b3, new byte[]{32}) - 2;

        maxHp = read2ByteData(new byte[]{b3[maxHpOff], b3[maxHpOff + 1]});

        if (b3[off + 2] == 40) {
            hp = read2ByteData(new byte[]{b3[off], b3[off + 1]});
            off += 2;
        } else {
            hp = read2ByteData(new byte[]{b3[off], 1});
            off++;
        }

        off += 4;

        maxMp = read2ByteData(new byte[]{b3[off - 3], b3[off - 2]});

        if (b3[off + 2] == 56) {
            mp = read2ByteData(new byte[]{b3[off], b3[off + 1]});
        } else if (b3[off] == 56) {
            mp = read2ByteData(new byte[]{b3[off - 1], 1});
        } else {
            mp = read2ByteData(new byte[]{b3[off], 1});
        }
    }
}
