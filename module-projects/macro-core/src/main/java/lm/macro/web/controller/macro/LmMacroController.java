package lm.macro.web.controller.macro;

import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
import lm.macro.auto.manager.device.LmConnectedDeviceManager;
import lm.macro.auto.object.instance.LmPcState;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/macro")
public class LmMacroController {
    @Resource
    private LmConnectedDeviceManager connectedDeviceManager;

    @RequestMapping("/startMacro")
    public LmConnectedDeviceHolder startMacro(@RequestParam("port") int port) {
        LmConnectedDeviceHolder device = connectedDeviceManager.getConnectedDeviceByPort(port);
        device.getPcInstance().setState(LmPcState.PLAY);
        return device;
    }

    @RequestMapping("/stopMacro")
    public LmConnectedDeviceHolder stopMacro(@RequestParam("port") int port) {
        LmConnectedDeviceHolder device = connectedDeviceManager.getConnectedDeviceByPort(port);
        device.getPcInstance().setState(LmPcState.STOP);

        return device;
    }
}
