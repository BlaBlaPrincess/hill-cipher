package com.github.blablaprincess.hillcipher.core;

import com.github.blablaprincess.hillcipher.commons.Alphabets;
import com.github.blablaprincess.hillcipher.core.decoder.HillCipherDecoderImpl;
import com.github.blablaprincess.hillcipher.core.encryptor.HillCipherEncryptorImpl;
import com.github.blablaprincess.hillcipher.rule.HillCipherAlphabet;
import com.github.blablaprincess.hillcipher.rule.HillCipherRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static com.github.blablaprincess.hillcipher.commons.Alphabets.lowercaseLatin;
import static com.github.blablaprincess.hillcipher.commons.Alphabets.uppercaseLatin;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class HillCipherEncryptorCoreIT {

    private final HillCipherEncryptorImpl encryptor = new HillCipherEncryptorImpl();
    private final HillCipherDecoderImpl decoder = new HillCipherDecoderImpl();

    @DisplayName("encrypt -> decode")
    @ParameterizedTest(name = "[{index}]")
    @MethodSource("getTestCases")
    void test(HillCipherRule rule, String message, String expected) {
        // Act
        String encrypted = encryptor.encrypt(rule, message);
        String decoded = decoder.decode(rule, encrypted);

        // Assert
        assertEquals(expected, encrypted);
        assertEquals(message, decoded);
    }

    @SuppressWarnings("SpellCheckingInspection")
    private static Stream<Arguments> getTestCases() {
        Set<Character> additionalSymbols = Alphabets.getAlphabet(".,");
        Set<Character> extendedLowercaseLatin = new HashSet<>(lowercaseLatin);
        Set<Character> extendedUppercaseLatin = new HashSet<>(uppercaseLatin);
        extendedLowercaseLatin.addAll(additionalSymbols);
        extendedUppercaseLatin.addAll(additionalSymbols);
        return Stream.of(
                arguments(rule(extendedLowercaseLatin, "extension"), "movement", "kqomzvore"),
                arguments(rule(lowercaseLatin, "extraterrestrial"),  "update",   "xbieppik"),
                arguments(rule(extendedUppercaseLatin, "FORMATION"), "COMMAND",  "NWVTCZY_T")
                        );
    }

    private static HillCipherRule rule(Set<Character> alphabet, String key) {
        return new HillCipherRule(new HillCipherAlphabet(alphabet, '_'), key);
    }

}