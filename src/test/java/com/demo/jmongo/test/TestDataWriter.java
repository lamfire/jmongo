package com.demo.jmongo.test;

public class TestDataWriter implements Runnable{
	private int count = 1000;
	
	public TestDataWriter(){
		
	}

	public TestDataWriter(int count) {
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
