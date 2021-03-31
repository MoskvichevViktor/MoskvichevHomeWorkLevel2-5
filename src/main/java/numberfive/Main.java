package numberfive;

public class Main {
    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    //заполнение массива элементами = 1
    public float[] elementsone(float[] arr) {
        for (int i = 0; i < arr.length; i++)
            arr[i] = 1.0f;
        return arr;
    }

    //заполнение массива элементами по формуле
    public float[] elementsformula(float[] arr) {
        for (int i = 0; i < arr.length; i++)
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f +
                    i / 5) * Math.cos(0.4f + i / 2));
        return arr;
    }

    public void metodone() {
        float[] arr = new float[SIZE];
        elementsone(arr);
        long a = System.currentTimeMillis();
        elementsformula(arr);
        System.out.println("Замер времени работы из первого метода: " + (System.currentTimeMillis() - a));
    }

    public void metodtwo() {
        float[] arr = new float[SIZE];
        final float[] arr1 = new float[HALF];
        final float[] arr2 = new float[HALF];
        elementsone(arr);
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, HALF);
        System.arraycopy(arr, HALF, arr2, 0, HALF);

        new Thread() {
            public void run() {
                float[] a1 = elementsformula(arr1);
                System.arraycopy(a1, 0, arr1, 0, a1.length);
            }
        }.start();

        new Thread() {
            public void run() {
                float[] a2 = elementsformula(arr2);
                System.arraycopy(a2, 0, arr2, 0, a2.length);
            }
        }.start();

        System.arraycopy(arr1, 0, arr, 0, HALF);
        System.arraycopy(arr2, 0, arr, HALF, HALF);
        System.out.println("Замер времени работы из второго метода: " + (System.currentTimeMillis() - a));
    }

    public static void main(String  args[]) {
        Main test = new Main();
        test.metodone();
        test.metodtwo();
    }
}