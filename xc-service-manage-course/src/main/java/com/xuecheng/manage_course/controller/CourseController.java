package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description
 * @date 2022/12/1 14:04:57
 */
@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {

    @Autowired
    CourseService courseService;
    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {

        return courseService.findTeachplanList(courseId);
    }

    @Override
    @PostMapping("/teachplan/add")//post提交json数据
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachplan(teachplan);
    }
}
