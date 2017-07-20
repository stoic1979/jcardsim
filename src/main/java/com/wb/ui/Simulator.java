package com.wb.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.management.remote.NotificationResult;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.licel.jcardsim.smartcardio.JCardSimProvider;
import com.licel.jcardsim.utils.APDUScriptTool;

public class Simulator extends JFrame implements ActionListener  {
	
	CardChannel jcsChannel;

	
	private JTextField txtApduCommand,txtChooseConfig;
	private JTextArea  txtOutputApdu;
	private JLabel     lbApdu,lbOutput;
	private JButton    btnSend,btnReset,btnClear,btnBrowseConfig;
	
	private JPanel     northPanel,centerPanel,southPanel;
	
	JFileChooser chooser;
	String choosertitle;
	
    private static APDUListener apduListener; 

	
	public Simulator(APDUListener apduListener) {
		setSize(800,800);
		//Buttons
		btnBrowseConfig = new JButton("Browse Config");
		btnBrowseConfig.addActionListener(this);
		btnSend		    = new JButton("Send");
		btnSend.addActionListener(this);
		btnReset	    = new JButton("Reset");
		//Label
		lbApdu          = new JLabel("APDU Commands");
		
		lbOutput        = new JLabel("Output");
		//TextFields
		txtApduCommand  = new JTextField();
		
		txtApduCommand.setPreferredSize(new Dimension(920, 200));

		txtChooseConfig = new JTextField();
		txtOutputApdu   = new JTextArea(); 
		txtOutputApdu.setPreferredSize(new Dimension(920, 200));

		
		
		//north Panel
		northPanel 		= new JPanel(new GridLayout(1,1,50,40));
		northPanel.setBackground(Color.gray);
		northPanel.add(btnBrowseConfig);
		northPanel.add(txtChooseConfig);
        northPanel.setBorder(new EmptyBorder(50, 400, 50, 400));
        getContentPane().add(northPanel, BorderLayout.PAGE_START);	
        
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.gray);
        centerPanel.add(lbApdu);
        centerPanel.add(txtApduCommand);
        centerPanel.add(btnSend);
        centerPanel.add(btnReset);
        getContentPane().add(centerPanel, BorderLayout.CENTER);	
        
        southPanel = new JPanel(new FlowLayout());
        southPanel.setBackground(Color.gray);
        southPanel.add(lbOutput);
        southPanel.add(txtOutputApdu);
        southPanel.setBorder(new EmptyBorder(50, 400, 50, 400));
        getContentPane().add(southPanel, BorderLayout.SOUTH);	
      
        setVisible(true);

}
	
	public static void main(String arg[])
	{
		new Simulator(apduListener);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="Send")
		{
			System.out.println("======================Send Apdu Command==========================");
			
			try {
				String dump = apduListener.handleAPDU(Utils.parseAPDUCommand(txtApduCommand.getText()));
				txtOutputApdu.setText(txtOutputApdu.getText() + " \n" + dump);  

			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		if(e.getActionCommand()=="Browse Config")
		{
			chooseConfigFiles();
			
		}
		
	}

private void chooseConfigFiles() {
	    chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle(choosertitle);
	    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	   
	    chooser.setAcceptAllFileFilterUsed(true);
	      
	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
	      System.out.println("getCurrentDirectory(): "  +  chooser.getCurrentDirectory());
	      System.out.println("getSelectedFile() : "  +  chooser.getSelectedFile());
	      txtChooseConfig.setText(chooser.getCurrentDirectory() + chooser.getSelectedFile().getName());

	      }
	    else {
	      System.out.println("No Selection ");
	      }
	    
}
	
}
