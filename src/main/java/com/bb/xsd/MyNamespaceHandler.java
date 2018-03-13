package com.bb.xsd;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by admin on 2018/3/13.
 */
public class MyNamespaceHandler extends NamespaceHandlerSupport {


    @Override
    public void init() {
        registerBeanDefinitionParser("people" , new PeopleBeanDefinitionParser() );
    }


}
