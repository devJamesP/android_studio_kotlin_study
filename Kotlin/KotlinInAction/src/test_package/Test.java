package test_package;


import java.util.ArrayList;
import java.util.List;

import static test_package.Chapter1.UNIX_LINE_SEPARATOR;

public class Test {
    public static void main(String[] args) {
        List<Integer> a = new ArrayList<Integer>();
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);

        System.out.println(Chapter1.joinToStringEVen(a));
        System.out.println(UNIX_LINE_SEPARATOR);
    }
}

