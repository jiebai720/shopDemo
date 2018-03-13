package com.bb.concurrency;

/**
 * Created by admin on 2017/5/24.
 */


import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ATest {


    public static void main(String[] args) throws InterruptedException {


        final Lock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();

        Thread thread = new Thread( (Runnable)() -> {
            lock.lock();
            try{
//                System.out.println( "i am wait" + this );
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println();
        } , "waitThread" );

    }

}

