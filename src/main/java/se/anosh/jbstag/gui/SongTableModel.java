package se.anosh.jbstag.gui;

import java.util.List;
import java.util.Objects;

import javax.swing.table.AbstractTableModel;

import se.anosh.jbstag.model.GbsBean;

public class SongTableModel extends AbstractTableModel {

	private List<GbsBean> database;

	private final String columnNames[] = { "Title", "Composer", "Copyright", "Filename" };

	public SongTableModel() {
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		return database.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int row, int column) {
		GbsBean gbs = database.get(row);
		
		switch (column) {
		case 0:
			return gbs.getTitle();
		case 1:
			return gbs.getComposer();
		case 2:
			return gbs.getCopyright();
		case 3:
			return gbs.getFilename();
		default:
			return null;
		}
	}
	
	public void setData(List<GbsBean> data) {
		this.database = Objects.requireNonNull(data);
	}
	

}
