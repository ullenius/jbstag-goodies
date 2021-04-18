package se.anosh.jbstag.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Objects;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;

import se.anosh.jbstag.model.GbsBean;

public class SongPanel extends JPanel {
	
	private JTable table;
	private SongTableModel tableModel;
	
	public SongPanel() {
		tableModel = new SongTableModel();
		
		table = new JTable(tableModel);
		setLayout(new FlowLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
		setMinimumSize(new Dimension(100,200));
		setVisible(true);
	}
	
	public void refresh() {
		tableModel.fireTableDataChanged();
	}
	
	public void setData(List<GbsBean> db) {
		Objects.requireNonNull(db);
		tableModel.setData(db);
	}

}
