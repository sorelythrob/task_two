package com.task.two;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@MapperScan("com.task.two.mapper")
@EnableTransactionManagement
@SpringBootApplication
public class SpringbootTaskTwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootTaskTwoApplication.class, args);
	}

}
