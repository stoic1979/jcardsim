package com.wb.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

public class PanelConfig extends JPanel {
	
	JButton fileChoose;
	JTextField textField;
	
	JFileChooser chooser;
	String choosertitle;
	
	
	
	public PanelConfig() {
		
		textField  = new JTextField();
		fileChoose = new JButton("Browse Config");	

		textField.setPreferredSize(new Dimension(200, 30));
        fileChoose.setPreferredSize(new Dimension(200,30));
        
        add(textField);
		add(fileChoose);
        
		
		fileChoose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("================chooseFile=================");
				chooseFiles();
				
			}
		});

   	}

	
	private void chooseFiles() {
		    chooser = new JFileChooser(); 
		    chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle(choosertitle);
		    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		   
		    chooser.setAcceptAllFileFilterUsed(true);
		      
		    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
		      System.out.println("getCurrentDirectory(): "  +  chooser.getCurrentDirectory());
		      System.out.println("getSelectedFile() : "  +  chooser.getSelectedFile());
				textField.setText(chooser.getCurrentDirectory() + chooser.getSelectedFile().getName());

		      }
		    else {
		      System.out.println("No Selection ");
		      }
		    
	}
	
}
