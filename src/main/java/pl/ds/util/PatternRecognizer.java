package pl.ds.util;

import pl.ds.util.patternextractor.PatternExtractor;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Iterator;
import java.util.List;

import static java.util.Objects.isNull;

class PatternRecognizer {
    private boolean allSubPatternsFound = false;
    private boolean finished = false;

    private Iterator<StringCharacterIterator> subPatternIterators;
    private StringCharacterIterator currentSubPatternIterator;
    private StringCharacterIterator stringIterator;


    public PatternRecognizer(PatternExtractor patternExtractor, String pattern) {
        if (isNull(pattern)) {
            finished = true;
            return;
        }

        List<String> subPatterns = patternExtractor.extractSubPatterns(pattern);
        if (subPatterns.isEmpty()) {
            allSubPatternsFound = true;
            finished = true;
        } else {
            initialize(subPatterns);
        }
    }

    public boolean findPattern(String string) {
        if (isNull(string)) {
            return false;
        }

        stringIterator = new StringCharacterIterator(string);
        do {
            char stringChar = stringIterator.current();
            processCharacter(stringChar);
        } while (isNotFinished(stringIterator) && !isPatternFound());

        return isPatternFound();
    }

    public boolean isPatternFound() {
        return allSubPatternsFound;
    }

    private void processCharacter(char character) {
        if (isProcessingComplete()) {
            return;
        }

        if (isCharacterMatchingSubPattern(character)) {
            if (isLastCharacterInCurrentSubPattern()) {
                proceedToNextSubPatternOrComplete();
            } else {
                advanceToNextCharacterInSubPattern();
            }
        } else {
            resetPatternAndStringIndex();
        }
    }

    private boolean isNotFinished(StringCharacterIterator stringIterator) {
        return stringIterator.next() != CharacterIterator.DONE;
    }

    private boolean isProcessingComplete() {
        return finished;
    }

    private void resetPatternAndStringIndex() {
        int subPatternIndex = currentSubPatternIterator.getIndex();
        stringIterator.setIndex(stringIterator.getIndex() - subPatternIndex);
        currentSubPatternIterator.first();
    }

    private void advanceToNextCharacterInSubPattern() {
        currentSubPatternIterator.next();
    }

    private boolean isCharacterMatchingSubPattern(char character) {
        return currentSubPatternIterator.current() == character;
    }

    private void proceedToNextSubPatternOrComplete() {
        if (subPatternIterators.hasNext()) {
            currentSubPatternIterator = subPatternIterators.next();
        } else {
            allSubPatternsFound = true;
            finished = true;
        }
    }

    private void initialize(List<String> patterns) {
        subPatternIterators = patterns.stream()
                .map(StringCharacterIterator::new)
                .iterator();
        currentSubPatternIterator = subPatternIterators.next();
    }

    private boolean isLastCharacterInCurrentSubPattern() {
        return currentSubPatternIterator.getIndex() + 1 == currentSubPatternIterator.getEndIndex();
    }
}