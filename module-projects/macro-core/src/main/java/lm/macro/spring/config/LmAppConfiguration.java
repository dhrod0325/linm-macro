package lm.macro.spring.config;

import lm.macro.auto.android.screen.LmAbstractAndroidScreen;
import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.android.screen.LmLocalAndroidScreen;
import lm.macro.auto.packet.LmPcInstancePacketListener;
import lm.macro.auto.packet.items.LmBagPacket;
import lm.macro.auto.packet.items.LmItemUsePacket;
import lm.macro.pcap.PacketHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class LmAppConfiguration {
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setQueueCapacity(25);

        return threadPoolTaskExecutor;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(2);
        return threadPoolTaskScheduler;
    }

    @Bean
    public LmBagPacket bagPacket() {
        return new LmBagPacket();
    }

    @Bean
    public LmItemUsePacket itemUsePacket() {
        return new LmItemUsePacket();
    }

    @Bean
    public LmPcInstancePacketListener pcInstancePacketListener() {
        LmPcInstancePacketListener listen = new LmPcInstancePacketListener();
        listen.addPacket(itemUsePacket());
        listen.addPacket(bagPacket());

        return listen;
    }

    @Bean
    public PacketHandler packetHandler() {
        PacketHandler packetHandler = new PacketHandler();
        packetHandler.addListener(pcInstancePacketListener());

        taskExecutor().execute(() -> {
            try {
                packetHandler().run();
            } catch (Exception e) {
//                System.out.println(e.getMessage());
            }
        });

        return packetHandler;
    }

    @Bean(name = "teleportScreen")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public LmAndroidScreen teleportScreen() {
        return createBean("teleportScreen.png");
    }

    @Bean(name = "initScreen")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public LmAndroidScreen initScreen() {
        return createBean("initScreen.png");
    }

    @Bean(name = "npcShopScreen")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public LmAndroidScreen npcShopScreen() {
        return createBean("npcShopScreen.png");
    }

    @Bean(name = "captureScreen")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public LmAndroidScreen captureScreen() {
        return createBean("captureScreen.png");
    }

    @Bean(name = "buyItemScreen")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public LmAndroidScreen buyItemScreen() {
        return createBean("buyItemScreen.png");
    }

    private LmAndroidScreen createBean(String fileName) {
        LmAbstractAndroidScreen screen = new LmLocalAndroidScreen();
        screen.setFileName(fileName);
        return screen;
    }
}