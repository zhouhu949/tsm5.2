package com.qftx.tsm.log.thread;

public class LogDataListener {
	private LogDataWriteThread thread;
	
	public LogDataListener(){
		thread = new LogDataWriteThread();
		new Thread(thread).start();
	}
	
}
