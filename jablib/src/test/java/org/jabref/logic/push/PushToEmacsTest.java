package org.jabref.logic.push;

import java.util.ArrayList;
import java.util.List;

import org.jabref.logic.util.NotificationService;
import org.jabref.model.entry.BibEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PushToEmacsTest {
    private PushToEmacs pushToEmacs;

    @BeforeEach
    void setUp() {
        NotificationService notifService = message -> {};
        PushToApplicationPreferences preferences = PushToApplicationPreferences.getDefault();

        pushToEmacs = new PushToEmacs(notifService,preferences);
    }

    @Test
    void keyStringEmptyEntryList() {
        List<BibEntry> entries = new ArrayList<BibEntry>();

        String result = pushToEmacs.getKeyString(entries, ",");
        String expected = "";

        assertEquals(expected, result);
    }

    @Test
    void keyStringSingleValidEntry() {
        List<BibEntry> entries = new ArrayList<BibEntry>();
        entries.add(new BibEntry("Smith2020"));

        String result = pushToEmacs.getKeyString(entries, ",");
        String expected = "Smith2020";

        assertEquals(expected, result);
    }

    @Test
    void keyStringMultipleValidEntries() {
        List<BibEntry> entries = new ArrayList<BibEntry>();
        entries.add(new BibEntry("Smith2020"));
        entries.add(new BibEntry("Lee2021"));

        String result = pushToEmacs.getKeyString(entries, ",");
        String expected = "Smith2020,Lee2021";

        assertEquals(expected, result);
    }

    @Test
    void keyStringEntryMissingCitationKey() {
        List<BibEntry> entries = new ArrayList<BibEntry>();
        entries.add(new BibEntry());

        String result = pushToEmacs.getKeyString(entries, ",");
        String expected = "";

        assertEquals(expected, result);
    }

    @Test
    void keyStringMixedEntries() {
        List<BibEntry> entries = new ArrayList<BibEntry>();
        entries.add(new BibEntry("Smith2020"));
        entries.add(new BibEntry());

        String result = pushToEmacs.getKeyString(entries, ",");
        String expected = "Smith2020";

        assertEquals(expected, result);
    }

    @Test
    void keyStringAlternateDelimiter() {
        List<BibEntry> entries = new ArrayList<BibEntry>();
        entries.add(new BibEntry("Smith2020"));
        entries.add(new BibEntry("Lee2021"));

        String result = pushToEmacs.getKeyString(entries, ";");
        String expected = "Smith2020;Lee2021";

        assertEquals(expected, result);
    }

    @Test
    void pushEntriesPushSingleEntry() {

    }

    @Test
    void pushEntriesPushMultipleEntries() {

    }

    @Test
    void pushEntriesMissingCitation() {

    }

    @Test
    void pushEntriesEmptyEntryList() {

    }

    @Test
    void pushEntriesLargeEntrySet() {

    }

    @Test
    void pushEntriesUnsupportedApplication() {

    }

    @Test
    void pushEntriesInvalidApplicationPath() {

    }
}
