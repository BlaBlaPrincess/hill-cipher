package com.github.blablaprincess.hillcipher.core;

import com.github.blablaprincess.hillcipher.rule.HillCipherAlphabet;
import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.RealMatrix;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HillCipherEncryptorCore {

    public static String encrypt(HillCipherAlphabet alphabet, String message, RealMatrix key) {
        int chunkSize = key.getColumnDimension();

        List<String> messageEncryptedChunks = new ArrayList<>();
        for (int chunk = 0; chunk < message.length() / chunkSize; chunk++) {
            messageEncryptedChunks.add(
                    Arrays.stream(MatrixUtils.createRowRealMatrix(
                                                     message.substring(chunk * chunkSize, (chunk + 1) * chunkSize)
                                                            .chars()
                                                            .map(symbol -> {
                                                                if (!alphabet.characterMap.containsKey((char) symbol)) {
                                                                    throw new IllegalArgumentException("Unsupported by alphabet message");
                                                                }
                                                                return alphabet.characterMap.get((char) symbol);
                                                            })
                                                            .asDoubleStream()
                                                            .toArray())
                                             .multiply(key)
                                             .getRow(0))
                          .map(value -> value % alphabet.characterMap.size())
                          .mapToObj(value -> (int) value)
                          .map(alphabet.restorationMap::get)
                          .map(String::valueOf)
                          .collect(Collectors.joining()));
        }
        StringBuilder encryptedMessage = new StringBuilder();
        for (String chunk : messageEncryptedChunks) {
            encryptedMessage.append(chunk);
        }
        return encryptedMessage.toString();
    }

}
