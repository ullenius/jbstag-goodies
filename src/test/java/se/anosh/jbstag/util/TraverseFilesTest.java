package se.anosh.jbstag.util;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class TraverseFilesTest {

    private Path directory;
    private Path FOO_GBS;
    private Path BAR_GBS;
    private Path BAZ_GBS;

    @Before
    public void setup() {
         directory = Paths.get("src/main/resources");
         FOO_GBS = Paths.get("foo.gbs");
         BAR_GBS = Paths.get("bar.gbs");
         BAZ_GBS = Paths.get("baz.gbs");
    }

    @Test
    public void findGbsFiles() throws IOException {
        List<Path> results =  TraverseFiles
                .stream(directory.toFile())
                .collect(Collectors.toList());

        assertEquals(3, results.size());
        assertEquals(BAZ_GBS, results.get(0).getFileName());
        assertEquals(BAR_GBS, results.get(1).getFileName());
        assertEquals(FOO_GBS, results.get(2).getFileName());
    }



}
