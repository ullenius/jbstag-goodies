package se.anosh.jbstag;

import javax.swing.SwingUtilities;

import org.pmw.tinylog.Logger;
import se.anosh.jbstag.gui.Main;

public class App {
	
	
	public static void main(String[] args) {

		Logger.debug("hello world");
		Logger.warn("varning");

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});	
		
		
	}

}
