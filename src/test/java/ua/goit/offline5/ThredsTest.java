package ua.goit.offline5;

/**
 * Created by andreymi on 3/17/2017.
 */
public class ThredsTest {
    static class A {
        volatile long i;
        Object mutex = new Object();

        long inc() {
            //synchronized (this) {
                return ++i;
            //}
        }
    }

    public static void main(String[] args) {
        A a = new A();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Thread t1 = Thread.currentThread();
                for (int i = 0; i < 10; i++) {
                    long val = a.inc();
                    System.out.println("Hello from :" + t1 + ":" + val);
                }
            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();

        for (int i = 0; i < 10; i++) {
            long val = a.inc();
            Thread t1 = Thread.currentThread();
            System.out.println("Hello from :" + t1 + ":" + val);
        }
    }
}
