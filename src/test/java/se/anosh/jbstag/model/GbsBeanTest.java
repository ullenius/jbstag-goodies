package se.anosh.jbstag.model;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GbsBeanTest {

    private static final String TITLE = "Kid Dracula";
    private static final String COMPOSER = "Akiko Itoh";
    private static final String COPYRIGHT = "1993 Konami";
    private static final String FILENAME = "kiddracula.gbs";

    private static final String FULL_PATH = "/root/warez/gbs/".concat(FILENAME); // dummy path

    private ValueModel titleModel;
    private ValueModel composerModel;
    private ValueModel copyrightModel;
    private ValueModel filenameModel;
    private ValueModel fullpathModel;

    private GbsBean bean;

    @Before
    public void setup() {
        bean = new GbsBean();
        bean.setTitle(TITLE);
        bean.setComposer(COMPOSER);
        bean.setCopyright(COPYRIGHT);
        bean.setFilename(FILENAME);

        PresentationModel<GbsBean> presentationModel = new PresentationModel<>(bean);
        titleModel = presentationModel.getModel(GbsBean.PROPERTY_TITLE);
        composerModel = presentationModel.getModel(GbsBean.PROPERTY_COMPOSER);
        copyrightModel = presentationModel.getModel(GbsBean.PROPERTY_COPYRIGHT);
        filenameModel = presentationModel.getModel(GbsBean.PROPERTY_FILENAME);
        fullpathModel = presentationModel.getModel(GbsBean.PROPERTY_FULL_PATH);
    }

    @Test
    public void sanityCheck() {
        assertEquals(TITLE, titleModel.getValue());
        assertEquals(COMPOSER, composerModel.getValue());
        assertEquals(COPYRIGHT, copyrightModel.getValue());
        assertEquals(FILENAME, filenameModel.getValue());
    }

    @Test
    public void updateTitle() {
        final String NEW_TITLE = "Shantae";
        titleModel.setValue(NEW_TITLE);
        assertEquals(NEW_TITLE, bean.getTitle());

        bean.setTitle(TITLE);
        assertEquals(TITLE, titleModel.getValue());
    }

    @Test
    public void updateComposer() {
        final String NEW_COMPOSER = "M. Hamano";
        composerModel.setValue(NEW_COMPOSER);
        assertEquals(NEW_COMPOSER, bean.getComposer());

        bean.setComposer(COMPOSER);
        assertEquals(COMPOSER, composerModel.getValue());
    }

    @Test
    public void updateCopyright() {
        final String NEW_COPYRIGHT = "1993 Square";
        copyrightModel.setValue(NEW_COPYRIGHT);
        assertEquals(NEW_COPYRIGHT, bean.getCopyright());

        bean.setCopyright(COPYRIGHT);
        assertEquals(COPYRIGHT, copyrightModel.getValue());
    }

    @Test
    public void updateFilename() {
        final String NEW_FILENAME = "dkland2.gbs";
        filenameModel.setValue(NEW_FILENAME);
        assertEquals(NEW_FILENAME, bean.getFilename());

        bean.setFilename(FILENAME);
        assertEquals(FILENAME, filenameModel.getValue());
    }

    @Test
    public void updateFullpath() {
        final String NEW_FULLPATH = "/tmp/".concat(FILENAME);
        fullpathModel.setValue(NEW_FULLPATH);
        assertEquals(NEW_FULLPATH, bean.getFullpath());

        bean.setFullpath(FULL_PATH);
        assertEquals(FULL_PATH, fullpathModel.getValue());
    }

    @Test
    public void nullFullpathReturnsEmptyString() {
        fullpathModel.setValue(null);
        assertEquals("", bean.getFullpath());
    }

    @Test
    public void toStringDoesNotThrowNPEOnEmptyFullpath() {
        GbsBean bean = new GbsBean();
        assertFalse(bean.toString().isEmpty());
    }




}
