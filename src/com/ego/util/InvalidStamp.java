package com.ego.util;

public class InvalidStamp extends Exception{
	private static final long serialVersionUID = 1L;

	//Parameterless Constructor
    public InvalidStamp() {}

    //Constructor that accepts a message
    public InvalidStamp(String message)
    {
       super(message);
    }
}
