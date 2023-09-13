package pl.ds.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


public class StringUtilTest {
    @DisplayName("Testing StringUtil.contains")
    @ParameterizedTest(name = "StringUtils.contains({0}, {1}) should return {2}")
    @MethodSource("stringAndPatternProvider")
    void testContains(String input, String pattern, boolean expectedResult) {
        boolean result = StringUtil.contains(input, pattern);
        assertThat(result).isEqualTo(expectedResult);
    }

    public static Stream<Arguments> stringAndPatternProvider() {
        return Stream.of(
                Arguments.of("abc", "", true),
                Arguments.of("abc", "*", true),
                Arguments.of(null, "*", false),
                Arguments.of("*", null, false),
                Arguments.of("", "", true),
                Arguments.of("abc", "", true),
                Arguments.of("abc", "a", true),
                Arguments.of("abc", "a*", true),
                Arguments.of("abc", "a**", true),
                Arguments.of("abc", "a*c", true),
                Arguments.of("a", "a*", true),
                Arguments.of("a*", "a\\*", true),
                Arguments.of("a*c", "a\\*", true),
                Arguments.of("a*c", "abc", false),
                Arguments.of("abc", "A", false),
                Arguments.of("abc", "abcd", false),
                Arguments.of("aaab", "aab", true),
                Arguments.of("aab", "Aab", false),
                Arguments.of("a\\bc", "a\\bc", true),
                Arguments.of("abcd", "a*d", true),
                Arguments.of("a*cd", "a\\*cd", true),
                Arguments.of("abc", "A", false),
                Arguments.of("", "*", true),
                Arguments.of("abcd", "**a**b****c***d***", true),
                Arguments.of("abcd", "*a*b*c*d*", true),
                Arguments.of("\\", "\\\\", false),
                Arguments.of("***", "*\\**\\*\\*", true),
                Arguments.of("abcd*abcde", "a*\\*abcd", true),
                Arguments.of("**", "\\*\\*\\*", false),
                Arguments.of("***", "\\*\\*\\*", true),
                Arguments.of("***", "*\\*\\*\\**", true),
                Arguments.of("abcd---ababcd", "abcd*abcd", true),
                Arguments.of("\\", "\\", true),
                Arguments.of("*", "\\", false)
        );
    }
}