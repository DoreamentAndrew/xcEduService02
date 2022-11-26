package com.xuecheng.manage_cms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description
 * @date 2022/11/17 21:04:58
 */
@SpringBootApplication

@EntityScan("com.xuecheng.framework.domain.cms")//扫描实体类
@ComponentScan(basePackages = {"com.xuecheng.framework"})//扫描本项目下的所有的类//这个不加也可以扫描,因为启动类会扫描包及所在目录下所有子包的bean
@ComponentScan(basePackages = {"com.xuecheng.manage_cms_client"})//扫描本项目下的所有的类//这个不加也可以扫描,因为启动类会扫描包及所在目录下所有子包的bean



public class    ManageCmsClientApplication {
    public static void main(String[] args) {
        SpringApplication.run( ManageCmsClientApplication.class,args);
    }
}


