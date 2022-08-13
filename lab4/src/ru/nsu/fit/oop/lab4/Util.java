package ru.nsu.fit.oop.lab4;

import java.util.Arrays;

public class Util {

    public static <T> T[] append(T[] array, T lastElement) {
        final int N = array.length;
        array = Arrays.copyOf(array, N + 1);
        array[N] = lastElement;
        return array;
    }

    public static <T> T[] prepend(T[] array, T firstElement) {
        final int N = array.length;
        array = Arrays.copyOf(array, N + 1);
        System.arraycopy(array, 0, array, 1, N);
        array[0] = firstElement;
        return array;
    }

    public static String toUpperFirstChar(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    public static String toLowerFirstChar(String line) {
        return Character.toLowerCase(line.charAt(0)) + line.substring(1);
    }

    public static String eraseWhitespaces(String line) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean toUpperCase = false;
        for (int i = 0; i < line.length(); ++i) {
            char c = line.charAt(i);
            if (!Character.isWhitespace(c)) {
                if (toUpperCase) {
                    stringBuilder.append(Character.toUpperCase(c));
                    toUpperCase = false;
                } else
                    stringBuilder.append(c);
            } else
                toUpperCase = true;
        }
        return new String(stringBuilder);
    }
}
