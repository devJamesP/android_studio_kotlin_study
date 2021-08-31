package javacontents;

public class CompareExam {
    public static void exec(Compare comapre) {
        int k = 10;
        int m = 20;
        int value = comapre.compareTo(k, m);
        System.out.println(value);
    }

    public static void main(String[] args) {
        exec((i, j) -> i - j);
    }
}
