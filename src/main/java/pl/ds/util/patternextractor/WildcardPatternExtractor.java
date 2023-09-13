package pl.ds.util.patternextractor;

import java.util.ArrayList;
import java.util.List;

public class WildcardPatternExtractor implements PatternExtractor {
    public static final char WILDCARD = '*';
    public static final char BACKSLASH = '\\';

    public List<String> extractSubPatterns(String pattern) {
        List<String> subPatterns = new ArrayList<>();
        StringBuilder currentSubPattern = new StringBuilder();

        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == WILDCARD && (i == 0 || pattern.charAt(i - 1) != BACKSLASH)) {
                if (currentSubPattern.length() > 0) {
                    subPatterns.add(currentSubPattern.toString());
                    currentSubPattern.setLength(0);
                }
            } else {
                if (i > 0 && pattern.charAt(i - 1) == BACKSLASH && pattern.charAt(i) == WILDCARD) {
                    currentSubPattern.deleteCharAt(currentSubPattern.length() - 1);
                }
                currentSubPattern.append(pattern.charAt(i));
            }
        }

        if (currentSubPattern.length() > 0) {
            subPatterns.add(currentSubPattern.toString());
        }
        return subPatterns;
    }
}
