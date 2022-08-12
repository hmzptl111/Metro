package com.metro.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.config.MetroConfig;
import com.metro.presentation.MetroPresentationImplementation;

@ComponentScan("com.metro")
public class Client 
{
    public static void main( String[] args )
    {
    	AnnotationConfigApplicationContext springContainer = new AnnotationConfigApplicationContext(MetroConfig.class);	
    	MetroPresentationImplementation mpi = (MetroPresentationImplementation)springContainer.getBean("metroPresentationImplementation");
    	while(true) {
    		mpi.menu();
    		
		}
    }
}