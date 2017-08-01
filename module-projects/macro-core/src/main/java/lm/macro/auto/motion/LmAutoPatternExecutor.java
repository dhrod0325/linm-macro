package lm.macro.auto.motion;

import lm.macro.auto.log.LmLog;
import lm.macro.auto.object.instance.LmPcInstance;
import org.springframework.stereotype.Component;

@Component
public class LmAutoPatternExecutor {
    private LmLog logger = new LmLog(getClass());

    public void execute(LmPcInstance instance, LmAutoPattern autoPattern) throws Exception {
        long time = System.currentTimeMillis();

        if (time < autoPattern.getUseTime() + autoPattern.getDelayMilliSecond()) {
            return;
        }
        
        autoPattern.setValue1(instance.getHp());
        autoPattern.setValue2(instance.getMp());

        if (autoPattern.validate()) {
            String motionName = autoPattern.getMotion();

            instance.useSlotFromString(motionName);

            logger.trace(instance.getDevice().getPort() + " : " + autoPattern.getName() + "사용 , " + autoPattern.getDelay() + "초 대기", instance.getDevice());

            autoPattern.setUseTime(System.currentTimeMillis());
        }
    }
}