package org.com.gx.ebusiness;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.com.gx.ebusiness.mapper")// 告诉 Spring 扫描 mapper 包下的接口
public class EBusinessSpringBootFirstApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBusinessSpringBootFirstApplication.class, args);
    }

}
