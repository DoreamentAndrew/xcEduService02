package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常捕获类
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description
 * @date 2022/10/15 13:17:52
 */
@ControllerAdvice//控制器增强的一个注解
public class ExceptionCatch {


    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

//    定义map,配置异常类型所对应的错误代码
    private static ImmutableMap<Class<? extends Throwable>,ResultCode>EXCEPTIONS;
//定义map的一个buliter对象,去构建ImmutalMap
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();



//    捕获CustomException此类异常
    @ExceptionHandler(CustomExcetion.class)
    @ResponseBody
    public ResponseResult customException(CustomExcetion customExcetion){
        //记录日志
        LOGGER.error("catch exception:{}",customExcetion.getMessage());
        ResultCode resultCode = customExcetion.getResultCode();
        return new ResponseResult(resultCode);
    }


    //    捕获Exception此类异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception){
        //记录日志
        LOGGER.error("catch exception:{}",exception.getMessage());
        if (EXCEPTIONS==null){
          EXCEPTIONS =    builder.build();//此时map构建成功,不能更改该
        }
//        从EXCEPTIONS中寻找异常类型所对应的错误代码,如果找到了将错误代码响应给用户,如果没有找到给用户响应99999异常
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        if (resultCode!=null){
        return new ResponseResult(resultCode);

        }else {
//返回9999异常
        return new ResponseResult(CommonCode.SERVER_ERROR);
        }
    }
    static{
//        定义了异常类型所对应的错误代码
        builder.put(HttpMessageNotReadableException.class,CommonCode.INVALID_PARAM);
    }
}
