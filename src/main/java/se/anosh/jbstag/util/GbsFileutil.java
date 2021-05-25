package se.anosh.jbstag.util;

import org.pmw.tinylog.Logger;
import se.anosh.gbs.domain.ReadOnlySimpleGbsTag;
import se.anosh.gbs.domain.Tag;
import se.anosh.gbs.service.Gbs;
import se.anosh.gbs.service.GbsFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class GbsFileutil {

    public static void writeChanges(String path, ReadOnlySimpleGbsTag oldtag) throws IOException {
        Gbs gbs = new GbsFile(path);
        Tag filetag = gbs.getTag();
        final int oldHash = Objects.hash(oldtag.getAuthor(), oldtag.getCopyright(), oldtag.getTitle());
        final int currentHash = Objects.hash(filetag.getAuthor(), filetag.getCopyright(), filetag.getTitle());

        if (oldHash != currentHash) {
            Logger.debug("Hash has changed, writing changes");
            filetag.setCopyright(oldtag.getCopyright());
            filetag.setTitle(oldtag.getTitle());
            filetag.setAuthor(oldtag.getAuthor());
            gbs.save();
        }
        else {
            Logger.debug("Hash has not changed. Do nothing");
        }
        Logger.debug("Old hash: {}", oldHash);
        Logger.debug("New hash: {}", currentHash);
    }

}
