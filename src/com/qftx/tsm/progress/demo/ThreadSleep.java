package com.qftx.tsm.progress.demo;

public class ThreadSleep {
	
	public static void sleep(int millSecond){
		try {
			Thread.sleep(millSecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
