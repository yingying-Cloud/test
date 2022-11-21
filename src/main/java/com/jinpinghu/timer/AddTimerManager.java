package com.jinpinghu.timer;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;

import fw.jbiz.common.conf.ZSystemConfig;
import fw.jbiz.jpa.ZJpaHelper;

/**
 * java定时发送任务
 */
public class AddTimerManager  {
	
	 ScheduledExecutorService service ;
	
	 private volatile static AddTimerManager manager = null;
	 private AddTimerManager(){}
	 public static AddTimerManager getInstance(){
		 if(manager == null){
			 synchronized (AddTimerManager.class){
				 if(manager == null){
					 manager = new AddTimerManager();
				 }	
			 }
		 }
	     return manager;
	  }
	
	private EntityManager em;
	
	public void beginSendTask() {
		
		em = ZJpaHelper.getEntityManager(ZSystemConfig.getProperty("wisdom_logistics_jar"));
    	ZJpaHelper.beginTransaction(em);
		service = Executors.newScheduledThreadPool(30);
		
		ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
		ZonedDateTime nextRun = now.withHour(22).withMinute(0).withSecond(0);//返回当日的22点的时间
		if(now.compareTo(nextRun) > 0)
		    nextRun = nextRun.plusDays(1);

		Duration duration = Duration.between(now, nextRun);
		long initalDelay = duration.getSeconds();

		
		
		AddScheduledExecutor task2 = new AddScheduledExecutor(); 
		AddToolScheduledExecutor task3 = new AddToolScheduledExecutor();
		AddToolOrderScheduledExecutor task4 = new AddToolOrderScheduledExecutor(); 
		
		AddToolRecordScheduledExecutor task5 = new AddToolRecordScheduledExecutor();
		AddToolRecoveryScheduledExecutor task6 = new AddToolRecoveryScheduledExecutor();
		AddLinkInfoScheduledExecutor task7 = new AddLinkInfoScheduledExecutor();
		
		AddToolRecordNoScheduledExecutor task8 = new AddToolRecordNoScheduledExecutor();
		
//		service.schedule(task2, TimeUnit.SECONDS.toSeconds(40), TimeUnit.SECONDS);
//		service.schedule(task3, TimeUnit.SECONDS.toSeconds(50), TimeUnit.SECONDS);
//		service.scheduleAtFixedRate(task4, 0 , TimeUnit.SECONDS.toSeconds(60), TimeUnit.SECONDS);
//		service.scheduleAtFixedRate(task8, initalDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
//		service.schedule(task7, TimeUnit.SECONDS.toSeconds(300), TimeUnit.SECONDS);
//		service.schedule(task5, TimeUnit.SECONDS.toSeconds(300), TimeUnit.SECONDS);
//		service.schedule(task6, TimeUnit.SECONDS.toSeconds(300), TimeUnit.SECONDS);
		
//		service.scheduleAtFixedRate(task2, initalDelay,TimeUnit.DAYS.toSeconds(1),TimeUnit.SECONDS);
//		service.scheduleAtFixedRate(task3,initalDelay,TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
//		service.scheduleAtFixedRate(task7, initalDelay,TimeUnit.DAYS.toSeconds(1),TimeUnit.SECONDS);
//		service.scheduleAtFixedRate(task5, initalDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
//		service.scheduleAtFixedRate(task6,initalDelay, TimeUnit.DAYS.toSeconds(1),TimeUnit.SECONDS);
//		service.scheduleAtFixedRate(task4,initalDelay,TimeUnit.DAYS.toSeconds(1),TimeUnit.SECONDS);
		
		
	}

	public void doCancle() {
		List<Runnable> list = service.shutdownNow();
		System.out.println(list);
	}
}