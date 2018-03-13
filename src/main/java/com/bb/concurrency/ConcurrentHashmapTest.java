package com.bb.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by admin on 2017/5/25.
 */
public class ConcurrentHashmapTest {



    public static void main(String[] args) {

//        System.out.println( "aaaa ");

        Map map  = new HashMap();
        Thread t = new Thread( new Runnable(){

            @Override
            public void run() {

                for(int i=0 ; i< 5000; i++){
                    new Thread( new Runnable() {

                        @Override
                        public void run() {
                            map.put( UUID.randomUUID().toString() , "" );
                        }
                    }).start();
                }

            }
        });

        t.start();

    }


}
