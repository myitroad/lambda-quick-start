package com.example.lambda.example01;

/**
 * Created by liutingna on 2017/9/9.
 */
public class RunnableTest {
    public static void main(String[] args) {
        System.out.println("=== RunnableTest ===");
        // Anonymous Runnable
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world one!");
            }
        };
        // Lambda Runnable
        Runnable r2 = () -> System.out.println("Hello world two!");
        // Run em!
        r1.run();
        r2.run();
    }
}