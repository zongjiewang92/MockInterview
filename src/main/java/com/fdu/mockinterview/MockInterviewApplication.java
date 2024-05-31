package com.fdu.mockinterview;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fdu.mockinterview.mapper")
public class MockInterviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockInterviewApplication.class, args);
	}

}
