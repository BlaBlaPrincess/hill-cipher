package com.github.blablaprincess.hillcipher.core.decoder;

import com.github.blablaprincess.hillcipher.commons.BezoutRatio;
import com.github.blablaprincess.hillcipher.core.HillCipherEncryptorCore;
import com.github.blablaprincess.hillcipher.rule.HillCipherRule;
import org.apache.commons.math.linear.LUDecomposition;
import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.RealMatrix;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class HillCipherDecoderImpl implements HillCipherDecoder {

    @Override
    public String decode(HillCipherRule rule, String message) {
        int alphabetSize = rule.alphabet.characterMap.size();

        LUDecomposition decomposition = new LUDecompositionImpl(rule.key);
        int determinant = (int) Math.round(decomposition.getDeterminant());

        int bezoutRatioX = BezoutRatio.getInstance(determinant, alphabetSize).x;
        int invertedDeterminant;
        if (bezoutRatioX > 0) {
            invertedDeterminant = bezoutRatioX;
        } else {
            if (determinant > 0) {
                invertedDeterminant = alphabetSize + bezoutRatioX;
            } else {
                invertedDeterminant = -bezoutRatioX;
            }
        }

        RealMatrix invertedKey = decomposition.getSolver().getInverse();
        invertedKey = invertedKey.scalarMultiply(determinant * invertedDeterminant);

        for (int i = 0; i < invertedKey.getRowDimension(); i++) {
            double[] row = invertedKey.getRow(i);
            row = Arrays.stream(row).map(value -> {
                value = Math.round(value) % alphabetSize;
                if (value < 0) {
                    value += alphabetSize;
                }
                return value;
            }).toArray();
            invertedKey.setRow(i, row);
        }

        return HillCipherEncryptorCore.encrypt(rule.alphabet, message, invertedKey)
                                      .replace(String.valueOf((char) rule.alphabet.filler), "");
    }

}
