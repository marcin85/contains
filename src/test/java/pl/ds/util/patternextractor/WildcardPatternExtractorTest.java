package pl.ds.util.patternextractor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WildcardPatternExtractorTest {
    static WildcardPatternExtractor patternExtractor = new WildcardPatternExtractor();

    @Test
    @DisplayName("Should extract patterns with wildcards")
    void testExtractSubPatterns() {
        List<String> subPatterns = patternExtractor.extractSubPatterns("abc*def*ghi");

        assertEquals(3, subPatterns.size());
        assertEquals("abc", subPatterns.get(0));
        assertEquals("def", subPatterns.get(1));
        assertEquals("ghi", subPatterns.get(2));
    }

    @Test
    @DisplayName("Should extract patterns with escaped wildcard")
    void testExtractSubPatternsWithEscapedWildcard() {
        List<String> subPatterns = patternExtractor.extractSubPatterns("abc\\*def*ghi");

        assertEquals(2, subPatterns.size());
        assertEquals("abc*def", subPatterns.get(0));
        assertEquals("ghi", subPatterns.get(1));
    }

    @Test
    @DisplayName("Should extract simple pattern")
    void testExtractSubPatternsWithNoWildcard() {
        List<String> subPatterns = patternExtractor.extractSubPatterns("abcdefghi");

        assertEquals(1, subPatterns.size());
        assertEquals("abcdefghi", subPatterns.get(0));
    }

    @Test
    @DisplayName("Should extract pattern with escaped backslash")
    void testExtractSubPatternsWithEscapedBackslash() {
        List<String> subPatterns = patternExtractor.extractSubPatterns("abc\\\\def");

        assertEquals(1, subPatterns.size());
        assertEquals("abc\\\\def", subPatterns.get(0));
    }
}
