package javacontents;

public class DeamonThread implements Runnable {
    @Override
    public void run() {
        while(true) {
            System.out.println("데몬 쓰레드가 실행중입니다.");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new DeamonThread());

        thread.setDaemon(true);
        thread.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End the Main Thread");

    }


}
