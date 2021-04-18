package se.anosh.jbstag.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.value.ValueModel;

import se.anosh.gbs.domain.ReadOnlySimpleGbsTag;
import se.anosh.gbs.service.GbsFile;
import se.anosh.jbstag.model.Bean;

public class MainFrame extends JPanel {

	private JTextField titleField;
	private JTextField composerField;
	private JTextField copyrightField;
	
	private JButton saveButton;
	private JButton openButton;
	private JTextField filenameField;
	
	private Bean bean;
	private Path filePath; // FIXME unused
	
	private ReadOnlySimpleGbsTag tag;

	private static final int TEXTFIELD_COLUMNS = 30;

	public MainFrame() {

		bean = new Bean();
		bean.setTitle("Fooobar");
		bean.setComposer("Foo composer");
		bean.setCopyright("Foobar AB");
		
		saveButton = new JButton("Save");
		openButton = new JButton("Open file");
		
		openButton.addActionListener( (e) -> openFile() );
		saveButton.addActionListener( (e -> {
			System.out.println(bean);
		}));
		
		// Bean adapter is an adapter that can create many value model objects for a single 
		// bean. It is more efficient than the property adapter. The 'true' once again means 
		// we want it to observe our bean for changes.
		BeanAdapter adapter = new BeanAdapter(bean, true);

		ValueModel titleModel = adapter.getValueModel("title");
		ValueModel composerModel = adapter.getValueModel("composer");
		ValueModel copyrightModel = adapter.getValueModel("copyright");
		ValueModel filenameModel = adapter.getValueModel("filename");

		titleField = BasicComponentFactory.createTextField(titleModel);
		composerField = BasicComponentFactory.createTextField(composerModel);
		copyrightField = BasicComponentFactory.createTextField(copyrightModel);
		filenameField = BasicComponentFactory.createTextField(filenameModel);
		
		titleField.setColumns(TEXTFIELD_COLUMNS);
		composerField.setColumns(TEXTFIELD_COLUMNS);
		copyrightField.setColumns(TEXTFIELD_COLUMNS);
		filenameField.setColumns(TEXTFIELD_COLUMNS);
		filenameField.setEditable(false);

		layoutComponents();

		setMinimumSize(new Dimension(300,300));
		setVisible(true);
	}
	
	
	private void openFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory((new File(System.getProperty("user.home"))));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Game Boy Sound files", "gbs");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(filter);

		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();

			if (readFile(selectedFile.getAbsolutePath())) {
				filePath = selectedFile.toPath();
				bean.setFilename(selectedFile.getName());
				updateFields();
			}
			//toggleInputFields();
			//toggleSaveButton();
		}
	}
	
	private void updateFields() {
		bean.setComposer(tag.getAuthor());
		bean.setTitle(tag.getTitle());
		bean.setCopyright(tag.getCopyright());
	}
	
	private boolean readFile(final String filename) {
		try {
			GbsFile reader = new GbsFile(filename);
			tag = reader.getTag();
			return true;
		} catch (IOException ex) {
			//showErrorMessageBox("Unable to open file: " + ex.getMessage());
			//showErrorMessageBox("Unable to open file: " + ex.getMessage());
			ex.getMessage();
			return false;
		}
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
		
		// ////////// Next row///////////////////////////////////
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 10);
		add(new JLabel("Filename"), gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(filenameField, gc);


		// ////////// Next row///////////////////////////////////
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 10);
		add(openButton, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(saveButton, gc);
		
	}

}
