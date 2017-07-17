package com.wb.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class PanelAPDU extends JPanel {
     
	  JLabel     lbCLA, lbINS, lbP1, lbP2, lbLC,lbResult;
      JTextField tfCLA, tfINS, tfP1, tfP2, tfLC,tfResult;
      JButton    btnSend,btnReset,btnClear;
      JPanel     mainPanel,centerPanel,northPanel,southPanel;
      JTextArea  txtResult;
      String     txtOutput = "Output";
      
       PanelAPDU()
      {
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
//          txtResult.setPreferredSize(new Dimension(400,500));

          //set JButton
          btnSend = new JButton("SEND APDU");
          btnReset= new JButton("RESET");
          btnClear= new JButton("CLEAR");
      
          
//          btnSend.addActionListener((ActionListener) this);
          
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
//        centerPanel.setSize(400,300);
          
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
          add(mainPanel, BorderLayout.CENTER);
          
  }
       
    

}//PanelAPDU

    
	

