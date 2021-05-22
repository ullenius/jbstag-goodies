package se.anosh.jbstag.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.jgoodies.binding.value.AbstractValueModel;
import com.jgoodies.binding.value.Trigger;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.factories.Paddings;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import org.pmw.tinylog.Logger;
import se.anosh.gbs.domain.SimpleGbsTag;
import se.anosh.gbs.domain.Tag;
import se.anosh.gbs.service.Gbs;
import se.anosh.gbs.service.GbsFile;
import se.anosh.jbstag.model.GbsBean;
import se.anosh.jbstag.util.FileFinder;

public class MainFrame extends JPanel {

	private static final long serialVersionUID = 0xBEEF;
	private static final boolean DEBUG = true;

	private JTextField titleField;
	private JTextField composerField;
	private JTextField copyrightField;

	private final JButton saveButton;
	private final JButton openButton;
	private final JButton addFolderButton;
	private JTextField filenameField;
	private AddGbsFileListener addFileListener;
	private final Trigger trigger;
	
	private ValueModel beanProperty;
	private PresentationModel<GbsBean> adapter;

	private SimpleGbsTag tag;
	private final List<GbsBean> db;

	private static final int TEXTFIELD_COLUMNS = 25;

	public MainFrame(SelectionInList<GbsBean> tableSelection, List<GbsBean> database) {
		this.db = Objects.requireNonNull(database);
		this.trigger = new Trigger();
		adapter = new PresentationModel<>(tableSelection, trigger);
		beanProperty = new PropertyAdapter<Object>(adapter, PresentationModel.PROPERTY_BEAN);

		ValueModel titleModel = adapter.getBufferedModel(GbsBean.PROPERTY_TITLE); //DRY
		createFields(adapter);

		// bind buttons to actions
		saveButton = new JButton("Save");
		PropertyConnector.connect(adapter, PresentationModel.PROPERTY_BUFFERING, saveButton, "enabled");

		openButton = new JButton("Open file");

		addFolderButton = new JButton("Add folder");
		addFolderButton.addActionListener((e) -> {
			Logger.debug("Add folder");
			try {
				openDirectory();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		});
		saveButton.setPreferredSize(addFolderButton.getPreferredSize());
		openButton.setPreferredSize(addFolderButton.getPreferredSize());

		openButton.addActionListener( (e) -> {
			Logger.debug("Flushing/rollback commits");
			trigger.triggerFlush();
			openFile();
		});	
		saveButton.setEnabled(Boolean.FALSE);
		saveButton.addActionListener( (e -> {
			trigger.triggerCommit();
			Logger.debug("Committing changes to bean");
			Logger.debug("Valuemodel title: {}", titleModel.getValue());

			try {
				saveToFile();
				addFileListener.refresh();
			} catch (IOException ex) {
				Logger.error("Write error", ex);
				showErrorMessageBox("Unable to open file: " + ex.getMessage());
			}
		}));
	}

	private void saveToFile() throws IOException {
		updateTag();

		AbstractValueModel filepathModel = adapter.getModel(GbsBean.PROPERTY_FULL_PATH);
		String path = filepathModel.getString();

		Gbs gbs = new GbsFile(path);
		Tag filetag = gbs.getTag();
		final int oldHash = Objects.hash(tag.getAuthor(), tag.getCopyright(), tag.getTitle());
		final int currentHash = Objects.hash(filetag.getAuthor(), filetag.getCopyright(), filetag.getTitle());

		if (oldHash != currentHash) {
			Logger.debug("Hash has changed, writing changes");
			filetag.setCopyright(tag.getCopyright());
			filetag.setTitle(tag.getTitle());
			filetag.setAuthor(tag.getAuthor());
			gbs.save();
		}
		else {
			Logger.debug("Hash has not changed. Do nothing");
		}
		Logger.debug("Old hash: {}", oldHash);
		Logger.debug("New hash: {}", currentHash);
	}

	private void updateTag() {
		AbstractValueModel titleModel = adapter.getModel(GbsBean.PROPERTY_TITLE);
		AbstractValueModel composerModel = adapter.getModel(GbsBean.PROPERTY_COMPOSER);
		AbstractValueModel copyrightModel = adapter.getModel(GbsBean.PROPERTY_COPYRIGHT);

		String title = titleModel.getString();
		String composer = composerModel.getString();
		String copyright = copyrightModel.getString();

		tag.setAuthor(composer);
		tag.setCopyright(copyright);
		tag.setTitle(title);
	}

	private void createFields(PresentationModel<GbsBean> adapter) {
		// creating ValueModels
		ValueModel titleModel = adapter.getBufferedModel(GbsBean.PROPERTY_TITLE);
		ValueModel composerModel = adapter.getBufferedModel(GbsBean.PROPERTY_COMPOSER);
		ValueModel copyrightModel = adapter.getBufferedModel(GbsBean.PROPERTY_COPYRIGHT);
		ValueModel filenameModel = adapter.getBufferedModel(GbsBean.PROPERTY_FILENAME);

		titleField = BasicComponentFactory.createTextField(titleModel);
		composerField = BasicComponentFactory.createTextField(composerModel);
		copyrightField = BasicComponentFactory.createTextField(copyrightModel);
		filenameField = BasicComponentFactory.createTextField(filenameModel);

		titleField.setColumns(TEXTFIELD_COLUMNS);
		composerField.setColumns(TEXTFIELD_COLUMNS);
		copyrightField.setColumns(TEXTFIELD_COLUMNS);
		filenameField.setColumns(TEXTFIELD_COLUMNS);
		filenameField.setEditable(false);
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
				updateFields(selectedFile.getName(), selectedFile.getAbsolutePath());
			}
		}
	}

	private void openDirectory() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory((new File(System.getProperty("user.home"))));
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			Logger.debug("approved option");
			File selectedDirectory = fileChooser.getSelectedFile();
			Logger.debug("selected dir: {}", selectedDirectory);
			traverseAndAdd(selectedDirectory);
		}
		else {
			Logger.debug("not approved");
		}
	}

	private void traverseAndAdd(File dir) throws IOException {
		Logger.debug("dir absPath {}", dir.getAbsolutePath());
		Path path = dir.toPath();
		if (Files.isSymbolicLink(path)) {
			Logger.debug("Path is symlink");
			path = path.toRealPath();
			Logger.debug("real path resolved: {}", path);
		}

		FileFinder fs = new FileFinder("*.gbs");
		// pass the initial directory and the finder to the file tree walker
		Files.walkFileTree(Paths.get(path.toString()), fs);
		// get the matched paths
		Logger.debug("total matches {}", fs.getTotalMatches());
		List<Path> files = fs.getList();
		//System.out.println(files);
		for (Path p : files) {
			if (readFile(p.toString())) {
				updateFields(p.getFileName().toString(), p.toAbsolutePath().toString());
			}
		}
	}

	public void setAddFileListener(AddGbsFileListener listener) {
		this.addFileListener = listener;
	}

	private void updateFields(String filename, String fullpath) {
		Logger.debug("Fullpath = {}", fullpath);

		GbsBean newBean = new GbsBean();
		newBean.setComposer(tag.getAuthor());
		newBean.setCopyright(tag.getCopyright());
		newBean.setTitle(tag.getTitle());
		newBean.setFilename(filename);
		newBean.setFullpath(fullpath);

		db.add(newBean);

		addFileListener.refresh();
		beanProperty.setValue(newBean);
		trigger.triggerCommit();
	}

	private boolean readFile(final String filename) {
		try {
			Gbs reader = new GbsFile(filename);
			tag = reader.getTag();
			return true;
		} catch (IOException | IllegalArgumentException ex) {
			Logger.error("Readfile error", ex);
			showErrorMessageBox("Unable to open file: " + ex.getMessage());
			return false;
		}
	}

	private void showErrorMessageBox(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
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
				"right:75dlu, $lcg, left:pref:grow",	// 3 columns
				"pref, 10dlu, pref, 10dlu, pref, 10dlu, pref, 20dlu, pref, $lcg, pref, $lcg, pref");// 13 rows
		//layout.setColumnGroups( new int[][] { { 1, 3 } } );
		layout.setRowGroups( new int[][] { { 2, 4, 6  } } );

		JPanel panel = new JPanel(layout);
		panel.setVisible(true);
		add(panel);

		PanelBuilder builder = createPanelBuilder(layout);
		builder.border(Paddings.DIALOG); // replaces the deprecated setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Title", cc.xy(1, 1));
		builder.add(titleField, cc.xy(3, 1));
		builder.addLabel("Composer", cc.xy(1, 3));
		builder.add(composerField, cc.xy(3, 3));
		builder.addLabel("Copyright", cc.xy(1, 5));
		builder.add(copyrightField, cc.xy(3, 5));
		
		builder.addLabel("Filename", cc.xy(1, 7));
		builder.add(filenameField, cc.xy(3, 7));
		
		builder.add(openButton, cc.xy(1, 9));
		builder.add(saveButton, cc.xy(3, 9));

		builder.add(addFolderButton, cc.xy(1, 11));
		
		builder.add(gameboy, cc.xy(3, 13, CellConstraints.FILL, CellConstraints.CENTER));
		return builder.getPanel();
	}

	private PanelBuilder createPanelBuilder(FormLayout layout) {
		return DEBUG ? new PanelBuilder(layout, new FormDebugPanel()) : new PanelBuilder(layout);
	}
	
	private void credits() {
		JOptionPane.showMessageDialog(this, "GBS Tagger made by Anosh <git@anosh.se> in 2019-2021.\n\n" +
				"Licensed under GNU GPL version 3, see enclosed file COPYING for full licence.\n\n"
				+ "Uses the gbs-lib library which is licensed under LGPL 2.1\n\n"
				+ "Image by Tahsin Tahil (Creative Commons Attribution 3.0 Unported)", "About GBS Tagger", JOptionPane.INFORMATION_MESSAGE);
	}


}
