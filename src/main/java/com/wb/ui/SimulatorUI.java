package com.wb.ui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.licel.jcardsim.smartcardio.JCardSimProvider;
import com.licel.jcardsim.utils.APDUScriptTool;


public class SimulatorUI extends JFrame implements APDUListener {
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

		PanelAPDU pandelAPDU = new PanelAPDU(this);
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


	@Override
	public void handleAPDU(ArrayList<CommandAPDU> commands) {

		try {
			runAPDUCommand(commands);
		} catch(Exception e) {
			System.out.println("[SimulatorUI] handleAPDU() got Exception :: " + e);
		}

	}

	public static void runAPDUCommand(ArrayList<CommandAPDU> commands) throws NoSuchAlgorithmException, CardException{

		String cfgFilePath = "/home/leo/work/simulator/jcardsim/jcardsim.cfg";

		System.out.println("[SimulatorUI] runAPDUCommand...");

		//-----------------------------------------------------------
		//      LOADING CONFIG FILE   
		//-----------------------------------------------------------
		Properties cfg = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(cfgFilePath);
			cfg.load(fis);
		} catch (Throwable t) {
			System.err.println("Unable to load configuration " + cfgFilePath + " due to: " + t.getMessage());
			System.exit(-1);
		} 
		System.out.println("[SimulatorUI] Loaded Config file");

		Enumeration keys = cfg.propertyNames();
		while(keys.hasMoreElements()) {
			String propertyName = (String) keys.nextElement();
			System.setProperty(propertyName, cfg.getProperty(propertyName));
		}

		//-----------------------------------------------------------
		//      Add Security Provider 
		//-----------------------------------------------------------
		if (Security.getProvider("jCardSim") == null) {
			JCardSimProvider provider = new JCardSimProvider();
			Security.addProvider(provider);
		}

		//-----------------------------------------------------------
		//      Creating Terminal
		//-----------------------------------------------------------    
		TerminalFactory tf = TerminalFactory.getInstance("jCardSim", null);
		CardTerminals ct = tf.terminals();
		List<CardTerminal> list = ct.list();
		CardTerminal jcsTerminal = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals("jCardSim.Terminal")) {
				jcsTerminal = list.get(i);
				break;
			}
		}
		
		System.out.println("[SimulatorUI] Created Terminal");

		//-----------------------------------------------------------
		//      RUNNING APDU Command
		//-----------------------------------------------------------

		Card jcsCard = jcsTerminal.connect("T=0");
		CardChannel jcsChannel = jcsCard.getBasicChannel();

		 for (int i = 0; i < commands.size(); i++) {
			 CommandAPDU command = commands.get(i);
		ResponseAPDU response = jcsChannel.transmit(command);

		System.out.println("[SimulatorUI] sending command on jcs channel");
		System.out.println("\n[SimulatorUI] Command:\n " + APDUScriptTool.commandToStr(command) );

		System.out.println("\n[SimulatorUI] Response:\n " + APDUScriptTool.responseToStr(response));

		String dump = APDUTest.commandToStr(command) + APDUTest.responseToStr(response);
		
		System.out.println("\n[SimulatorUI] dump:\n " + dump);

		 }
	}//runAPDUCommand


}//SimulatorUI

