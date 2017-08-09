package lm.macro.pcap;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.device.service.impl.LmAdbAndroidService;
import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
import lm.macro.auto.manager.device.LmConnectedDeviceManager;
import lm.macro.auto.manager.process.LmInMemoryAdbProcessManager;
import lm.macro.auto.packet.LmPcInstancePacketListener;
import lm.macro.auto.packet.items.LmBagPacket;
import lm.macro.auto.packet.items.LmItemUsePacket;

import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        LmInMemoryAdbProcessManager adbProcessManager = new LmInMemoryAdbProcessManager();
        adbProcessManager.refresh();

        LmAdbAndroidService deviceService = new LmAdbAndroidService();
        List<LmAndroidDevice> deviceList = deviceService.getDeviceList();
        LmAndroidDevice device = null;

//        device = deviceList.get(0);
//
        for (LmAndroidDevice d : deviceList) {
            if (d.getPort() == 5565) {
                device = d;
            }
        }

        System.out.println(device.getPort());

        PacketHandler packetHandler = new PacketHandler();

        LmPcInstancePacketListener listen = new LmPcInstancePacketListener();
        listen.addPacket(new LmItemUsePacket());
        listen.addPacket(new LmBagPacket());
        listen.addPacket(new LmTestPacket());

        LmConnectedDeviceHolder holder = new LmConnectedDeviceHolder(device);
        holder.setAdbProcessManager(adbProcessManager);

        LmConnectedDeviceManager connectedDeviceManager = new LmConnectedDeviceManager();
        connectedDeviceManager.connect(holder);

        listen.setConnectedDeviceManager(connectedDeviceManager);

        packetHandler.addListener(listen);
        packetHandler.run();
    }
}
