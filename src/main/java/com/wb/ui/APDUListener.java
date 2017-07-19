package com.wb.ui;

import java.util.ArrayList;

import javax.smartcardio.CommandAPDU;


public interface APDUListener {
	
	public String handleAPDU(CommandAPDU command);

}//APDUListener
