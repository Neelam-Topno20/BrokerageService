package com.bs.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bs.beans.Trade;
import com.bs.beans.User;

@SpringBootApplication(scanBasePackages = "com.bs")
@EntityScan(basePackages = "com.bs.beans")
@EnableJpaRepositories(basePackages = "com.bs.dao")
public class BrokerageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrokerageServiceApplication.class, args);

	}

}
