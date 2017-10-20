package com.wang.custom;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;



/**
 * 现在是不支持根据线程输出的(在多线程中多次调用该方法会出现问题)，貌似log4j2不支持多线程，里面好多类都是static的
 * 自定义日志:可根据需求设置不同的日志格式，
 * 参考网址：http://logging.apache.org/log4j/2.x/manual/customconfig.html
 */
public class JobLogFactory {
	private JobLogFactory() {
	}

	public static void start(int jobId) {
		 Date date=new Date();    
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss"); 
		 String fileName=Thread.currentThread().getName()+"-"+df.format(date).toString();
		ConfigurationBuilder< BuiltConfiguration > builder = ConfigurationBuilderFactory.newConfigurationBuilder();
		builder.setStatusLevel( Level.DEBUG);
		builder.setConfigurationName("RollingBuilder");
		//添加过滤器
		 builder.add(builder.newFilter("ThresholdFilter", Filter.Result.NEUTRAL, Filter.Result.DENY).
		            addAttribute("level", Level.INFO));
		 builder.add(builder.newFilter("ThresholdFilter", Filter.Result.DENY, Filter.Result.ACCEPT).
		            addAttribute("level", Level.WARN));
		// create a console appender
		AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout", "CONSOLE").addAttribute("target",
		    ConsoleAppender.Target.SYSTEM_OUT);
		appenderBuilder.add(builder.newLayout("PatternLayout")
		    .addAttribute("pattern", "%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"));
		builder.add( appenderBuilder);
		// create a rolling file appender
		LayoutComponentBuilder layoutBuilder = builder.newLayout("PatternLayout")
		    .addAttribute("pattern", "%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n");
		ComponentBuilder triggeringPolicy = builder.newComponent("Policies")
		    .addComponent(builder.newComponent("CronTriggeringPolicy").addAttribute("schedule", "0 0 0 * * ?"))
		    .addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", "100KB"));
		appenderBuilder = builder.newAppender("rolling", "RollingFile")
		    .addAttribute("fileName", "logs/info-"+fileName+".log")
		    //如果精确到秒会每秒钟生成一个新文件
		    .addAttribute("filePattern", "logs/info/info-%d{yy-MM-dd-HH-mm}.log")
		    .add(layoutBuilder)
		    .addComponent(triggeringPolicy);
		builder.add(appenderBuilder);

		// create the new logger
		builder.add( builder.newLogger( "TestLogger", Level.DEBUG )
		    .add( builder.newAppenderRef( "rolling" ) )
		    .addAttribute( "additivity", false ) );
       //rolling,Stdout都是上面定义的
	   //这里定义了两个loger(root和TestLogger)，root可打印到控制台,默认是root logger
		builder.add( builder.newRootLogger( Level.DEBUG )
		    .add( builder.newAppenderRef( "rolling" ) )
		    .add( builder.newAppenderRef( "Stdout" ) )
		    );
		
		builder.add( builder.newRootLogger( Level.DEBUG )
			    .add( builder.newAppenderRef( "rolling" ) )
			    .add( builder.newAppenderRef( "Stdout" ) )
			    );
		//异步日志
		builder.add(builder.newAsyncRootLogger(Level.DEBUG)
				  .add( builder.newAppenderRef( "rolling" ) )
				    .add( builder.newAppenderRef( "Stdout" ) )
				    );
		LoggerContext ctx = Configurator.initialize(builder.build());
		System.out.println("LoggerContext ctx : "+ctx);
	}

	public static void stop(int jobId) {
		
	}

	/**
	 * 获取Logger
	 *
	 * 如果不想使用slf4j,那这里改成直接返回Log4j的Logger即可
	 * 
	 * @param jobId
	 * @return 
	 * @return
	 */
	public static  Logger createLogger(int jobId) {
		start(jobId);
		return LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	}
}