[1mdiff --git a/src/main/java/se/anosh/jbstag/gui/MainFrame.java b/src/main/java/se/anosh/jbstag/gui/MainFrame.java[m
[1mindex 4ae3091..030f44a 100644[m
[1m--- a/src/main/java/se/anosh/jbstag/gui/MainFrame.java[m
[1m+++ b/src/main/java/se/anosh/jbstag/gui/MainFrame.java[m
[36m@@ -66,7 +66,7 @@[m [mpublic class MainFrame extends JPanel {[m
 		adapter = new PresentationModel<>(tableSelection, trigger);[m
 		beanProperty = new PropertyAdapter<Object>(adapter, "bean");[m
 		[m
[31m-		ValueModel titleModel = adapter.getBufferedModel("title"); //DRY[m
[32m+[m		[32mValueModel titleModel = adapter.getBufferedModel(GbsBean.PROPERTY_TITLE); //DRY[m
 		createFields(adapter);[m
 [m
 		// bind buttons to actions[m
[36m@@ -100,7 +100,7 @@[m [mpublic class MainFrame extends JPanel {[m
 	private void saveToFile() throws IOException {[m
 		updateTag();[m
 [m
[31m-		AbstractValueModel filepathModel = adapter.getModel("fullpath");[m
[32m+[m		[32mAbstractValueModel filepathModel = adapter.getModel(GbsBean.PROPERTY_FULL_PATH);[m
 		String path = filepathModel.getString();[m
 [m
 		Gbs gbs = new GbsFile(path);[m
[36m@@ -123,9 +123,9 @@[m [mpublic class MainFrame extends JPanel {[m
 	}[m
 [m
 	private void updateTag() {[m
[31m-		AbstractValueModel titleModel = adapter.getModel("title");[m
[31m-		AbstractValueModel composerModel = adapter.getModel("composer");[m
[31m-		AbstractValueModel copyrightModel = adapter.getModel("copyright");[m
[32m+[m		[32mAbstractValueModel titleModel = adapter.getModel(GbsBean.PROPERTY_TITLE);[m
[32m+[m		[32mAbstractValueModel composerModel = adapter.getModel(GbsBean.PROPERTY_COMPOSER);[m
[32m+[m		[32mAbstractValueModel copyrightModel = adapter.getModel(GbsBean.PROPERTY_COPYRIGHT);[m
 [m
 		String title = titleModel.getString();[m
 		String composer = composerModel.getString();[m
[36m@@ -138,10 +138,10 @@[m [mpublic class MainFrame extends JPanel {[m
 [m
 	private void createFields(PresentationModel<GbsBean> adapter) {[m
 		// creating ValueModels[m
[31m-		ValueModel titleModel = adapter.getBufferedModel("title");[m
[31m-		ValueModel composerModel = adapter.getBufferedModel("composer");[m
[31m-		ValueModel copyrightModel = adapter.getBufferedModel("copyright");[m
[31m-		ValueModel filenameModel = adapter.getBufferedModel("filename");[m
[32m+[m		[32mValueModel titleModel = adapter.getBufferedModel(GbsBean.PROPERTY_TITLE);[m
[32m+[m		[32mValueModel composerModel = adapter.getBufferedModel(GbsBean.PROPERTY_COMPOSER);[m
[32m+[m		[32mValueModel copyrightModel = adapter.getBufferedModel(GbsBean.PROPERTY_COPYRIGHT);[m
[32m+[m		[32mValueModel filenameModel = adapter.getBufferedModel(GbsBean.PROPERTY_FILENAME);[m
 [m
 		titleField = BasicComponentFactory.createTextField(titleModel);[m
 		composerField = BasicComponentFactory.createTextField(composerModel);[m
[36m@@ -236,20 +236,20 @@[m [mpublic class MainFrame extends JPanel {[m
 		builder.border(Paddings.DIALOG); // replaces the deprecated setDefaultDialogBorder();[m
 		CellConstraints cc = new CellConstraints();[m
 [m
[31m-		builder.addLabel("Title",    cc.xy(1, 1));[m
[31m-		builder.add(titleField,         			 cc.xy(3, 1));[m
[32m+[m		[32mbuilder.addLabel("Title", cc.xy(1, 1));[m
[32m+[m		[32mbuilder.add(titleField, cc.xy(3, 1));[m
 		builder.addLabel("Composer", cc.xy(1, 3));[m
[31m-		builder.add(composerField,         			 cc.xy(3, 3));[m
[32m+[m		[32mbuilder.add(composerField, cc.xy(3, 3));[m
 		builder.addLabel("Copyright", cc.xy(1, 5));[m
[31m-		builder.add(copyrightField,        			 cc.xy(3, 5));[m
[32m+[m		[32mbuilder.add(copyrightField, cc.xy(3, 5));[m
 		[m
 		builder.addLabel("Filename", cc.xy(1, 7));[m
[31m-		builder.add(filenameField,					 cc.xy(3, 7));[m
[32m+[m		[32mbuilder.add(filenameField, cc.xy(3, 7));[m
 		[m
[31m-		builder.add(openButton,	    				 cc.xy(1, 9));[m
[31m-		builder.add(saveButton,	    				 cc.xy(3, 9));[m
[32m+[m		[32mbuilder.add(openButton, cc.xy(1, 9));[m
[32m+[m		[32mbuilder.add(saveButton, cc.xy(3, 9));[m
 		[m
[31m-		builder.add(gameboy,					 cc.xy(3, 11, CellConstraints.FILL, CellConstraints.CENTER));[m
[32m+[m		[32mbuilder.add(gameboy, cc.xy(3, 11, CellConstraints.FILL, CellConstraints.CENTER));[m
 		return builder.getPanel();[m
 	}[m
 [m
