package com.bb.core.spring;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by admin on 2017/5/23.
 */
public class SpringTest {


    public static void main(String[] args) {

//        int value = 37423 ;
//        Random random = new Random() ;
//        List<Integer> list = new ArrayList<Integer>();
//
//        for (int i = 1; i < 16; i++) {
////            System.out.println( (i/5)  + "==" + i );
//            int b = (i/5) + 1 ;
//            int seed = 20000;
//            seed = seed * b ;
//            int a = value + random.nextInt(seed) ;
//
//            list.add(a);
////            System.out.println( a );
//        }
//        Collections.sort(list);
//
//        for ( Integer a1:  list ) {
//            System.out.println( a1 );
//        }

//        System.out.println( " security manager====" + System.getSecurityManager());

//        String path = "classpath:com/bb/core/spring/beans.xml";

//        springTest.test( "a1" );
//        springTest.test( "a2" , "123" , "test" );
//        UserService userService = (UserService) springTest.createBean();
//        System.out.println( "== my ===" );
//        userService.selectUser() ;
//
//        springTest.testSpringBeanFactory();

        SpringTest springTest = new SpringTest();
        springTest.gen();
    }


    private void test(String abc , String... ignoreColumns ){
        System.out.println( ignoreColumns.length + "==abc==" +   abc );
        String columnName = "test" ;
        if( ignoreColumns != null && ignoreColumns.length > 0 ){
            for( String ignoreColumn : ignoreColumns ){
                System.out.println( "ignoreColumnignoreColumn====" + ignoreColumn );
                if( ignoreColumn.equals(columnName ) ){
                    continue;
                }
            }
        }
    }

    private void gen(){

        System.out.println( "gogogogogoo  ApplicationContext =====");
        String[] path = { "beans.xml" , "beans-tx.xml" };

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext( path );
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.selectUser();
    }


    private void testSpringBeanFactory(){

//        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
//        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
//        reader.loadBeanDefinitions("app.xml");
//        System.out.println(factory.getBean("date"));

        String path = "beans.xml";
        BeanFactory beanFactory  = new XmlBeanFactory( new ClassPathResource(path) ) ;
        UserService userService = (UserService) beanFactory.getBean("userService");

        userService.selectUser();

//        InputStream is = new FileInputStream("beans.xml");
//        XmlBeanFactory factory = new XmlBeanFactory(is);
//
//        String[] file = new String[1] ;
//        file[0] = "applicationContext.xml" ;
//
//        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext( file );
//// of course, an ApplicationContext is just a BeanFactory
//        BeanFactory factory = (BeanFactory) appContext ;

    }


    private Object createBean(){

        Constructor<?> constructorToUse = null ;
        final Class<?> clazz = com.bb.core.spring.UserService.class;
        if (clazz.isInterface()) {
            throw new BeanInstantiationException(clazz, "Specified class is an interface");
        }
        try {
            if (System.getSecurityManager() != null) {
//                constructorToUse = AccessController.doPrivileged(new PrivilegedExceptionAction<Constructor<?>>() {
//                    @Override
//                    public Constructor<?> run() throws Exception {
//                        return clazz.getDeclaredConstructor((Class[]) null);
//                    }
//                });
            }
            else {
                constructorToUse =	clazz.getDeclaredConstructor((Class[]) null);
            }

            if ((!Modifier.isPublic( constructorToUse.getModifiers()) ||
                    !Modifier.isPublic( constructorToUse.getDeclaringClass().getModifiers())) && !constructorToUse.isAccessible()) {
                constructorToUse.setAccessible(true);
            }
            Object object =  constructorToUse.newInstance();
            System.out.println( object.getClass().getName() );
            return object ;
         }
        catch (Throwable ex) {
            throw new BeanInstantiationException(clazz, "No default constructor found", ex);
        }

    }


}
