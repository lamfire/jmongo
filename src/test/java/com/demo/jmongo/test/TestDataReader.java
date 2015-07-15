package com.demo.jmongo.test;

import com.lamfire.utils.RandomUtils;

public class TestDataReader implements Runnable{
	private int count = 1000;
	
	public TestDataReader(){
		
	}

	public TestDataReader(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public void run() {
		long begin = System.currentTimeMillis();
		for (int i=0;i<count;i++) {
			read();
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
