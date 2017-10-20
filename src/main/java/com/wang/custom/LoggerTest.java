package com.wang.custom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
simple test
 */
public class LoggerTest {
  public static void main(String[] args) {
 
      Logger logger = JobLogFactory.createLogger(12);
	  //Logger logger= LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
      for(int i=0;i<3000;i++){
    	  logger.info("Testing testing testing 111");
          logger.debug("Testing testing testing 222");
          logger.error("Testing testing testing 333"); 
      }
  

  }
}