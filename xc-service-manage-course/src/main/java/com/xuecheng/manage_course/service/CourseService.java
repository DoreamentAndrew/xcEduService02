package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description
 * @date 2022/12/1 14:02:05
 */
@Service
public class CourseService {
    @Autowired
    TeachplanMapper teachplanMapper;
//    课程计划查询
    public TeachplanNode findTeachplanList(String couseId){
        return teachplanMapper.selectList(couseId);
    }
}
