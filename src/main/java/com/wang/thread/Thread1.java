package com.wang.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wang.custom.JobLogFactory;

public class Thread1 extends Thread{  
    private String name;  
    //public Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    ThreadLocal<Logger> loggerLocal= new ThreadLocal<Logger>();
    
    public Thread1(String name) { 
    	super(name);
       this.name=name;  
    }  
    public void run() {  
    	Logger logger=null;
    	synchronized (Thread1.class) {
    		logger=JobLogFactory.createLogger(12);
    	    loggerLocal.set(logger);
		}
    	    logger=loggerLocal.get();
			logger.info("info level");  
			logger.info("info level");
			logger.info("info level");
			logger.info("info level");  
    
         
    }  
}  