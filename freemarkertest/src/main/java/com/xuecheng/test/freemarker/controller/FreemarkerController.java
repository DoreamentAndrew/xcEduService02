package com.xuecheng.test.freemarker.controller;

import com.xuecheng.test.freemarker.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description
 * @date 2022/10/18 21:10:25
 */
@Controller//这里不要使用RestController,因为返回json数据的化,要输出的是html网页
@RequestMapping("/freemarker")
public class FreemarkerController {
    @Autowired
    RestTemplate restTemplate;

    //    测试1
    @RequestMapping("/banner")
    public String index_banner(Map<String, Object> map) {
//        使用restTemplate请求轮播图模型数据
        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", Map.class);
        Map body = forEntity.getBody();//body就是模型数据
//        设置模型数据
        map.putAll(body);
        return "index_banner";
    }
        //    测试1
    @RequestMapping("/test1")
    public String test1(Map<String, Object> map) {

        map.put("name", "传智播客");

        Student stu1 = new Student();
        stu1.setName("小明");
        stu1.setAge(18);
        stu1.setMoney(10089.89f);
        stu1.setBirthday(new Date());
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setAge(18);
        stu2.setMoney(10089.89f);
        stu2.setBirthday(new Date());
//朋友列表
        List<Student> friends = new ArrayList<>();
        friends.add(stu1);
//        给第二的同学设置朋友列表
        stu2.setFriends(friends);
        stu2.setBestFriends(stu1);
        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);
//        像模型数据中放数据
        map.put("stus", stus);

//        准备map数据
        HashMap<String, Student> studentHashMap = new HashMap<>();
        studentHashMap.put("stu1", stu1);
        studentHashMap.put("stu2", stu2);
map.put("stu1",stu1);
map.put("studentHashMap",studentHashMap);
map.put("point",1234564879);

//        返回freemarker模板的位置,基于resource/templates路径的

        return "test1";
    }
}
