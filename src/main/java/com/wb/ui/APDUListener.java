package com.wb.ui;

import javax.smartcardio.CommandAPDU;


public interface APDUListener {
	
	public void handleAPDU(CommandAPDU apdu);

}//APDUListener
