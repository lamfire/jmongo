package com.demo.jmongo.test;

import java.util.concurrent.atomic.AtomicInteger;

public class TestDataWriter implements Runnable{
	private static AtomicInteger count = new AtomicInteger();
	
	public TestDataWriter(){
		
	}

	public int getCount() {
		return count.get();
	}


	@Override
	public void run() {
		long begin = System.currentTimeMillis();
		while (true) {
            int i = count.getAndIncrement();
			write();
			if (i % 10000 == 0) {
				System.out.println("[WRITE (" + i + ")]: time:" + (System.currentTimeMillis() - begin));
				begin = System.currentTimeMillis();
			}
		}
	}
	
	private void write(){
		try{
			HotelTest.insertRandom();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
