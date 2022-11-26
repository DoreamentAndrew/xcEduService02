package com.xuecheng.test.freemarker.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description
 * @date 2022/10/18 20:48:58
 */
@Data
@ToString
public class Student {
    private String name;
    private int age;
    private Date birthday;
    private Float money;
    private List<Student> friends;
    private Student bestFriends;
}
