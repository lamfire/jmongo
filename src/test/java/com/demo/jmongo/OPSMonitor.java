package com.demo.jmongo;

import com.lamfire.logger.Logger;
import com.lamfire.utils.Threads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-9-30
 * Time: 下午12:02
 * To change this template use File | Settings | File Templates.
 */
public class OPSMonitor implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(OPSMonitor.class);

    private AtomicInteger counter = new AtomicInteger(0);
    private int ops = 0;
    private int prevCount;
    private int interval = 1;


    @Override
    public void run() {
        int thisCount = counter.get();
        this.ops = thisCount - prevCount;
        this.prevCount = thisCount;
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("count=" + thisCount +",ops=" + ops + "/" + interval +"s");
        }
    }

    public void increment(){
        counter.incrementAndGet();
    }

    public int getOps(){
        return ops;
    }

    public int getCount(){
        return this.counter.get();
    }

    public void setInterval(int interval){
        this.interval = interval;
    }

    public void startup(){
        Threads.scheduleWithFixedDelay(this, interval, interval, TimeUnit.SECONDS);
    }

    public void shutdown(){
        Threads.removeScheduledTask(this);
    }
}
