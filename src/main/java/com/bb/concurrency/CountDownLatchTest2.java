package com.bb.concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * Created by admin on 2017/5/25.
 */
public class CountDownLatchTest2 {



    public static void main(String[] args) throws InterruptedException {

        CountDownLatch c = new CountDownLatch(2);

        Thread parser = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println( "parser1  finish");
                c.countDown();
                System.out.println( "parser2  finish");
                c.countDown();
            }
        });

        parser.start();
        c.await();
        System.out.println(  "all finish");
    }

}
