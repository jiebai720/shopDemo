package com.bb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ShopDemoApplication {

	public static void main(String[] args) {

//        System.out.println("aaaa");
		SpringApplication.run(ShopDemoApplication.class, args);
	}


}
