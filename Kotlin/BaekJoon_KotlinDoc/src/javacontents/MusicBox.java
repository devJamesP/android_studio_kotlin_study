package javacontents;

import java.util.concurrent.locks.ReentrantLock;

public class MusicBox {
    ReentrantLock reentrantLock = new ReentrantLock();

    public void playMusic1() {

        for (int i = 0; i < 10; i++) {
            synchronized (this) {
                System.out.println("신나는음악");
                try {
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public synchronized void playMusic2() {

        for (int i = 0; i < 10; i++) {
            System.out.println("슬픈음악");
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public synchronized void playMusic3() {

        for (int i = 0; i < 10; i++) {
            System.out.println("조용한음악");
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

