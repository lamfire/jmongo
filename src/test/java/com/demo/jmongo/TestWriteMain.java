package com.demo.jmongo;

import com.demo.jmongo.test.TestDataWriter;
import com.lamfire.utils.Threads;

import java.util.concurrent.ThreadPoolExecutor;


public class TestWriteMain {

	public static void main(String[] args) {
		int threads = 1;

		if(args.length == 2){
			threads = Integer.parseInt(args[0]);
		}

        ThreadPoolExecutor executor = Threads.newFixedThreadPool(threads);
		
		for(int i=0;i<threads;i++){
            executor.submit(new TestDataWriter());
		}
	}
}
