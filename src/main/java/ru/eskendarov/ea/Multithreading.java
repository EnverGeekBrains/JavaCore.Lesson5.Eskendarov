package ru.eskendarov.ea;

import lombok.SneakyThrows;

public class Multithreading extends Thread {
    static final int size = 10000000;
    static final int h = size / 2;

    public synchronized void simpleСalculationArray() {

        float[] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1.0f;
        }
        long a = System.currentTimeMillis();
        calcArray(arr);
        System.out.println("Simple calculation array execution time: " + (System.currentTimeMillis() - a));
    }

    @SneakyThrows
    public synchronized void complexСalculationArray() {

        final float[] arr = new float[size];
        final float[] a1 = new float[h];
        final float[] a2 = new float[h];

        for (int i = 0; i < size; i++) {
            arr[i] = 1.0f;
        }
        long a = System.currentTimeMillis();

//      деление массива arr на два: a1, a2
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

//      создадим два параллельных потока для разделенных массивов
        Thread multithreading1 = new Thread(() -> firstComplexCalculate(a1));
        Thread multithreading2 = new Thread(() -> secondaryComplexCalculate(a2));
        multithreading1.start();
        multithreading2.start();

//      подождем пока высчитывается медленный поток, для высчитывания времени родительского потока
        multithreading1.join();
        multithreading2.join();

//      обратная склейка массива arr из a1 и a2
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        System.out.println("Complex calculation array execution time in total: " + (System.currentTimeMillis() - a));
    }

    private void firstComplexCalculate(float[] arr) {
        long a = System.currentTimeMillis();
        float[] a1 = calcArray(arr);
        System.out.println("Time by first complex: " + (System.currentTimeMillis() - a));
    }

    private void secondaryComplexCalculate(float[] arr) {
        long a = System.currentTimeMillis();
        float[] a2 = calcArray(arr);
        System.out.println("Time by secondary complex: " + (System.currentTimeMillis() - a));
    }

    private float[] calcArray(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        return arr;
    }
}
