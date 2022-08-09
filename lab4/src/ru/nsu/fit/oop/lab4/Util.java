package ru.nsu.fit.oop.lab4;

import java.util.Arrays;

public class Util {

    public static <T> T[] append(T[] array, T lastElement) {
        final int N = array.length;
        array = Arrays.copyOf(array,N + 1);
        array[N] = lastElement;
        return array;
    }

    public static <T> T[] prepend(T[] array, T firstElement) {
        final int N = array.length;
        array = Arrays.copyOf(array, N + 1);
        System.arraycopy(array,0,array,1,N);
        array[0] = firstElement;
        return array;
    }
}
