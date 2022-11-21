package com.jinpinghu.servlet.service;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import fw.jbiz.ZObject;

public class GcService extends ZObject{
	public static TimerTask task;
	
	public static void start() {
		task = new TimerTask() {
			@Override
			public void run() {
				Runtime runtime = Runtime.getRuntime();
				runtime.gc();
				System.out.println("gc.....freememory:"+runtime.freeMemory()+",totalmemory:"+runtime.totalMemory());
			}
			
		};
		
		long CD = 60 * 60 * 1000;
		
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(task, 0, CD, TimeUnit.MILLISECONDS);
	}
	
	public static void destroy() { 
		task.cancel();
	}
	
}
