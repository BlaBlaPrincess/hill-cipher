package com.github.blablaprincess.hillcipher.core.decoder;

import com.github.blablaprincess.hillcipher.rule.HillCipherRule;

public interface HillCipherDecoder {
    String decode(HillCipherRule rule, String message);
}
