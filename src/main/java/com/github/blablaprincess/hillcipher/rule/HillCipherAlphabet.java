package com.github.blablaprincess.hillcipher.rule;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
public class HillCipherAlphabet {

    public final Map<Character, Integer> characterMap;
    public final Map<Integer, Character> restorationMap;
    public final int filler;

    public HillCipherAlphabet(Set<Character> characters) {
        this(characters, 999);
    }

    public HillCipherAlphabet(Set<Character> characters, int filler) {
        this.filler = filler;
        characterMap = new HashMap<>();
        int symbolCode = 0;
        for (Character symbol : characters) {
            if (symbol == filler) {
                throw new IllegalArgumentException("Unsupported alphabet");
            }
            characterMap.put(symbol, symbolCode);
            symbolCode++;
        }
        characterMap.put((char) filler, symbolCode);

        restorationMap = invert(characterMap);
    }

    private static <V, K> Map<V, K> invert(Map<K, V> map) {
        return map.entrySet()
                  .stream()
                  .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

}
