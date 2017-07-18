package com.wb.ui;

import java.text.ParseException;

import javax.smartcardio.CommandAPDU;

public class Utils {
	
	public static int parseNumber(String str) {
	        // hex number
	        if (str.startsWith("0x")) {
	            return Integer.parseInt(str.substring(2), 16);
	        } else {
	            return Integer.parseInt(str);
	        }

	    }

	public static CommandAPDU parseAPDUCommand(String command) throws ParseException {
        String[] bytes = command.split("\\s+");
        if (bytes.length < 6) {
            throw new ParseException("C-APDU format must be: <CLA> <INS> <P1> <P2> <LC> [<byte 0> <byte 1> ... <byte LC-1>] <LE>; "+command, 6);
        }
        int cla = parseNumber(bytes[0]);
        int ins = parseNumber(bytes[1]);
        int p0 = parseNumber(bytes[2]);
        int p1 = parseNumber(bytes[3]);
        int lc = parseNumber(bytes[4]);
        int le = parseNumber(bytes[bytes.length - 1]);
        // check lc
        if (lc + 6 > bytes.length) {
            throw new ParseException("Unexpected end of C-APDU: " + command, lc + 5);
        }
        byte[] data = new byte[lc];
        for (int i = 0; i < lc; i++) {
            data[i] = (byte) parseNumber(bytes[i + 5]);
        }
        return new CommandAPDU(cla, ins, p0, p1, data, le);
    }
	
}
