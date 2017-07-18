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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.licel.jcardsim.base.SimulatorSystem;
import com.licel.jcardsim.smartcardio.JCardSimProvider;
import com.licel.jcardsim.utils.APDUScriptTool;

import javacard.framework.APDU;

public class PanelAPDU extends JPanel implements ActionListener {
	
    private static final String TEST_APPLET_AID = "010203040506070809";

	
	
	  byte[] values = new byte[] {(byte)0xB0,(byte)0xB0,(byte)0xB0,(byte)0xB0,(byte)0xB0};
 	
      JLabel     lbCLA, lbINS, lbP1, lbP2, lbLC,lbResult;
      JTextField tfCLA, tfINS, tfP1, tfP2, tfLC;
	  static     JTextField   tfResult;
      JButton    btnSend,btnReset,btnClear;
      JPanel     mainPanel,centerPanel,northPanel,southPanel;
      static     JTextArea  txtResult;
      String     txtOutput = "Output";
      
      int cla = 0x80;
      int ins = 0xb8;
      int p1  = 0x00;
      int p2  = 0x00;
      int LC  = 0x10;
      
      
      
      byte[] data = new byte[] {(byte) 0xEF, (byte) 0x08};
      int le  = 0x0D;
      
      private APDUListener apduListener; 
      
       PanelAPDU(APDUListener apduListener)
      {
    	   
    	   this.apduListener = apduListener;
    	   
         //Set JLabel
          lbCLA   = new JLabel("CLA");
          lbINS   = new JLabel("INS");
          lbP1    = new JLabel("P1");
          lbP2    = new JLabel("P2");
          lbLC    = new JLabel("LC");
          lbResult= new JLabel(txtOutput,SwingConstants.CENTER);
        
          //Set  JTextFields
          tfCLA   = new JTextField();
          tfINS   = new JTextField();
          tfP1    = new JTextField();
          tfP2    = new JTextField();
          tfLC    = new JTextField();
          tfResult= new JTextField();
          //set JTextArea
          txtResult=new JTextArea(); 

          //set JButton
          btnSend = new JButton("SEND APDU");
          btnReset= new JButton("RESET");
          btnClear= new JButton("CLEAR");
          
          btnSend.addActionListener(this);
            
          btnReset.setPreferredSize(new Dimension(200, 30));
          btnSend.setPreferredSize(new Dimension(200, 30));
          btnClear.setPreferredSize(new Dimension(500, 30));
          
          northPanel = new JPanel();
          northPanel.setLayout(new GridLayout(5, 2,10,10));
          northPanel.setPreferredSize(new Dimension(400,200));


          northPanel.add(lbCLA);
          northPanel.add(tfCLA);
   	  
          northPanel.add(lbINS);
          northPanel.add(tfINS);    	  
          northPanel.add(lbP1);
          northPanel.add(tfP1);

          northPanel.add(lbP2);
          northPanel.add(tfP2);
   	  
          northPanel.add(lbLC);
          northPanel.add(tfLC);
         
          centerPanel = new JPanel();
          centerPanel.add(btnSend);
          centerPanel.add(btnReset);
          
          southPanel = new JPanel();
          southPanel.setPreferredSize(new Dimension(400,300));
          southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
          btnClear.setAlignmentX(Component.CENTER_ALIGNMENT);
          btnClear.add(Box.createVerticalStrut(20));
          lbResult.setAlignmentX(Component.CENTER_ALIGNMENT);
          southPanel.add(lbResult);
          southPanel.add(txtResult);
          southPanel.add(btnClear);

  	      mainPanel = new JPanel();
          mainPanel.setBackground(Color.green);


          mainPanel.setLayout(new BorderLayout());
          mainPanel.add(northPanel,  BorderLayout.NORTH);
          mainPanel.add(centerPanel, BorderLayout.CENTER);
          mainPanel.add(southPanel,BorderLayout.SOUTH);   
          
          EventClickListener();
          setAPDUCommand();
          add(mainPanel, BorderLayout.CENTER);
          
          
   }
       
       public void setAPDUCommand()
       {
    	   tfCLA.setText(String.valueOf(cla)); 
    	   tfINS.setText(String.valueOf(ins)); 
    	   tfP1.setText(String.valueOf(p1)); 
    	   tfP2.setText(String.valueOf(p2));
    	   tfLC.setText(String.valueOf(LC)); 
    	   
       }   
       
