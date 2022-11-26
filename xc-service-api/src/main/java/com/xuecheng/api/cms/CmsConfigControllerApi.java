package com.xuecheng.api.cms;

/**
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description
 * @date 2022/10/21 17:30:26
 */

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api (value = "cms配置管理接口",description = "cms配置管理接口,提供数据模型的管理,查询接口")
public interface CmsConfigControllerApi {
    @ApiOperation("根据id查询cms的信息")
    public CmsConfig getmodel(String id);
}
