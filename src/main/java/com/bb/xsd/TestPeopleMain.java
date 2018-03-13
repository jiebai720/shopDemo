package com.bb.xsd;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by admin on 2018/3/13.
 */
public class TestPeopleMain {



    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("myxsd.xml");
        People p = (People)ctx.getBean("cutesource");
        System.out.println(p.getId());
        System.out.println(p.getName());
        System.out.println(p.getAge());
    }

}
