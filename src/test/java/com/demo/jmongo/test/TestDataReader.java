package com.demo.jmongo.test;

import com.lamfire.utils.RandomUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class TestDataReader implements Runnable{
	private AtomicInteger count = new AtomicInteger();
	
	public TestDataReader(){
		
	}

	public int getCount() {
		return count.get();
	}

	@Override
	public void run() {
		long begin = System.currentTimeMillis();
		while (true) {
			read();
            int i = count.getAndIncrement();
			if (i % 100 == 0) {
				System.out.println("[QUERY (" + i + ")]: time:" + (System.currentTimeMillis() - begin));
				begin = System.currentTimeMillis();
			}
		}
	}
	
	private void read(){
		try{
			HotelTest.findAgeGreater(RandomUtils.nextInt(100), RandomUtils.nextInt(9999), 100);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
