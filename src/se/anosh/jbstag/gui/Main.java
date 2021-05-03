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
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel.buildFormPanel(), songList);
		splitPane.setResizeWeight(1);
		splitPane.setDividerSize(1);
		setMinimumSize(new Dimension(1500,400));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		add(splitPane, BorderLayout.CENTER);
		setVisible(true);
		setResizable(false);
	}
	
		

}