      public void responseAPDU()
       {
    	   
    	   Properties cfg = new Properties();
           cfg.setProperty("com.licel.jcardsim.smartcardio.applet.0.AID", TEST_APPLET_AID);
           cfg.setProperty("com.licel.jcardsim.smartcardio.applet.0.Class", "com.licel.jcardsim.samples.HelloWorldApplet");
         
           
           StringBuilder sb = new StringBuilder();
           sb.append("0x80 0xb8 0x00 0x00 0x10 0x9 0x01 0x02 0x03 0x04 0x05 0x06 0x07 0x8 0x09 0x05 0x00 0x00 0x02 0xF 0xF 0x7f;\n");
           InputStream commandsStream = new ByteArrayInputStream(sb.toString().replaceAll("\n", System.getProperty("line.separator")).getBytes());     
           boolean isException = true;
           try {
               SimulatorSystem.resetRuntime();
               APDUScriptTool.executeCommands(cfg, commandsStream, null);
               isException = false;
           } catch (Throwable t) {
               t.printStackTrace();
           }
           try {
			commandsStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
      
  
           
       
    private void EventClickListener() {
    	  //Send APDU
          
          //RestButton
          btnReset.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		
				
				System.out.println("========click Listener RestValue==========");

				
			}
		});
          //clearOtputData
          btnClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("========click Listener btnClear===========");
				
			}
		});
     
      }//init()
      

  
       public void testExecuteCommands() throws Exception {
           System.out.println("executeCommands");
           Properties cfg = new Properties();
           cfg.setProperty("com.licel.jcardsim.smartcardio.applet.0.AID", TEST_APPLET_AID);
           cfg.setProperty("com.licel.jcardsim.smartcardio.applet.0.Class", "com.licel.jcardsim.samples.HelloWorldApplet");
           StringBuilder sb = new StringBuilder();
           sb.append("0x80 0xb8 0x00 0x00 0x10 0x9 0x01 0x02 0x03 0x04 0x05 0x06 0x07 0x8 0x09 0x05 0x00 0x00 0x02 0xF 0xF 0x7f;\n");
           sb.append("0x00 0xa4 0x00 0x00 0x09 0x01 0x02 0x03 0x04 0x05 0x06 0x07 0x8 0x09 0x7f;\n");
           sb.append("0x01 0x02 0x00 0x00 0x00 0x2; \n");
           sb.append("0x01 0x01 0x00 0x00 0x00 0x0d;\n");
           sb.append("0x01 0x01 0x01 0x00 0x0d 0x48 0x65 0x6c 0x6c 0x6f 0x20 0x77 0x6f 0x72 0x6c 0x64 0x20 0x21 0x0d;\n");
           sb.append("0x01 0x03 0x01 0x02 0x05 0x01 0x02 0x03 0x04 0x05 0x7F;");
           InputStream commandsStream = new ByteArrayInputStream(sb.toString().replaceAll("\n", System.getProperty("line.separator")).getBytes());     
           boolean isException = true;
           try {
               SimulatorSystem.resetRuntime();
               APDUScriptTool.executeCommands(cfg, commandsStream, null);
               isException = false;
           } catch (Throwable t) {
               t.printStackTrace();
           }
//           assertEquals(isException, false);
           commandsStream.close();
       }

	@Override
	public void actionPerformed(ActionEvent ae) {
		System.out.println("Button: " + ae.getActionCommand());
		
		String apduCLA = tfCLA.getText();
		String apduINS = tfINS.getText();
		String apduP1  = tfP1.getText();
		String apduP2  = tfP2.getText();
		String apduLC  = tfLC.getText();
		
		
		String apdu = tfCLA.getText() + " " + tfINS.getText() + " " + tfP1.getText() + " " + tfP2.getText();
		
		String apdu1 = "0x80 0xb8 0x00 0x00 0x10 0x9 0x01 0x02 0x03 0x04 0x05 0x06 0x07 0x8 0x09 0x05 0x00 0x00 0x02 0xF 0xF 0x7f";
		
		
		if(ae.getActionCommand() == "SEND APDU") {
			try {
			apduListener.handleAPDU( Utils.parseAPDUCommand(apdu1) );
			} catch(Exception e) {
				System.out.println("[PanelAPDU] actionPerformed() got Exception :: " + e);
			}
        }
		
}


}//PanelAPDU

    
	

