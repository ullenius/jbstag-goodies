package se.anosh.jbstag;

import javax.swing.SwingUtilities;

import se.anosh.jbstag.gui.Main;
import se.anosh.jbstag.gui.MainFrame;

public class App {
	
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});	
		
		
	}

}
