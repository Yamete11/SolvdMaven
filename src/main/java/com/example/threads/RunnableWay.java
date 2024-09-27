package com.example.threads;

public class RunnableWay implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - Starting RunnableWay execution");
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " - RunnableWay - Working " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " - RunnableWay execution completed");
    }
}
