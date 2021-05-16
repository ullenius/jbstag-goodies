package se.anosh.jbstag.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Objects;

import javax.swing.*;

import se.anosh.jbstag.model.GbsBean;

public class SongPanel extends JPanel {
	
	private JTable table;
	private SongTableModel tableModel;

	public SongPanel(ListModel<GbsBean> listModel, ListSelectionModel listSelectionModel) {
		tableModel = new SongTableModel(listModel);
		table = new JTable(tableModel);

		table.setSelectionModel(listSelectionModel);
		
		setLayout(new FlowLayout());
		final JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(900,300));
		scrollPane.setMinimumSize(scrollPane.getPreferredSize());
		add(scrollPane, BorderLayout.CENTER);

		setPreferredSize(new Dimension(1000,300));
		setVisible(true);
	}

	@Deprecated
	public void refresh() {
		tableModel.fireTableDataChanged();
		System.out.println("Refresh was called");
	}

}
