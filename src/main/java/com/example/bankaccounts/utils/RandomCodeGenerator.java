package com.example.bankaccounts.utils;

import java.util.Random;

public class RandomCodeGenerator {
    private static Random random = new Random();
    public static int generateCode(){
        return 1000 + random.nextInt(9000);
    }
}
