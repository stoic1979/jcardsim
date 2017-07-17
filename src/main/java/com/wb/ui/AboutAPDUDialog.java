package com.wb.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutAPDUDialog extends JFrame {
	JPanel northPanel,southPanel;
	
	AboutAPDUDialog(){
		setSize(300, 300);
		setVisible(true);
		setLayout(new GridLayout(2, 0,10,10));
        northPanel = new JPanel();
		String  about = "";
		JLabel  aboutAPDU = new JLabel("");
	    aboutAPDU.setPreferredSize(new Dimension(30, 30));
	    northPanel.add(aboutAPDU);
       
	    southPanel = new JPanel();
		JButton ok        = new JButton("ok");
		ok.setPreferredSize(new Dimension(30, 30));
		southPanel.add(ok);

		
 }
}
