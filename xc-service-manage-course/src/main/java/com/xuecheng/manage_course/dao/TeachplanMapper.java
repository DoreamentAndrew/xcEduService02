package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description
 * @date 2022/11/30 23:45:01
 */
@Mapper
public interface TeachplanMapper {
//    课程计划查询
    public TeachplanNode selectList(String courseId);
}
