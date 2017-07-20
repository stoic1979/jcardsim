package com.wb.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;






public class PanelAPDU extends JPanel implements ActionListener {
	 
	  byte[] values = new byte[] {(byte)0xB0,(byte)0xB0,(byte)0xB0,(byte)0xB0,(byte)0xB0};
	  
	  JLabel     lbAPDU;
	  JTextField txtAPDU;
	  
      JButton    btnSend,btnReset,btnClear;
      JPanel     mainPanel,centerPanel,northPanel,southPanel;
      JTextArea  txtResult;
      String     txtOutput = "Output";

      
      
      
      
      private APDUListener apduListener; 
      
       PanelAPDU(APDUListener apduListener)
      {
    	   
    	   this.apduListener = apduListener;
    	    //Set JLabel
    	    lbAPDU   = new JLabel("APDU");
    	    //Set  JTextFields
    	    txtAPDU  = new JTextField();
            //TextArea output
    	    txtResult=new JTextArea(); 

           //set JButton
            btnSend  = new JButton("SEND APDU");
            btnReset = new JButton("RESET");
            btnClear = new JButton("CLEAR");
          
            btnSend.addActionListener(this);
            btnReset.addActionListener(this);
            btnClear.addActionListener(this);

          
            btnReset.setPreferredSize(new Dimension(200, 30));
            btnSend.setPreferredSize(new Dimension(200, 30));
            btnClear.setPreferredSize(new Dimension(500, 30));
          
            northPanel = new JPanel();

            
            
            northPanel.setPreferredSize(new Dimension(1000,200));
            northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
 

            northPanel.add(lbAPDU);
            northPanel.add(txtAPDU);
          
            centerPanel = new JPanel();
            centerPanel.add(btnSend);
            centerPanel.add(btnReset);
           
            southPanel = new JPanel();
            southPanel.setPreferredSize(new Dimension(1000,400));
            southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
            btnClear.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnClear.add(Box.createVerticalStrut(20));

            southPanel.add(txtResult);
            southPanel.add(btnClear);

  	        mainPanel = new JPanel();
            mainPanel.setBackground(Color.green);


           mainPanel.setLayout(new BorderLayout());
           mainPanel.add(northPanel,  BorderLayout.NORTH);
           mainPanel.add(centerPanel, BorderLayout.CENTER);
           mainPanel.add(southPanel,BorderLayout.SOUTH);   
          
          add(mainPanel, BorderLayout.CENTER);
          
        }
       
    
    @Override
	public void actionPerformed(ActionEvent ae) {
		System.out.println("Button: " + ae.getActionCommand());
		
		String apduCreateApplet = "0x80 0xb8 0x00 0x00 0x10 0x9 0x01 0x02 0x03 0x04 0x05 0x06 0x07 0x8 0x09 0x05 0x00 0x00 0x02 0xF 0xF 0x7f";		
		String apduSelectApplet = "0x00 0xa4 0x00 0x00 0x09 0x01 0x02 0x03 0x04 0x05 0x06 0x07 0x8 0x09 0x2";
		
		String apduNOP          = "0x00 0x02 0x00 0x00 0x00 0x2";
		
		String apduHello        = "0x00 0x01 0x00 0x00 0x00 0x0d";
		
		

		
	

		if(ae.getActionCommand() == "SEND APDU") {
			try {
				
				ArrayList<CommandAPDU> apduCommands = new ArrayList();
			    apduCommands.add( Utils.parseAPDUCommand(apduCreateApplet));
				apduCommands.add( Utils.parseAPDUCommand(apduSelectApplet) );
				apduCommands.add( Utils.parseAPDUCommand(apduNOP));
				apduCommands.add( Utils.parseAPDUCommand(apduHello) );
				
				String dump = apduListener.handleAPDU(Utils.parseAPDUCommand(txtAPDU.getText()));
			    txtResult.setText(txtResult.getText() + " \n" + dump);  
               } catch(Exception e) {
				System.out.println("[PanelAPDU] actionPerformed() got Exception :: " + e);
				e.printStackTrace();
			}
        }
		
		
}


}//PanelAPDU

    
	

