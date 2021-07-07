package javacontents;

public class JoinExam {
    public static void main(String[] args) {
        MyThread5 thread = new MyThread5();
        // Thread시작
        thread.start();
        System.out.println("Thread 끝날때까지 대기");
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("Thread 종료");
    }
}
