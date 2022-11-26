package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResultCode;

/**
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description
 * @date 2022/10/15 13:12:59
 */
public class ExceptionCast {
    public static void cast(ResultCode resultCode){
        throw new CustomExcetion(resultCode);
    }
}
