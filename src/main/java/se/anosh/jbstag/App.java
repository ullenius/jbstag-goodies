package se.anosh.jbstag;

import javax.swing.SwingUtilities;

import se.anosh.jbstag.gui.Main;

public class App {
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(Main::new);
	}

}
