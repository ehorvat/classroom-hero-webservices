package com.ego.util;

public class PasswordMisMatch extends Exception{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Parameterless Constructor
    public PasswordMisMatch() {}

    //Constructor that accepts a message
    public PasswordMisMatch(String message)
    {
       super(message);
    }

}