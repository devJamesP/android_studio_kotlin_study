package javacontents;

import java.lang.reflect.Method;

public class AnnotationExam {
    public static void main(String[] args) {
//        MyHello hello = new MyHello();
//        try {
//            Method method = hello.getClass().getDeclaredMethod("hello");
//            // 해당 메서드에 대한 정보가 Count100이라는 어노테이션이 적용되어 있는지 ? true : false
//            if (method.isAnnotationPresent(Count100.class)){
//                for(int i = 0; i < 100; i++) {
//                    hello.hello();
//                }
//            } else {
//                hello.hello();
//            }
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
        AnnotationExam w = new AnnotationExam();
        try {
            Method method = w.getClass().getDeclaredMethod("test1");
            if (method.isAnnotationPresent(RunTwice.class)) {
                w.test1();w.test1();
            }

            Method method2 = w.getClass().getDeclaredMethod("test2");
            if (method2.isAnnotationPresent(RunTwice.class)) {
                w.test2();w.test2();
            }


            Method method3 = w.getClass().getDeclaredMethod("test3");
            if (method3.isAnnotationPresent(RunTwice.class)) {
                w.test3();w.test3();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void test1() {
        System.out.println("test1");
    }
    @RunTwice
    private void test2() {
        System.out.println("test2");
    }
    private void test3() {
        System.out.println("test3");
    }
}
