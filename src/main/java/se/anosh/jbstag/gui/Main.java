package se.anosh.jbstag.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.SelectionInList;
import se.anosh.jbstag.model.GbsBean;

public class Main extends JFrame {
	
	private final MainFrame formPanel;
	private final JSplitPane splitPane;
	private final SongPanel songList;
	private final List<GbsBean> db = new LinkedList<>();
	
	public Main() {
		super("Jbstag 0.9");
		SelectionInList<GbsBean> tableSelection = new SelectionInList<>(db);
		ListSelectionModel listSelectionModel = new SingleListSelectionAdapter(
				tableSelection.getSelectionIndexHolder());

		formPanel = new MainFrame(tableSelection, db);
		songList = new SongPanel(tableSelection, listSelectionModel);
		formPanel.setAddFileListener(songList::refresh); // event listener
		setLayout(new BorderLayout());
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel.buildFormPanel(), songList);
		splitPane.setResizeWeight(1);
		splitPane.setDividerSize(1);
		setMinimumSize(new Dimension(1400,400));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		add(splitPane, BorderLayout.CENTER);
		setVisible(true);
		setResizable(false);
	}
	
		

}
