package se.anosh.jbstag.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import se.anosh.jbstag.model.GbsBean;

public class SongPanel extends JPanel {
	
	private JTable table;
	private SongTableModel tableModel;
	private SongTableListener listener;
	
	public SongPanel() {
		tableModel = new SongTableModel();
		
		table = new JTable(tableModel);
		
		TableColumnModel columnModel = table.getColumnModel();
		setLayout(new FlowLayout());
		final JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(900,300));
		scrollPane.setMinimumSize(scrollPane.getPreferredSize());
		add(scrollPane, BorderLayout.CENTER);

		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);
				listener.rowSelected(row);
			}
		});
		
		
		setPreferredSize(new Dimension(1000,300));
		setVisible(true);
	}
	
	public void refresh() {
		tableModel.fireTableDataChanged();
	}
	
	public void setData(List<GbsBean> db) {
		Objects.requireNonNull(db);
		tableModel.setData(db);
	}
	
	public void setSongTableListener(SongTableListener listener) {
		this.listener = Objects.requireNonNull(listener);
	}

}
