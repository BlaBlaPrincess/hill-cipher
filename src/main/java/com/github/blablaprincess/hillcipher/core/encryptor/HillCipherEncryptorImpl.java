package com.github.blablaprincess.hillcipher.core.encryptor;

import com.github.blablaprincess.hillcipher.core.HillCipherEncryptorCore;
import com.github.blablaprincess.hillcipher.rule.HillCipherRule;
import org.springframework.stereotype.Component;

@Component
public class HillCipherEncryptorImpl implements HillCipherEncryptor {

    @Override
    public String encrypt(HillCipherRule rule, String message) {
        int chunkSize = rule.key.getColumnDimension();

        if (message.indexOf(rule.alphabet.filler) != -1) {
            throw new IllegalArgumentException("Message contains filler symbol");
        }

        int filledMessageLength = message.length() % chunkSize == 0 ? message.length() :
                                  message.length() + (chunkSize - message.length() % chunkSize);
        String filledMessage = message + new String(new char[filledMessageLength - message.length()])
                .replace('\0', (char) rule.alphabet.filler);

        return HillCipherEncryptorCore.encrypt(rule.alphabet, filledMessage, rule.key);
    }

}
