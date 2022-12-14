package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description
 * @date 2022/12/1 15:14:19
 */
public interface TeachplanRepository extends JpaRepository<Teachplan ,String> {
//    根据课程id和parentid 查询teachplan可能产生多个记录所以用List
/*
SELECT
  *
FROM
  teachplan a
WHERE a.courseid = '4028e581617f945f01617f9dabc40000'
  AND a.parentid = '0'
* */
    public List<Teachplan> findByCourseidAndParentid(String courseId,String parentId);

}
