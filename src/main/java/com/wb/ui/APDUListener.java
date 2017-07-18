package com.wb.ui;

import java.util.ArrayList;

import javax.smartcardio.CommandAPDU;


public interface APDUListener {
	
	public void handleAPDU(ArrayList<CommandAPDU> commands);

}//APDUListener
