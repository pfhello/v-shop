package cn.itcast.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "cn.itcast.shop.user.mapper")
@EnableTransactionManagement
@EnableAsync
@ComponentScan(basePackages = "cn.itcast.shop.redis")
@ComponentScan(basePackages = "cn.itcast.shop.user")
public class UserApp {

    public static void main(String[] args) {
        SpringApplication.run(UserApp.class);
    }
}
