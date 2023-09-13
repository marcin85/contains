package pl.ds.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.ds.util.patternextractor.PatternExtractor;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatternRecognizerTest {

    private final PatternExtractor emptyPatternExtractor = (pattern) -> Collections.emptyList();

    @Test
    @DisplayName("Initialize with valid pattern should not throw any exception")
    void validStringTest() {
        assertDoesNotThrow(() -> new PatternRecognizer(emptyPatternExtractor, "validPattern"));
    }

    @Test
    @DisplayName("Initialize with null pattern should not throw any exception")
    void nullStringTest() {
        assertDoesNotThrow(() -> new PatternRecognizer(emptyPatternExtractor, null));
    }

    @Test
    @DisplayName("Should recognize basic pattern")
    void testProcessingCharactersWithSimplePattern() {
        PatternExtractor patternExtractor = (pattern) -> Collections.singletonList("abc");
        PatternRecognizer patternRecognizer = new PatternRecognizer(patternExtractor, "abc");

        assertTrue(patternRecognizer.findPattern("abc"));
    }

    @Test
    @DisplayName("Should recognize sub patterns")
    void testProcessingCharactersWithSubPatterns() {
        PatternExtractor patternExtractor = (pattern) -> Arrays.asList("ab", "cd");
        PatternRecognizer patternRecognizer = new PatternRecognizer(patternExtractor, "ab*cd");

        assertTrue(patternRecognizer.findPattern("abXcd"));
    }

    @Test
    @DisplayName("Should recognize pattern with escaped wildcards")
    void testProcessingCharactersWithWildcards() {
        PatternExtractor patternExtractor = (pattern) -> Collections.singletonList("ab*cd");
        PatternRecognizer patternRecognizer = new PatternRecognizer(patternExtractor, "ab\\*cd");

        assertTrue(patternRecognizer.findPattern("ab*cd"));
    }

    @Test
    @DisplayName("Should recognize pattern with escaped backslash")
    void testProcessingCharactersWithEscapedBackslash() {
        PatternExtractor patternExtractor = (pattern) -> Collections.singletonList("ab\\cd");
        PatternRecognizer patternRecognizer = new PatternRecognizer(patternExtractor, "ab\\\\cd");

        assertTrue(patternRecognizer.findPattern("ab\\cd"));
    }
}