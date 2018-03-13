package com.bb.concurrency;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/12/21.
 * @author  baijie
 */
public class SimpleThreadLocal<T> {


    private Map<Object , T> valueMap = Collections.synchronizedMap( new HashMap());

    public void set( T newValue ){
        valueMap.put( Thread.currentThread() , newValue );
    }

    public T get(){

        Thread currentThread = Thread.currentThread() ;
        T object = valueMap.get( currentThread );

        if( object == null && (!valueMap.containsKey(currentThread)) ){
            object = initialValue();
            valueMap.put( currentThread , object) ;
        }

        return object ;
    }

    public void remove(){
        valueMap.remove( Thread.currentThread() );
    }

    public T initialValue(){
        return null ;
    }

}
