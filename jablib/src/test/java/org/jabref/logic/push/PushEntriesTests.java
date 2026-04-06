package org.jabref.logic.push;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import java.util.function.BiFunction;

import org.jabref.logic.util.NotificationService;
import org.jabref.model.entry.BibEntry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

/*
    A test class for JabRef push features related to the pushEntries method
    Creates a list for each application that extends AbstractPushToApplication to ensure each one's functionality for all tests
 */

public class PushEntriesTests {
    private static ArrayList<AbstractPushToApplication> pushToList;

    //required to get priority over MethodSource tests
    @BeforeAll
    static void setUp() {
        pushToList = new ArrayList<>();
        //Instantiate default notifications and preferences
        NotificationService notifService = message -> {};
        PushToApplicationPreferences preferences = PushToApplicationPreferences.getDefault();

        Stream<BiFunction<NotificationService, PushToApplicationPreferences, AbstractPushToApplication>> editorFactories = Stream.of(
                PushToEmacs::new,
                PushToLyx::new,
                PushToSublimeText::new,
                PushToTexmaker::new,
                PushToTexShop::new,
                PushToTeXstudio::new,
                PushToTeXworks::new,
                PushToVim::new,
                PushToVScode::new,
                PushToWinEdt::new
        );

        //instantiate objects with notifications/preferences and add to list
        editorFactories.forEach(factory -> pushToList.add(factory.apply(notifService, preferences)));
    }

    //helper method for MethodSource tests
    //also renames objects to their Class types for better readability in debug
    private static Stream<Arguments> editorProvider() {
        return pushToList.stream()
                         .map(pta -> Arguments.of(Named.of(pta.getClass().getSimpleName(), pta)));
    }

    @ParameterizedTest(name = "Application: {0}")
    @MethodSource("editorProvider")
    @DisplayName("Generic test for pushing single entry.")
    void pushEntriesPushSingleEntry(AbstractPushToApplication pta) {
        List<BibEntry> entries = new ArrayList<BibEntry>();
        entries.add(new BibEntry("Smith2020"));

        assertDoesNotThrow(() -> pta.pushEntries(entries));
    }

    @ParameterizedTest(name = "Application: {0}")
    @MethodSource("editorProvider")
    @DisplayName("Generic test for pushing multiple entries.")
    void pushEntriesPushMultipleEntries(AbstractPushToApplication pta) {
        List<BibEntry> entries = new ArrayList<BibEntry>();
        entries.add(new BibEntry("Smith2020"));
        entries.add(new BibEntry("Lee2021"));

        assertDoesNotThrow(() -> pta.pushEntries(entries));
    }

    @ParameterizedTest(name = "Application: {0}")
    @MethodSource("editorProvider")
    @DisplayName("Generic test for pushing missing citation.")
    void pushEntriesMissingCitation(AbstractPushToApplication pta) {
        List<BibEntry> entries = new ArrayList<BibEntry>();
        entries.add(new BibEntry());

        assertDoesNotThrow(() -> pta.pushEntries(entries));
    }

    @ParameterizedTest(name = "Application: {0}")
    @MethodSource("editorProvider")
    @DisplayName("Generic test for pushing empty entry list.")
    void pushEntriesEmptyEntryList(AbstractPushToApplication pta) {
        List<BibEntry> entries = new ArrayList<BibEntry>();

        assertDoesNotThrow(() -> pta.pushEntries(entries));
    }

    @ParameterizedTest(name = "Application: {0}")
    @MethodSource("editorProvider")
    @DisplayName("Generic test for pushing a large entry set.")
    void pushEntriesLargeEntrySet(AbstractPushToApplication pta) {
        List<BibEntry> entries = new ArrayList<BibEntry>();

        for(int i = 0; i < 100; i++)
        {
            entries.add(new BibEntry("Entry" + i));
        }

        assertDoesNotThrow(() -> pta.pushEntries(entries));
    }

    /*
    @Test
    void pushEntriesUnsupportedApplication() {

    }

    @Test
    void pushEntriesInvalidApplicationPath() {

    }
    */
}
