package com.github.blablaprincess.hillcipher.commons;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("SpellCheckingInspection")
public class Alphabets {

    public static final Set<Character> numbers = getAlphabet("0123456789");
    public static final Set<Character> lowercaseLatin = getAlphabet("abcdefghijklmnopqrstuvwxyz");
    public static final Set<Character> uppercaseLatin = getAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

    public static Set<Character> getAlphabet(String symbols) {
        return symbols.chars()
                      .mapToObj(value -> (char) value)
                      .collect(Collectors.toSet());
    }

}
