package com.wang.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Thread1 extends Thread{  
    private String name;  
    public Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    public Thread1(String name) {  
       this.name=name;  
    }  
    public void run() {  
            
			logger.trace("trace level"+"-1");  
			logger.debug("debug level");  
			logger.info("info level");  
			logger.warn("warn level");  
			logger.error("error level");  
			logger.fatal("fatal level"+"-6");
			logger.trace("trace level"+"-1");  
			logger.debug("debug level");  
			logger.info("info level");  
			logger.warn("warn level");  
			logger.error("error level");  
			logger.fatal("fatal level"+"-6");  
    
         
    }  
}  