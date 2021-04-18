package se.anosh.jbstag.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.value.ValueModel;

import se.anosh.jbstag.model.GbsTag;

public class MainFrame extends JFrame {

	private JTextField titleField;
	private JTextField composerField;
	private JTextField copyrightField;

	private JTextField filenameField;
	private GbsTag bean;
	
	private static final int TEXTFIELD_COLUMNS = 30;

	public MainFrame() {

		super("Jbstag 0.2");
		bean = new GbsTag();
		bean.setTitle("The Legend of Zelda: Link's Awakening");
		bean.setComposer("Minako Hamano");
		bean.setCopyright("1993 Nintendo");
		

		// Bean adapter is an adapter that can create many value model objects for a single 
		// bean. It is more efficient than the property adapter. The 'true' once again means 
		// we want it to observe our bean for changes.
		BeanAdapter adapter = new BeanAdapter(bean, true);

		ValueModel titleModel = adapter.getValueModel("title");
		ValueModel composerModel = adapter.getValueModel("composer");
		ValueModel copyrightModel = adapter.getValueModel("copyright");

		titleField = BasicComponentFactory.createTextField(titleModel);
		composerField = BasicComponentFactory.createTextField(composerModel);
		copyrightField = BasicComponentFactory.createTextField(copyrightModel);
		titleField.setColumns(TEXTFIELD_COLUMNS);
		composerField.setColumns(TEXTFIELD_COLUMNS);
		copyrightField.setColumns(TEXTFIELD_COLUMNS);

		layoutComponents();

		setMinimumSize(new Dimension(500,200));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void layoutComponents() {

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		// ////////// First row ///////////////////////////////////

		gc.gridy = 0;

		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Title"), gc);

		gc.gridx = 1;
		gc.gridy = 0;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(titleField, gc);

		// ////////// Next row///////////////////////////////////

		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Composer"), gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(composerField, gc);
		
		// ////////// Next row///////////////////////////////////

		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Copyright"), gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(copyrightField, gc);

	}

}
