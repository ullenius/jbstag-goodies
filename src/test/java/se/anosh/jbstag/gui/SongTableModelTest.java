package se.anosh.jbstag.gui;

import org.junit.Before;
import org.junit.Test;
import se.anosh.jbstag.model.GbsBean;

import javax.swing.*;
import javax.swing.event.ListDataListener;

import static org.junit.Assert.assertEquals;

public class SongTableModelTest {

    private GbsBean bean;

    private static final String TITLE = "Symphony";
    private static final String COMPOSER = "Mozart";
    private static final String COPYRIGHT = "Public domain";
    private static final String FILENAME = "mozart.gbs";

    private static final int FIRST = 0;

    @Before
    public void setup() {
        bean = new GbsBean();
        bean.setTitle(TITLE);
        bean.setComposer(COMPOSER);
        bean.setCopyright(COPYRIGHT);
        bean.setFilename(FILENAME);
    }

    @Test
    public void correctColumns() {
        ListModel<GbsBean> mock = new Mock();
        SongTableModel songTableModel = new SongTableModel(mock);

        assertEquals(TITLE, songTableModel.getValueAt(FIRST,0));
        assertEquals(COMPOSER, songTableModel.getValueAt(FIRST,1));
        assertEquals(COPYRIGHT, songTableModel.getValueAt(FIRST,2));
        assertEquals(FILENAME, songTableModel.getValueAt(FIRST, 3));
        }

        private final class Mock implements ListModel<GbsBean> {

            @Override
            public int getSize() {
                return 1;
            }

            @Override
            public GbsBean getElementAt(int index) {
                return bean;
            }

            @Override
            public void addListDataListener(ListDataListener l) {
            }

            @Override
            public void removeListDataListener(ListDataListener l) {
            }
        }


}
