package ru.eskendarov.ea;

public class Multithreading extends Thread {
    static final int size = 10000000;
    static final int h = size / 2;


    public synchronized void method1() {

        float[] arr = new float[size];

        for (int i = 0; i < size; i++) {
            arr[i] = 1.0f;
        }
        long a = System.currentTimeMillis();
        calcArray(arr);
        System.out.println("Method 1 execution time: " + (System.currentTimeMillis() - a));
    }


    public synchronized void method2() {

        float[] arr = new float[size];
        float[] a1 = new float[h];
        float[] a2 = new float[h];

        for (int i = 0; i < size; i++) {
            arr[i] = 1.0f;
        }
        long a = System.currentTimeMillis();

//      деление массива arr на два: a1, a2
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        new Thread(() -> methodMiniArr1(a1)).start();
        new Thread(() -> methodMiniArr2(a2)).start();

//      обратная склейка массива arr из a1 и a2
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        System.out.println("Method 2 execution time: " + (System.currentTimeMillis() - a));
    }

    public void methodMiniArr1(float[] arr) {
        long a = System.currentTimeMillis();
        float[] a1 = calcArray(arr);
        System.out.println("methodMiniArr 1 execution time: " + (System.currentTimeMillis() - a));

    }

    public void methodMiniArr2(float[] arr) {
        long a = System.currentTimeMillis();
        float[] a2 = calcArray(arr);
        System.out.println("methodMiniArr 2 execution time: " + (System.currentTimeMillis() - a));
    }

    public float[] calcArray(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        return arr;
    }
}