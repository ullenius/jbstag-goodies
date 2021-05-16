package se.anosh.jbstag.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import se.anosh.jbstag.model.GbsBean;

public class SongPanel extends JPanel {
	
	private JTable table;
	private SongTableModel tableModel;
	private SongTableListener listener;
	
	public SongPanel(ListModel<GbsBean> listModel, ListSelectionModel listSelectionModel) {
		tableModel = new SongTableModel(listModel);
		table = new JTable(tableModel);

		table.setSelectionModel(listSelectionModel);
		
		setLayout(new FlowLayout());
		final JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(900,300));
		scrollPane.setMinimumSize(scrollPane.getPreferredSize());
		add(scrollPane, BorderLayout.CENTER);

		/*
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);
				listener.rowSelected(row);
			}
		});
		*/
		
		setPreferredSize(new Dimension(1000,300));
		setVisible(true);
	}

	@Deprecated
	public void refresh() {
		tableModel.fireTableDataChanged();
		System.out.println("Refresh was called");
	}

	@Deprecated
	public void setData(List<GbsBean> db) {
		System.out.println("Setdata called");
		Objects.requireNonNull(db);
		//tableModel.setData(db);
	}

	@Deprecated
	public void setSongTableListener(SongTableListener listener) {
		System.out.println("Setsongtablelistener was called");
		this.listener = Objects.requireNonNull(listener);
	}

}
