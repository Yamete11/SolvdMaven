package com.example.threads;

public class ThreadWay extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - Starting ThreadWay execution");
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " - Working " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " - ThreadWay execution completed");
    }
}
