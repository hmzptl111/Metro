package com.metro.client;

import com.metro.presentation.MetroPresentationImplementation;

public class Client 
{
    public static void main( String[] args )
    {
    	MetroPresentationImplementation mpi = new MetroPresentationImplementation();
    	while(true) {
			mpi.menu();
		}
    }
}
