package com.bb.core.spring;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Date;

/**
 * Created by admin on 2017/5/23.
 */
@Configuration
public class AnotateSpringTest {


//    public static void main(String[] args) {
//
//
//        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
//        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(factory);
//        reader.register(Test.class);
//        System.out.println(factory.getBean("date"));
//
//    }
//
//        public class Test {

//        @Bean
//        public Date date() { return new Date(); }
//
//        public static void main(String[] args) throws ClassNotFoundException, IOException {
//
//            DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
//            AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(factory);
//            reader.register( AnotateSpringTest.class );
//            System.out.println(factory.getBean("date"));
//
//            //出现异常
//        }

//    }



}
