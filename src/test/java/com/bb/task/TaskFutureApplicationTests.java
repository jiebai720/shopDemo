package com.bb.task;

import com.bb.ShopDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.Future;

/**
 * Created by admin on 2016/12/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ShopDemoApplication.class)
public class TaskFutureApplicationTests {


    @Autowired
    public TaskFuture taskFuture;

    @Test
    public void test() throws Exception {
//        taskFuture.doTaskOne();
//        taskFuture.doTaskTwo();
//        taskFuture.doTaskThree();

        long start = System.currentTimeMillis();

        Future<String> taskOne = taskFuture.doTaskOne();
        Future<String> taskTwo = taskFuture.doTaskTwo();
        Future<String> taskThree = taskFuture.doTaskThree();

        while (true){
            if( taskOne.isDone() && taskTwo.isDone() && taskThree.isDone() ){
                break;
            }
            Thread.sleep(1000);
        }

        long end = System.currentTimeMillis();

        System.out.println("all finished , use:" + (end-start) + " 秒");
    }

}
