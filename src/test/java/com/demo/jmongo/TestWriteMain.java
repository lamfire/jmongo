package com.demo.jmongo;

import com.demo.jmongo.test.TestDataWriter;
import com.lamfire.utils.Threads;



public class TestWriteMain {

	public static void main(String[] args) {
		int threads = 1;
		int count = 10000;
		
		if(args.length == 2){
			threads = Integer.parseInt(args[0]);
			count = Integer.parseInt(args[1]);
		}
		
		for(int i=0;i<threads;i++){
			Threads.startup(new TestDataWriter(count));
		}
	}
}
