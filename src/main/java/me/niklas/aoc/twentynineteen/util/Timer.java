package me.niklas.aoc.twentynineteen.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Niklas on 01.12.2019 in twentynineteen
 */
public class Timer {

    private static final Timer instance = new Timer();
    private final AtomicLong start = new AtomicLong();

    public static Timer instance() {
        return instance;
    }

    public void start() {
        start.set(System.currentTimeMillis());
    }

    public long stop() {
        return System.currentTimeMillis() - start.getAndSet(System.currentTimeMillis());
    }
}
