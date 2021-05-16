package se.anosh.jbstag.gui;


import javax.swing.*;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import se.anosh.jbstag.model.GbsBean;

public class SongTableModel extends AbstractTableAdapter<GbsBean> {

	private static final String[] columnNames = { "Title", "Composer", "Copyright", "Filename" };

	public SongTableModel(ListModel<GbsBean> listModel) {
		super(listModel, columnNames);
	}

	@Override
	public Object getValueAt(int row, int column) {
		GbsBean gbs = getRow(row);
		
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

}
