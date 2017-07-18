
package com.licel.jcardsim.samples;

import javacard.framework.*;

/**
 * Basic HelloWorld JavaCard Applet
 * @author LICEL LLC
 */
public class TestApplet extends BaseApplet {

    /**
     * Instruction: say hello
     */
    private final static byte SAY_HELLO_INS = (byte) 0x01;
   
    private static byte[] helloMessage = new byte[]{
        0x48, 0x65, 0x6C, 0x6C, 0x6F, 0x20, // "Hello "
        0x77, 0x6F, 0x72, 0x6C, 0x64, 0x20, 0x21 // "world !"
    };



    /**
     * Only this class's install method should create the applet object.
     */
    protected TestApplet(byte[] bArray, short bOffset, byte bLength) {

    	System.out.println("--- TestApplet ---");
        register();
    }    

    /**
     * This method is called once during applet instantiation process.
     * @param bArray
     * @param bOffset
     * @param bLength
     * @throws ISOException
     */
    public static void install(byte[] bArray, short bOffset, byte bLength)
            throws ISOException {
    	System.out.println("--- install ---");
        new TestApplet(bArray, bOffset,bLength);
    }

    /**
     * This method is called each time the applet receives APDU.
     */
    public void process(APDU apdu) {
    	
    	//System.out.println("--- process ---");

    	
        // good practice
        if(selectingApplet()) return;
        byte[] buffer = apdu.getBuffer();
        
        sayHello(apdu, (short)0x9000);

    }

    /**
     * Sends hello message to host using given APDU.
     *
     * @param apdu APDU that requested hello message
     * @param sw response sw code
     */
    private void sayHello(APDU apdu, short sw) {
        // Here all bytes of the APDU are stored
        byte[] buffer = apdu.getBuffer();
        // receive all bytes
        // if P1 = 0x01 (echo)
        short incomeBytes = apdu.setIncomingAndReceive();
        byte[] echo;
        if (buffer[ISO7816.OFFSET_P1] == 0x01) {
            echo = JCSystem.makeTransientByteArray(incomeBytes, JCSystem.CLEAR_ON_RESET);
            Util.arrayCopyNonAtomic(buffer, ISO7816.OFFSET_CDATA, echo, (short) 0, incomeBytes);
        } else {
            echo = JCSystem.makeTransientByteArray((short) helloMessage.length, JCSystem.CLEAR_ON_RESET);
            Util.arrayCopyNonAtomic(helloMessage, (short) 0, echo, (short) 0, (short) helloMessage.length);
        }
        // Tell JVM that we will send data
        apdu.setOutgoing();
        // Set the length of data to send
        apdu.setOutgoingLength((short) echo.length);
        // Send our message starting at 0 position
        apdu.sendBytesLong(echo, (short) 0, (short) echo.length);
        // Set application specific sw
        if(sw!=0x9000) {
            ISOException.throwIt(sw);
        }
    }


}
