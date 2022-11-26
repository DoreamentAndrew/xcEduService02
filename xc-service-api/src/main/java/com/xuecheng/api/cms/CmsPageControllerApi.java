package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageReuqest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.*;

@Api(value = "cms页面管理接口",description = "cms页面管理接口,提供页面的增删改查")
public interface CmsPageControllerApi {
//    页面查询
    @ApiOperation("分页查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码",required = true,paramType = "path",dataType = "int"),//name对应下面的形参,通过url获取,则paramType为path
            @ApiImplicitParam(name = "size",value = "每页记录数",required = true,paramType = "path",dataType = "int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageReuqest queryPageReuqest);
//    新增页面
@ApiOperation("新增页面")

public CmsPageResult add(CmsPage cmsPage);

//根据页面id查询页面信息
    @ApiOperation("根据页面id查询页面信息")
public CmsPage findById(String id);
//    修改页面
    @ApiOperation("修改页面")
    public CmsPageResult edit(String idm, CmsPage cmsPage);


    //    删除页面
    @ApiOperation("删除页面")
    public ResponseResult delete(String id);

//    页面发布
    @ApiOperation("页面发布")
    public ResponseResult post (String pageId);

}
