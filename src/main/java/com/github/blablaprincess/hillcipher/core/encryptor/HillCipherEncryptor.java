package com.github.blablaprincess.hillcipher.core.encryptor;

import com.github.blablaprincess.hillcipher.rule.HillCipherRule;

public interface HillCipherEncryptor {
    String encrypt(HillCipherRule rule, String message);
}
