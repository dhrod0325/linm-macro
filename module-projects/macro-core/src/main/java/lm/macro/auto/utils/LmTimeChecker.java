package lm.macro.auto.utils;

import java.util.concurrent.TimeUnit;

public class LmTimeChecker {
    private long time;

    /**
     * 대기해야 하는 시간인지 확인한다.
     *
     * @param targetTime 밀리세컨드 단위 입력
     * @param wait       기다릴 시간 초단위 입력
     * @return 대기해야되는 시간이라면 true
     */
    public boolean isWaitTime(long targetTime, long wait) {
        return isWaitTime(targetTime, wait, true);
    }

    /**
     * 대기해야 하는 시간인지 확인한다.
     *
     * @param targetTime  밀리세컨드 단위 입력
     * @param wait        기다릴 시간 초단위 입력
     * @param autoRefresh 기다려야 할 시간이 지났다면 자동으로 체크 시간 갱신 여부
     * @return 대기해야되는 시간이라면 true
     */
    public boolean isWaitTime(long targetTime, long wait, boolean autoRefresh) {
        long _time = time + TimeUnit.SECONDS.toMillis(wait);

        boolean result = targetTime < _time;

        if (autoRefresh && !result) {
            refreshTime();
        }

        return result;
    }

    private void refreshTime() {
        setTime(System.currentTimeMillis());
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
