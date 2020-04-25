package cn.itcast.shop.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "cn.itcast.shop.product.mapper")
@ComponentScan(basePackages = "cn.itcast.shop.redis")
@ComponentScan(basePackages = "cn.itcast.shop.product")
@EnableTransactionManagement
public class ProductApp {
    public static void main(String[] args) {
        SpringApplication.run(ProductApp.class);
    }
}
