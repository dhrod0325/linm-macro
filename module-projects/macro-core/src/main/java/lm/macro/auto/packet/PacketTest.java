package lm.macro.auto.packet;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.device.service.impl.LmAdbAndroidService;
import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
import lm.macro.auto.manager.device.LmConnectedDeviceManager;
import lm.macro.auto.manager.process.LmInMemoryAdbProcessManager;
import lm.macro.auto.packet.items.LmHpMpPacket;
import lm.macro.pcap.PacketHandler;

import java.util.List;

/**
 * Created by dhrod0325 on 2017-07-16.
 */
public class PacketTest {
    public static void main(String[] args) throws Exception {
        LmInMemoryAdbProcessManager adbProcessManager = new LmInMemoryAdbProcessManager();
        adbProcessManager.refresh();

        LmAdbAndroidService deviceService = new LmAdbAndroidService();
        List<LmAndroidDevice> deviceList = deviceService.getDeviceList();
        LmAndroidDevice device = deviceList.get(0);

        LmConnectedDeviceManager connectedDeviceManager = new LmConnectedDeviceManager();
        LmConnectedDeviceHolder holder = new LmConnectedDeviceHolder(device);
        holder.setAdbProcessManager(adbProcessManager);
        connectedDeviceManager.connect(holder);

        PacketHandler packetHandler = new PacketHandler();
        LmPcInstancePacketListener listen = new LmPcInstancePacketListener();
        listen.setConnectedDeviceManager(connectedDeviceManager);
        listen.addPacket(new LmHpMpPacket());

        packetHandler.addListener(listen);

        packetHandler.run();
    }
}
