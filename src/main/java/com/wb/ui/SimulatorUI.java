package com.wb.ui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;


public class SimulatorUI extends JFrame {
	JLabel label1; 
	
public static void main(String[] args){
	SimulatorUI swingContainerDemo = new SimulatorUI(); 
		  swingContainerDemo.setJFrame();
	}
	  public void setJFrame()
	  {
          setSize(500, 700);
          
          menuBar();

         
          JTabbedPane tabbedPane = new JTabbedPane();
          tabbedPane.setPreferredSize(new Dimension(200, 30));
//        UIManager.getLookAndFeelDefaults().put("TabbedPane:TabbedPaneTab.contentMargins", new Insets(10, 100, 0, 0));
          
          ImageIcon   icon       = new ImageIcon( this.getClass().getResource("/images/apdu.png"));
          ImageIcon   help       = new ImageIcon( this.getClass().getResource("/images/help.png"));
          ImageIcon   about      = new ImageIcon( this.getClass().getResource("/images/about.png"));
       
          PanelConfig panelConfig = new PanelConfig();
          tabbedPane.addTab("Config", icon,panelConfig);
          
          PanelAPDU pandelAPDU = new PanelAPDU();
          tabbedPane.addTab("APDU", icon, pandelAPDU);

          label1 = new JLabel("about", icon, JLabel.CENTER);
          tabbedPane.addTab("About", about, label1);

          
          JButton button1 = new JButton("");
          tabbedPane.addTab("Help", help, button1);
          
  		  add(tabbedPane);
                
          setResizable(false);
          setVisible(true);

          
       }//setJFrame 
	  
	   //setMenu Bar function
	  private void menuBar() {
		  
		  JMenuBar  jMenuBAR = new JMenuBar();
		
		  ImageIcon iconNew  = new ImageIcon( this.getClass().getResource("/images/new.png"));
		  ImageIcon iconOpen = new ImageIcon( this.getClass().getResource("/images/open.png"));
		  ImageIcon iconSave = new ImageIcon( this.getClass().getResource("/images/save.png"));
          ImageIcon iconExit = new ImageIcon( this.getClass().getResource("/images/exit.png"));
		  
		  //File Menu
		  
		  JMenu file = new JMenu("File");
		  file.setMnemonic(KeyEvent.VK_F);
		  
		  
		  JMenuItem menuitemNew     = new JMenuItem("New",  iconNew);
		  menuitemNew.setMnemonic(KeyEvent.VK_E);
		  JMenuItem menuitemOpen    = new JMenuItem("Open", iconOpen);
		  menuitemOpen.setMnemonic(KeyEvent.VK_E);
		  JMenuItem menuitemsave    = new JMenuItem("Save", iconSave);
		  menuitemsave.setMnemonic(KeyEvent.VK_E);
		  JMenuItem menuitemExit    = new JMenuItem("Exit", iconExit);
	      menuitemExit.setMnemonic(KeyEvent.VK_E);
		  menuitemExit.setToolTipText("Exit application");
		  menuitemExit.addActionListener((ActionEvent event) -> {
	            System.exit(0);
	        });
		  	file.add(menuitemNew);
		  	file.add(menuitemOpen);
		  	file.add(menuitemsave);
		  	file.add(menuitemExit);
            jMenuBAR.add(file);

	        setJMenuBar(jMenuBAR);
	        
	    
	      //About Menu  
         
	      JMenu  aboutMenu = new JMenu("About");
          aboutMenu.setMnemonic(KeyEvent.VK_F);
          
          JMenuItem aboutMenuItem = new JMenuItem("APDU");
          aboutMenuItem.setMnemonic(KeyEvent.VK_E);
          
          aboutMenu.add(aboutMenuItem);
          jMenuBAR.add(aboutMenu);
          
          setJMenuBar(jMenuBAR);
		  
  }//menuBar()
	  
	
}//SimulatorUI

