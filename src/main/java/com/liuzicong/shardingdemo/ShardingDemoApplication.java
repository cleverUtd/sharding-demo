package com.liuzicong.shardingdemo;

import com.liuzicong.shardingdemo.service.OrderService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.sql.SQLException;

@MapperScan(basePackages = "com.liuzicong.shardingdemo.repository")
@SpringBootApplication(scanBasePackages = "com.liuzicong.shardingdemo")
public class ShardingDemoApplication {

	public static void main(String[] args) throws SQLException {
		try(ConfigurableApplicationContext applicationContext = SpringApplication.run(ShardingDemoApplication.class, args)) {
			OrderService orderService = applicationContext.getBean(OrderService.class);
			try {
				orderService.initEnvironment();
				orderService.processSuccess();
			} finally {
				orderService.cleanEnvironment();
			}
		}
	}

}
