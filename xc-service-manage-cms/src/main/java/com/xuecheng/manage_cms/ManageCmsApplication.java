package com.xuecheng.manage_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Entity;

@SpringBootApplication

//启动包中的main一旦运行,就会扫描启动包下的所有子包以及bean,如果将启动类放在config等子包下,就只会扫描config包下的bean
//而放到xuecheng下面,就会扫描xuecheng下面的所有bean,有可能会扫描多,所以,要吧启动类放到本项目的根路径下面

@EntityScan("com.xuecheng.framework.domain.cms")//扫描实体类
@ComponentScan(basePackages = {"com.xuecheng.api"})//扫描接口
@ComponentScan(basePackages = {"com.xuecheng.manage_cms"})//扫描本项目下的所有的类//这个不加也可以扫描,因为启动类会扫描包及所在目录下所有子包的bean
@ComponentScan(basePackages = {"com.xuecheng.framework"})//扫描本项目下的所有的类//这个不加也可以扫描,因为启动类会扫描包及所在目录下所有子包的bean
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class,args);
    }

@Bean
public RestTemplate restTemplate(){
    return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
}}
