package se.anosh.jbstag.util;

import org.pmw.tinylog.Logger;
import se.anosh.jbstag.util.FileFinder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TraverseFiles {

    public static Stream<Path> stream(File dir) throws IOException {
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
        return fs.getList().stream();
    }


}
