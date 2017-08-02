package napodev.framework.bework.restclient.utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by opannapo on 8/2/17.
 */
public class TimeoutAttr {
    private int value;
    private TimeUnit timeUnit;

    public TimeoutAttr(int val, TimeUnit unit) {
        this.value = val;
        this.timeUnit = unit;
    }

    public int getValue() {
        return value;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
