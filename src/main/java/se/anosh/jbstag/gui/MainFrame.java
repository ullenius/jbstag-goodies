package se.anosh.jbstag.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.PropertyAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.Trigger;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import se.anosh.gbs.domain.ReadOnlySimpleGbsTag;
import se.anosh.gbs.service.GbsFile;
import se.anosh.jbstag.model.GbsBean;

public class MainFrame extends JPanel {
	
	private static final long serialVersionUID = 0xBEEF;

	private JTextField titleField;
	private JTextField composerField;
	private JTextField copyrightField;

	private JButton saveButton;
	private JButton openButton;
	private JTextField filenameField;
	private AddGbsFileListener addFileListener;
	private final Trigger trigger;
	
	private ValueModel beanProperty;

	private ReadOnlySimpleGbsTag tag;
	private final List<GbsBean> db;

	private static final int TEXTFIELD_COLUMNS = 25;

	public MainFrame(SelectionInList<GbsBean> tableSelection, List<GbsBean> database) {
		this.db = Objects.requireNonNull(database);

		this.trigger = new Trigger();
		PresentationModel adapter = new PresentationModel(tableSelection, trigger); // PRESENTATION MODEL
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
		saveButton.setPreferredSize(openButton.getPreferredSize());

		openButton.addActionListener( (e) -> {
			System.out.println("Flushing/rollback commits");
			trigger.triggerFlush();
			openFile();
		});	
		saveButton.setEnabled(Boolean.FALSE);
		saveButton.addActionListener( (e -> {
			trigger.triggerCommit();
			System.out.println("Committing changes to bean");
			//System.out.println(bean);
			System.out.println("Valuemodel title: " + titleModel.getValue());
		}));
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
				updateFields(selectedFile.getName());
			}
		}
	}

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
		} catch (IOException | IllegalArgumentException ex) {
			showErrorMessageBox("Unable to open file: " + ex.getMessage());
			return false;
		}
	}
	
	private void showErrorMessageBox(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void showSong(GbsBean show) {
			beanProperty.setValue(show);
			trigger.triggerCommit();
			System.out.println("Displaying " + show);
	}

	JPanel buildFormPanel() {
		
		JLabel gameboy = new JLabel(new ImageIcon(getClass().getResource("/gameboy-tahsin.png")));
		gameboy.setToolTipText("GBS Tagger");
		gameboy.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				credits();
			}
		});
		
		FormLayout layout = new FormLayout(
				"right:pref, $lcg, left:pref:grow",	// 3 columns
				"pref, 10dlu, pref, 10dlu, pref, 10dlu, pref, 20dlu, pref, $lcg, pref");// 11 rows
		layout.setColumnGroups( new int[][] { { 1, 3 } } );
		layout.setRowGroups( new int[][] { { 2, 4, 6  } } );

		JPanel panel = new JPanel(layout);
		panel.setVisible(true);
		add(panel);

		PanelBuilder builder = new PanelBuilder(layout, new FormDebugPanel());
		//PanelBuilder builder = new PanelBuilder(layout);
		builder.border(Borders.DIALOG); // replaces the deprecated setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Title",       	cc.xy(1, 1));
		builder.add(titleField,         	cc.xy(3, 1));
		builder.addLabel("Composer",       	cc.xy(1, 3));
		builder.add(composerField,         	cc.xy(3, 3));
		builder.addLabel("Copyright",       cc.xy(1, 5));
		builder.add(copyrightField,         cc.xy(3, 5));
		
		builder.addLabel("Filename",		cc.xy(1, 7));
		builder.add(filenameField,			cc.xy(3, 7));
		
		builder.add(openButton,	    		cc.xy(1, 9));
		builder.add(saveButton,	    		cc.xy(3, 9));
		
		builder.add(gameboy,				cc.xy(3, 11, CellConstraints.FILL, CellConstraints.CENTER));
		return builder.getPanel();
	}
	
	private void credits() {
		JOptionPane.showMessageDialog(this, "GBS Tagger made by Anosh <git@anosh.se> in 2019-2021.\n\n" +
				"Licensed under GNU GPL version 3, see enclosed file COPYING for full licence.\n\n"
				+ "Uses the gbs-lib library which is licensed under LGPL 2.1\n\n"
				+ "Image by Tahsin Tahil (Creative Commons Attribution 3.0 Unported)", "About GBS Tagger", JOptionPane.INFORMATION_MESSAGE);
	}


}
