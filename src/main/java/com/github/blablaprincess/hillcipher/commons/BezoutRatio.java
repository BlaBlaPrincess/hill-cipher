package com.github.blablaprincess.hillcipher.commons;

public class BezoutRatio {

    public final int gcd;
    public final int x;
    public final int y;

    private BezoutRatio(int gcd, int x, int y) {
        this.gcd = gcd;
        this.x = x;
        this.y = y;
    }

    public static BezoutRatio getInstance(int a, int b) {
        int[] ratio = getBezoutRatio(a, b);
        return new BezoutRatio(ratio[0], ratio[1], ratio[2]);
    }

    private static int[] getBezoutRatio(int a, int b) {
        if (b == 0) {
            return new int[] { a, 1, 0 };
        }

        int[] values = getBezoutRatio(b, a % b);
        int gcd = values[0];
        int x = values[2];
        int y = values[1] - (a / b) * values[2];
        return new int[] { gcd, x, y };
    }

}
