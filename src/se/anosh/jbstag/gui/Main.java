package se.anosh.jbstag.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import se.anosh.jbstag.model.GbsBean;

public class Main extends JFrame {
	
	private MainFrame formPanel;
	private JSplitPane splitPane;
	private SongPanel songList;
	private List<GbsBean> db = new LinkedList<>();
	
	public Main() {
		super("Jbstag 0.3");
		
		formPanel = new MainFrame(db);
		songList = new SongPanel();
		songList.setData(db);
		formPanel.setAddFileListener( () -> songList.refresh()); // event listener
		songList.setSongTableListener( (n) -> {
			formPanel.showSong(db.get(n));
		});
		
		setLayout(new BorderLayout());
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, songList);
		setMinimumSize(new Dimension(1400,600));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(splitPane, BorderLayout.CENTER);
		//setSize(800, 600);
		setVisible(true);
		setResizable(false);
		
	}
		

}
