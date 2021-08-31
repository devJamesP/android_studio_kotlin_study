package javacontents;

public class ThreadB extends Thread {
    int total = 0;

    public void run() {
        synchronized (this) {
            for(int i = 0; i < 5; i++) {
                System.out.println(i+"를더합니다.");
                total += i;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notify();
        }
    }
}
