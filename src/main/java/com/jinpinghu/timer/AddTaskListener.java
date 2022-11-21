package com.jinpinghu.timer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
 
 
public class AddTaskListener implements  ServletContextListener {
	AddTimerManager timerManager;
    @Override
	public void contextInitialized(ServletContextEvent sce) {
    	timerManager = AddTimerManager.getInstance();
    	timerManager.beginSendTask();
    }
 
    @Override
	public void contextDestroyed(ServletContextEvent sce) {
    	timerManager.doCancle();
    }
}