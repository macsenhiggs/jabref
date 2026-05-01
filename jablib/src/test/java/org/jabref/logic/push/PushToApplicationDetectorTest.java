package org.jabref.logic.push;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
    A test class for JabRef push features related to the isValidAbsolutePath method
    Creates a list for each application that extends AbstractPushToApplication to ensure each one's functionality for all tests
 */

class PushToApplicationDetectorTest {
    @BeforeEach
    void setUp() {

    }

    @Test
    void absolutePathNullPathInput() {
        String path = null;
        boolean result = PushToApplicationDetector.isValidAbsolutePath(path);

        assertFalse(result);
    }

    @Test
    void absolutePathEmptyPathInput() {
        String path = "";
        boolean result = PushToApplicationDetector.isValidAbsolutePath(path);

        assertFalse(result);
    }

    @Test
    void absolutePathValidExecutablePath() throws IOException {
        Path tempFile = Files.createTempFile("jabref_push_test", ".exe");
        boolean result = PushToApplicationDetector.isValidAbsolutePath(tempFile.toString());

        assertTrue(result);
    }

    @Test
    void absolutePathInvalidExecutablePath() {
        String path = "/invalid/application";
        boolean result = PushToApplicationDetector.isValidAbsolutePath(path);

        assertFalse(result);
    }

    @Test
    void absolutePathDirectoryPath() {
        String path = "/usr/bin/";
        boolean result = PushToApplicationDetector.isValidAbsolutePath(path);

        assertFalse(result);
    }

    @Test
    void absolutePathNonExecutableFile() {
        String path = "/usr/bin/sample.txt";
        boolean result = PushToApplicationDetector.isValidAbsolutePath(path);

        assertFalse(result);
    }

    @Test
    void absolutePathWindowsExecutablePath() {
        String path = "C:\\Program Files\\texstudio\\texstudio.exe";
        boolean result = PushToApplicationDetector.isValidAbsolutePath(path);

        assertTrue(result);
    }
}
