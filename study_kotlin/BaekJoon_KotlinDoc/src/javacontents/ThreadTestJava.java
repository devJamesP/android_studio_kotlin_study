package javacontents;

public class ThreadTestJava {
    public static void main(String[] args) {
//        MyThread myThread = new MyThread("*");
//        MyThread myThread2 = new MyThread("-");
//
//        myThread.start();
//        myThread2.start();

        MyRunnable myRunnable1 = new MyRunnable("*");
        MyRunnable myRunnable2 = new MyRunnable("-");

        Thread t1 = new Thread(myRunnable1);
        Thread t2 = new Thread(myRunnable2);

        t1.start();
        t2.start();

    }
}
