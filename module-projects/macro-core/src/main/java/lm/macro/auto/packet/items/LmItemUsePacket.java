package lm.macro.auto.packet.items;

import com.google.common.primitives.Bytes;
import lm.macro.auto.common.LmCommon;
import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

import static lm.macro.auto.packet.LmPacketUtils.read2ByteData;

@Component
public class LmItemUsePacket extends LmAbstractPacket {
    public int getItemCount(byte[] byteData, int validate, byte... searchByte) {
        if (searchByte.length == 0)
            return PACKET_NOT_FOUND;

        int pos = Bytes.indexOf(byteData, searchByte);

        if (pos != -1) {
            int search = pos + searchByte.length;
            byte a = byteData[search];
            byte b = byteData[search + 1];

            int result;

            if (b == 56) {
                result = read2ByteData(new byte[]{a, 1});
            } else {
                result = read2ByteData(new byte[]{a, b});
            }

            if (result < validate) {
                return result;
            }
        }

        return PACKET_NOT_FOUND;
    }

    @Override
    public int getItemCount(byte[] byteData, byte... searchByte) {
        return getItemCount(byteData, 10000, searchByte);
    }

    @Override
    public void _handlePacket(byte[] data, LmConnectedDeviceHolder holder, int opcode, byte[] byteData, Map<String, Integer> packetData, int srcPort, int dstPort) {
        if(byteData.length <150){
            int 빨간물약 = getItemCount(data, new byte[]{40, -23, 1, 48});
            putValue(packetData, LmCommon.빨간물약, 빨간물약);
        }

        int 주홍물약 = getItemCount(data, new byte[]{40, -19, 1, 48});
        int 초록물약 = getItemCount(data, new byte[]{40, -4, 2, 48});
        int 강화초록물약 = getItemCount(data, new byte[]{40, -21, 1, 48});
        int 파란물약 = getItemCount(data, new byte[]{40, -127, 3, 48});
        int 변신주문서 = getItemCount(data, new byte[]{40, -91, 3, 48});
        int 인형소환주문서 = getItemCount(data, new byte[]{40, -52, 6, 48});
        int 와퍼 = getItemCount(data, new byte[]{40, -63, 2, 48});
        int 은화살 = getItemCount(data, 50000, new byte[]{40, -47, 2, 48});

        int 순간이동주문서 = getItemCount(data, new byte[]{40, -71, 2, 48});
        int 귀환주문서 = getItemCount(data, new byte[]{40, -17, 1, 48});
        int 용기의물약 = getItemCount(data, new byte[]{40, -49, 2, 48});
        int 철괴 = getItemCount(data, new byte[]{40, -5, 2, 48});
        int 힘센한우스테이크 = getItemCount(data, new byte[]{40, -41, 6, 48});
        int 해독제 = getItemCount(data, new byte[]{40, -74, 2, 48});
        int 용해제 = getItemCount(data, new byte[]{40, -73, 1, 48});
        int 진주 = getItemCount(data, new byte[]{40, -9, 6, 48});
        int 날쌘연어찜 = getItemCount(data, new byte[]{40, -40, 6, 48});
        int 영리한칠면조 = getItemCount(data, new byte[]{40, -39, 6, 48});
        int 에바의축복 = getItemCount(data, new byte[]{40, -24, 8, 48});
        int 드레곤의다이아몬드 = getItemCount(data, new byte[]{40, -107, 50, 48});

        putValue(packetData, LmCommon.주홍물약, 주홍물약);
        putValue(packetData, LmCommon.초록물약, 초록물약);
        putValue(packetData, LmCommon.강화초록물약, 강화초록물약);
        putValue(packetData, LmCommon.파란물약, 파란물약);
        putValue(packetData, LmCommon.변신주문서, 변신주문서);
        putValue(packetData, LmCommon.인형소환주문서, 인형소환주문서);
        putValue(packetData, LmCommon.와퍼, 와퍼);
        putValue(packetData, LmCommon.은화살, 은화살);

        putValue(packetData, LmCommon.순간이동주문서, 순간이동주문서);
        putValue(packetData, LmCommon.귀환주문서, 귀환주문서);
        putValue(packetData, LmCommon.용기의물약, 용기의물약);
        putValue(packetData, LmCommon.철괴, 철괴);
        putValue(packetData, LmCommon.힘센한우스테이크, 힘센한우스테이크);
        putValue(packetData, LmCommon.해독제, 해독제);
        putValue(packetData, LmCommon.용해제, 용해제);
        putValue(packetData, LmCommon.진주, 진주);
        putValue(packetData, LmCommon.날쌘연어찜, 날쌘연어찜);
        putValue(packetData, LmCommon.영리한칠면조, 영리한칠면조);
        putValue(packetData, LmCommon.에바의축복, 에바의축복);
        putValue(packetData, LmCommon.드레곤의다이아몬드, 드레곤의다이아몬드);

//        if (opcode == 201) {
//            System.out.println(opcode + "," + Arrays.toString(byteData));
//        }


    }

    @Override
    public void onHandleServer(LmConnectedDeviceHolder holder, int opcode, byte[] byteData, byte[] data, int srcPort, int dstPort) {
        super.onHandleServer(holder, opcode, byteData, data, srcPort, dstPort);
    }
}


