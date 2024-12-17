package com.springboot.myblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyblogApplication.class, args);
		System.out.println("서버가 성공적으로 열림");
	}

}
