package se.anosh.jbstag.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;

import org.pmw.tinylog.Logger;
import se.anosh.jbstag.model.GbsBean;

public class SongPanel extends JPanel {
	
	private final JTable table;
	private final SongTableModel tableModel;

	public SongPanel(ListModel<GbsBean> listModel, ListSelectionModel listSelectionModel) {
		tableModel = new SongTableModel(listModel);
		table = new JTable(tableModel);

		table.setSelectionModel(listSelectionModel);
		
		setLayout(new FlowLayout());
		final JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(900,350));
		scrollPane.setMinimumSize(scrollPane.getPreferredSize());
		add(scrollPane, BorderLayout.CENTER);

		setPreferredSize(new Dimension(1000,400));
		setVisible(true);
	}

	public void refresh() {
		tableModel.fireTableDataChanged();
		Logger.debug("Refresh was called");
	}

}
