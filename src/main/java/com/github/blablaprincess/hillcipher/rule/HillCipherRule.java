package com.github.blablaprincess.hillcipher.rule;

import com.github.blablaprincess.hillcipher.commons.BezoutRatio;
import lombok.ToString;
import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.RealMatrix;

@ToString
public class HillCipherRule {

    public final HillCipherAlphabet alphabet;
    public final RealMatrix key;

    public HillCipherRule(HillCipherAlphabet alphabet, String key) {
        this.alphabet = alphabet;

        int chunkSize = (int) Math.sqrt(key.length());
        if (chunkSize * chunkSize != key.length()) {
            throw new IllegalArgumentException("Unsupported key size");
        }
        double[][] keyMatrixData = new double[chunkSize][chunkSize];
        for (int chunk = 0; chunk < chunkSize; chunk++) {
            keyMatrixData[chunk] = key.substring(chunk * chunkSize, (chunk + 1) * chunkSize)
                                      .chars()
                                      .map(symbol -> alphabet.characterMap.get((char) symbol))
                                      .asDoubleStream()
                                      .toArray();
        }
        this.key = MatrixUtils.createRealMatrix(keyMatrixData);
        if (new LUDecompositionImpl(this.key).getDeterminant() == 0) {
            throw new IllegalArgumentException("Unsupported key");
        }

        int gcd = BezoutRatio.getInstance(alphabet.characterMap.size(), key.length()).gcd;
        if (gcd != 1) {
            throw new IllegalArgumentException(
                    String.format("Alphabet size (%s) and key length (%s) must be coprime numbers, but actual gcd is %s",
                                  alphabet.characterMap.size(), key.length(), gcd));
        }
    }

}
