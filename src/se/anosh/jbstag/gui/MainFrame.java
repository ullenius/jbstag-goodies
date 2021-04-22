package se.anosh.jbstag.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.PropertyAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.value.Trigger;
import com.jgoodies.binding.value.ValueModel;

import se.anosh.gbs.domain.ReadOnlySimpleGbsTag;
import se.anosh.gbs.service.GbsFile;
import se.anosh.jbstag.model.GbsBean;

public class MainFrame extends JPanel {

	private JTextField titleField;
	private JTextField composerField;
	private JTextField copyrightField;

	private JButton saveButton;
	private JButton openButton;
	private JTextField filenameField;
	private AddGbsFileListener addFileListener;
	private final Trigger trigger;
	
	private Path filePath;
	
	private ValueModel beanProperty;

	private ReadOnlySimpleGbsTag tag;
	private final List<GbsBean> db;

	private static final int TEXTFIELD_COLUMNS = 25;

	public MainFrame(List<GbsBean> database) {
		this.db = Objects.requireNonNull(database);

		GbsBean bean = new GbsBean();
		bean.setTitle("Fooobar");
		bean.setComposer("Foo composer");
		bean.setCopyright("Foobar AB");

		// Bean adapter is an adapter that can create many value model objects for a single 
		// bean. It is more efficient than the property adapter. The 'true' once again means 
		// we want it to observe our bean for changes.
		this.trigger = new Trigger();
		PresentationModel adapter = new PresentationModel(bean, trigger); // PRESENTATION MODEL
		beanProperty = new PropertyAdapter(adapter, "bean");
		
		// creating ValueModels
		ValueModel titleModel = adapter.getBufferedModel("title");
		ValueModel composerModel = adapter.getBufferedModel("composer");
		ValueModel copyrightModel = adapter.getBufferedModel("copyright");
		ValueModel filenameModel = adapter.getBufferedModel("filename");

		titleField = BasicComponentFactory.createTextField(titleModel);
		composerField = BasicComponentFactory.createTextField(composerModel);
		copyrightField = BasicComponentFactory.createTextField(copyrightModel);
		filenameField = BasicComponentFactory.createTextField(filenameModel);

		titleField.setColumns(TEXTFIELD_COLUMNS);
		composerField.setColumns(TEXTFIELD_COLUMNS);
		copyrightField.setColumns(TEXTFIELD_COLUMNS);
		filenameField.setColumns(TEXTFIELD_COLUMNS);
		filenameField.setEditable(false);

		// bind buttons to actions
		saveButton = new JButton("Save");
		PropertyConnector.connect(adapter, "buffering", saveButton, "enabled");
		
		openButton = new JButton("Open file");

		openButton.addActionListener( (e) -> {
			System.out.println("Flushing/rollback commits");
			trigger.triggerFlush();
			openFile();
		});	
		saveButton.setEnabled(Boolean.FALSE);
		saveButton.addActionListener( (e -> {
			trigger.triggerCommit();
			System.out.println("Committing changes to bean");
			System.out.println(bean);
			System.out.println("Valuemodel title: " + titleModel.getValue());
			addFileListener.refresh();
		}));
		
		layoutComponents();

		setMinimumSize(new Dimension(420,300));
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
				updateFields(selectedFile.getName());
			}
			//toggleInputFields();
			//toggleSaveButton();
		}
	}

	// Event listener
	public void setAddFileListener(AddGbsFileListener listener) {
		this.addFileListener = listener;
	}

	private void updateFields(String filename) {
		GbsBean newBean = new GbsBean();
		newBean.setComposer(tag.getAuthor());
		newBean.setCopyright(tag.getCopyright());
		newBean.setTitle(tag.getTitle());
		newBean.setFilename(filename);
		db.add(newBean);
		addFileListener.refresh();
		
		beanProperty.setValue(newBean);
		trigger.triggerCommit();
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

	public void showSong(GbsBean show) {
			beanProperty.setValue(show);
			trigger.triggerCommit();
			System.out.println("Displaying " + show);
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
		gc.insets = new Insets(0, 0, 0, 0);
		add(openButton, gc);

		gc.gridx = 1;
		gc.insets = new Insets(1, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(saveButton, gc);

	}

}
