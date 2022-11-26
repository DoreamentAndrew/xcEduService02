package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageReuqest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController//@Controller+@ResponseBody:把响应的结果集转换为json
@RequestMapping("/cms/page")//根访问路径
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    PageService pageService;
    @Override
    @GetMapping("/list/{page}/{size}")//相当于RequestMapping使用get方式|{}方法是通过url来进行传参page用下面的形参进行接收,在形参中通过@PathVariable进行形参的接收
    public QueryResponseResult findList(@PathVariable("page") int page,@PathVariable("size") int size, QueryPageReuqest queryPageReuqest) {
//        暂时使用静态数据进行返回
//        定义
//        QueryResult<CmsPage> queryResult =new QueryResult<>();
//        List<CmsPage> list = new ArrayList<>();
//        CmsPage cmsPage = new CmsPage();
//        cmsPage.setPageName("测试页面");
//        list.add(cmsPage);
//        queryResult.setList(list);
//        queryResult.setTotal(1);
//        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
//        return queryResponseResult;
//        调用service
        return pageService.findList(page, size, queryPageReuqest);
    }

    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return pageService.add(cmsPage);
    }

    @Override
    @GetMapping("/get/{id}")
    public CmsPage findById(@PathVariable("id") String id) {
        return pageService.getById(id);
    }

    @Override
    @PutMapping("/edit/{id}")//这里使用put方法,put方法表示更新
    public CmsPageResult edit(@PathVariable("id") String id, @RequestBody CmsPage cmsPage) {
        return pageService.update(id,cmsPage);
    }

    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") String id) {
        return pageService.delete(id);
    }

    @Override
    @PostMapping("/postPage/{pageId}")
    public ResponseResult post(@PathVariable("pageId") String pageId) {

        return pageService.post(pageId);
    }
}
