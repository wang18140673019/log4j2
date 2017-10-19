package com.wang;
import com.wang.thread.Thread1;

public class Log4j2demo1 {

	public static void main(String[] args) {
		Thread1 mTh1=new Thread1("A");  
		Thread1 mTh2=new Thread1("B");  
	        mTh1.start();  
	        mTh2.start();  
	}
}
