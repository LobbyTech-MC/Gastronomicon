package io.github.schntgaispock.gastronomicon.util;

import java.util.concurrent.ThreadLocalRandom;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NumberUtil {

    private static @Getter ThreadLocalRandom random = ThreadLocalRandom.current();

    public static int clamp(int x, int lowerBound, int upperBound) {
        return Math.min(Math.max(x, lowerBound), upperBound);
    }

    public static double clamp(double x, double lowerBound, double upperBound) {
        return Math.min(Math.max(x, lowerBound), upperBound);
    }

    public static String asRomanNumeral(int x) {
        if (x >= 4000 || x <= 0)
            return Integer.toString(x);
        String[] thousands = { "", "M", "MM", "MMM" };
        String[] hundreds = { "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" };
        String[] tens = { "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" };
        String[] ones = { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" };
        return thousands[x / 1000] + hundreds[(x / 100) % 10] + tens[(x / 10) % 10] + ones[x % 10];
    }

    public static boolean flip(double chance) {
        return random.nextDouble(1) < chance;
    }

    public static int randomRound(double x) {
        final int f = (int) Math.floor(x);
        final int c = (int) Math.ceil(x);
        if (f == c)
            return f;

        return flip(x - f) ? f : c;
    }

    public static double roundToPrecision(double x, int precision) {
        final double magn = Math.pow(10, precision);
        return Math.round(x * magn) / magn;
    }

    public static double roundToPercent(double x, int precision) {
        return roundToPrecision(x * 100, precision);
    }

    public static int getFortuneAmount(int fortuneLevel, int sickleTier, int baseAmount) {
        return getFortuneAmount(fortuneLevel + sickleTier, baseAmount);
    }

    public static int getFortuneAmount(int fortuneLevel, int baseAmount) {
        return baseAmount + ThreadLocalRandom.current().nextInt(fortuneLevel, 1 + (int) Math.ceil(fortuneLevel * 1.5));
    }

	public static ThreadLocalRandom getRandom() {
		// TODO Auto-generated method stub
		return random;
	}

}
