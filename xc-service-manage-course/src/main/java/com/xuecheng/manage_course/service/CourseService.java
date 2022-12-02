package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import com.xuecheng.manage_course.dao.TeachplanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    TeachplanRepository teachplanRepository;
    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    CourseBaseRepository courseBaseRepository;
//    课程计划查询
    public TeachplanNode findTeachplanList(String couseId){
        return teachplanMapper.selectList(couseId);
    }
//添加课程计划
//操作mysql数据库是需要有事务控制的
//    一定要注意,增删改操作加事务

    @Transactional//mongoDb是不支持事务的就不用加
    public ResponseResult addTeachplan(Teachplan teachplan) {
//        注意无论如何,一定要记住对数据进行检查
        if (teachplan==null||
                StringUtils.isEmpty(teachplan.getCourseid())||
                StringUtils.isEmpty(teachplan.getPname())
                ){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);//弹出无效参数异常
        }
//        课程计划
        String courseid = teachplan.getCourseid();
//        页面传入的parentId
        String parentid = teachplan.getParentid();
        if (StringUtils.isEmpty(parentid)){
//            取出该课程的根节点
            parentid = this.getTeachplanRoot(courseid);
        }
        Optional<Teachplan> optional = teachplanRepository.findById(parentid);//父节点
        Teachplan parentNode = optional.get();
//        父节点的级别
        String grade = parentNode.getGrade();
//        新节点
        Teachplan teachplanNew = new Teachplan();
//        将页面提交的teachplan信息拷贝到teachplanNew对象中
//        使用Beanutils工具
        BeanUtils.copyProperties(teachplan,teachplanNew);
        teachplanNew.setParentid(parentid);
        teachplanNew.setCourseid(courseid);
        if (grade.equals("1")){
        teachplanNew.setGrade("2");//级别,根据父节点的级别来设置
        }else {
            teachplanNew.setGrade("3");//添加节点,要么是2,要么是3
        }
        teachplanRepository.save(teachplanNew);
        return new ResponseResult(CommonCode.SUCCESS);


//        重点处理parentId
    }

//    单独定义一个方法,查询课程的根节点,如果查询不到,那么,就需要自动添加一个根节点
    private String getTeachplanRoot(String courseId){
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);//可以拿到一个optional
        if (!optional.isPresent())   {//注意这里有一个!的符号
            return null;
        }
        CourseBase courseBase = optional.get();//拿到课程

//查询课程的根据点
        List<Teachplan> teachplanList =
                teachplanRepository.findByCourseidAndParentid(courseId, "0");
        if (teachplanList ==null|| teachplanList.size()<=0){//如果查询不到
//            自动添加根节点
            Teachplan teachplan = new Teachplan();
            teachplan.setParentid("0");
            teachplan.setGrade("1");
//            课程名称需要调用CourseIdRepository来查询
            teachplan.setPname(courseBase.getName());
            teachplan.setStatus("0");//未发布
            teachplanRepository.save(teachplan);
            return teachplan.getId();//自动添加根节点完成
        }
//        查询到
//        返回根节点的id
            return teachplanList.get(0).getId();

    }
}
