package se.anosh.jbstag.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class SongList extends JPanel {
	
	public SongList() {
		
		Object data[] = {"foo", "bar", "Nisse", "Lurvas", "Grant Kirkhope", "Yooka", "Laylee", "Banjo", "Kazzoie" };
		setLayout(new FlowLayout());
		
		
		JList list = new JList(data);
		list.setSize(900,200);
		list.setMinimumSize(new Dimension(500,500));
		
		add(list);
		setMinimumSize(new Dimension(100,200));
		setVisible(true);
		
	}

}
