package me.niklas.aoc.twentynineteen.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Niklas on 01.12.2019 in twentynineteen
 */
public class Timer {

    private static final Timer instance = new Timer();
    public static Timer instance() {
        return instance;
    }

    private final AtomicLong start = new AtomicLong();

    public Timer() {
        start();
    }

    public void start() {
        start.set(System.currentTimeMillis());
    }

    public long stop() {
        return System.currentTimeMillis() - start.getAndSet(System.currentTimeMillis());
    }

    public long stopNoReset() {
        return System.currentTimeMillis() - start.get();
    }
}
