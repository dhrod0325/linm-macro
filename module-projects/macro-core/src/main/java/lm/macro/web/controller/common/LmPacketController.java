package lm.macro.web.controller.common;

import lm.macro.pcap.PacketHandler;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/packet")
public class LmPacketController {

    @Resource
    private PacketHandler packetHandler;

    @Resource
    private TaskExecutor taskExecutor;

    @RequestMapping("/restart")
    public Object restart() throws Exception {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    packetHandler.restart();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        return 1;
    }
}
