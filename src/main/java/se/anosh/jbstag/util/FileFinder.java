package se.anosh.jbstag.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class FileFinder extends SimpleFileVisitor<Path> {

    private final PathMatcher matcher;
    private final List<Path> myList = new LinkedList<>();

    public FileFinder(String pattern) {
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    }

    // Compares the glob pattern against
    // the file or directory name.
    void match(Path file) {
        Path name = file.getFileName();
        if (name != null && matcher.matches(name)) {
            myList.add(file.toAbsolutePath());
        }
    }

    // Invoke the pattern matching
    // method on each file.
    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) {
        match(file);
        return FileVisitResult.CONTINUE;
    }

    public List<Path> getList() {
        return Collections.unmodifiableList(myList);
    }

    public int getTotalMatches() {
        return myList.size();
    }


}
