package se.anosh.jbstag.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class Main extends JFrame {
	
	
	private MainFrame formPanel;
	private JSplitPane splitPane;
	private SongList songList;
	
	public Main() {
		
		super("Jbstag 0.3");
		formPanel = new MainFrame();
		songList = new SongList();
		
		setLayout(new BorderLayout());
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, songList);
		setMinimumSize(new Dimension(1000,600));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(splitPane, BorderLayout.CENTER);
		setSize(800, 600);
		setVisible(true);
	}
	

}
