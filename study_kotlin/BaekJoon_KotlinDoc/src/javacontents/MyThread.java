package javacontents;

public class MyThread extends Thread {
    String str;
    public MyThread(String _str) {
        this.str = _str;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            System.out.println(str);
            try {
                Thread.sleep((long) (Math.random() * 1_000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
