package javacontents;

public class MyRunnable implements Runnable {
    private String str;
    public MyRunnable(String str) {
        this.str = str;
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(str);
            try {
                Thread.sleep((long) (Math.random() * 1_000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
