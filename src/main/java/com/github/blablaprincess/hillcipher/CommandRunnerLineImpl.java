package com.github.blablaprincess.hillcipher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class CommandRunnerLineImpl implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
    }

}
