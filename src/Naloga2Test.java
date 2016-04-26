import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

public class Naloga2Test {

    @Rule
    public Timeout globalTimeout = new Timeout(15000);

    // Path to test files, relative to idea project root.
    static final String TESTS_FOLDER_PATH = "./test-files";

    File[] inFiles, exFiles, outFiles;

    @Before
    public void setUp() throws Exception {
        // Verify that `TESTS_FOLDER_PATH` exists.
        File testsFolder = new File(TESTS_FOLDER_PATH);
        if (!testsFolder.exists()) {
            throw new Error(String.format("Path to tests folder (%s) does not exist.", TESTS_FOLDER_PATH));
        }

        // Read input and expected files.
        inFiles = testsFolder.listFiles(extensionFilter(".in"));
        exFiles = testsFolder.listFiles(extensionFilter(".expected"));
        // Sort input and expected files to follow order expected in args.
        Arrays.sort(inFiles, new TestFileComparator());
        Arrays.sort(exFiles, new TestFileComparator());
        // Copy input file names to corresponding output file.
        outFiles = new File[inFiles.length];
        for (int i = 0; i < inFiles.length; i++) {
            outFiles[i] = new File(inFiles[i].getAbsolutePath().replace(".in", ".out"));
        }

        // Confirm that number of found files match to number of args.
        if (inFiles.length != args.length || exFiles.length != args.length) {
            throw new Error(String.format("Missing matching pair for inputs (%d) or expected outs (%d) to args (%d)",
                    inFiles.length, exFiles.length, args.length));
        }
    }

    @Test
    public void testOS() throws Exception {
        runTests("os");
    }

    @Test
    public void testRU() throws Exception {
        runTests("ru");
    }

    @Test
    public void testDV() throws Exception {
        runTests("dv");
    }

    @Test
    public void testKA() throws Exception {
        runTests("ka");
    }

    private void runTests(String algorithm) throws Exception {
        // Store initial stdio.
        InputStream systemIn = System.in;
        PrintStream systemOut = System.out;
        for (int i = 0; i < inFiles.length; i++) {
            // Skip non-matching algorithms.
            if (!args[i][1].equalsIgnoreCase(algorithm)) {
                continue;
            }
            // Get input, expected and output file.
            File in = inFiles[i], ex = exFiles[i], out = outFiles[i];
            // Set input file as system standard input.
            System.setIn(new FileInputStream(in));
            // Set output file as system standard output.
            System.setOut(new PrintStream(out));
            long start = System.nanoTime();
            // Call Naloga2 main method with corresponding arguments.
            Naloga2.main(args[i]);
            long end = System.nanoTime();
            // Reset to initial stdio.
            System.setIn(systemIn);
            System.setOut(systemOut);

            System.out.printf("(%s) %s: %.3fms\n", algorithm, in.getName().split("\\.")[0], (end - start) / 1e6);

            // Build assertion message and assert that expected file and Naloga2 input match.
            String message = String.format("'%s %s': %s %s %s", args[i][0], args[i][1], in.getName(), out.getName(), ex.getName());
            Assert.assertEquals(message, readFile(ex), readFile(out));
        }
    }

    private static String readFile(File f) throws IOException {
        return new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())), StandardCharsets.UTF_8)
                .trim().replaceAll("\\r\\n|\\r|\\n", "\n");
    }

    String[][] args = new String[][]{
            // test-A-0
            new String[]{"trace", "ka",},
            // test-A-1
            new String[]{"trace", "os",},
            // test-A-2
            new String[]{"trace", "ru",},
            // test-A-3
            new String[]{"trace", "dv",},
            // test-A-4
            new String[]{"trace", "ka",},
            // test-B-1
            new String[]{"trace", "os",},
            // test-B-2
            new String[]{"trace", "os",},
            // test-B-3
            new String[]{"trace", "os",},
            // test-B-4
            new String[]{"trace", "ru",},
            // test-B-5
            new String[]{"trace", "ru",},
            // test-B-6
            new String[]{"trace", "ru",},
            // test-B-7
            new String[]{"trace", "dv",},
            // test-B-8
            new String[]{"trace", "dv",},
            // test-B-9
            new String[]{"trace", "dv",},
            // test-B-10
            new String[]{"trace", "ka",},
            // test-B-11
            new String[]{"trace", "ka",},
            // test-B-12
            new String[]{"trace", "ka",},
            // test-C-1
            new String[]{"count", "os",},
            // test-C-2
            new String[]{"count", "os",},
            // test-C-3
            new String[]{"count", "ru",},
            // test-C-4
            new String[]{"count", "ru",},
            // test-C-5
            new String[]{"count", "dv",},
            // test-C-6
            new String[]{"count", "dv",},
            // test-C-7
            new String[]{"count", "ka",},
            // test-C-8
            new String[]{"count", "ka",},
    };

    private FilenameFilter extensionFilter(final String ext) {
        return new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(ext);
            }
        };
    }

    private class TestFileComparator implements Comparator<File> {

        /**
         * Expected file name format is test-([A-Z])-([0-9]+).*
         * Sorts files by first the test name and then by number.
         */
        public int compare(File file1, File file2) {
            TestFileName name1 = new TestFileName(file1), name2 = new TestFileName(file2);
            int nameCmp = name1.getTestName().compareTo(name2.getTestName());
            return nameCmp != 0 ? nameCmp : Integer.compare(name1.getTestNumber(), name2.getTestNumber());
        }

        private class TestFileName {
            private final String[] parts;

            public TestFileName(File f) {
                parts = f.getName().split("\\.")[0].split("-");
            }

            private String getTestName() {
                return parts[1];
            }

            private int getTestNumber() {
                return Integer.parseInt(parts[2]);
            }
        }
    }
}

