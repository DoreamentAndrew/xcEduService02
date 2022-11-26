package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageReuqest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.CustomExcetion;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.config.RabbitmqConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description
 * @date 2022/10/4 17:44:08
 */
@Service
public class PageService {
    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    CmsConfigRepository cmsConfigRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CmsTemplateRepository cmsTemplateRepository;
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    GridFSBucket gridFSBucket;
    @Autowired
    RabbitTemplate rabbitTemplate;


    /**
     * 页面查询的一个方法
     *
     * @param page             页码,从1开始计数
     * @param size             每页记录数
     * @param queryPageReuqest 查询条件
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageReuqest queryPageReuqest) {//建议和controller中保持一直,这样的好处是后期调用起来非常的方便

        if (queryPageReuqest == null) {
            QueryPageReuqest pageReuqest = new QueryPageReuqest();
        }
//        自定义条件的查询
//        1.定义一个条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
//        2.条件的值对象
        CmsPage cmsPage = new CmsPage();
//        3.设置条件的值
//        设置站点id作为查询条件
        if (StringUtils.isNotEmpty(queryPageReuqest.getSiteId())) {
            cmsPage.setSiteId(queryPageReuqest.getSiteId());
        }
//设置模板id作为查询条件
        if (StringUtils.isNotEmpty(queryPageReuqest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageReuqest.getTemplateId());
        }
//设置页面的别名作为查询的条件
        if (StringUtils.isNotEmpty(queryPageReuqest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageReuqest.getPageAliase());
        }
//定义一个条件对象
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);


        //        分页参数
        if (page <= 0) {
            page = 1;
        }
        page = page - 1;
        if (size <= 0) {
            size = 10;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);//实现自定义条件查询并且分页查询
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());//数据列表
        queryResult.setTotal(all.getTotalElements());//数据总记录数
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);

        return queryResponseResult;
    }

/*//    新增页面
    public CmsPageResult add(CmsPage cmsPage){
//        校验页面名称,站点id,页面webpath的唯一性
//        根据三个属性,查询页面中是否有相同元素,如果有,则停止,如果没有,则继续添加
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cmsPage1 == null) {
//        调用dao新增页面
            cmsPage.setPageId(null);
            cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
        }
//        提交添加失败
        return new CmsPageResult(CommonCode.FAIL,null);

    }*/

    //    新增页面
    public CmsPageResult add(CmsPage cmsPage) {
        if (cmsPage == null) {
            //抛出异常,非法参数异常
        }
//        校验页面名称,站点id,页面webpath的唯一性
//        根据三个属性,查询页面中是否有相同元素,如果有,则停止,如果没有,则继续添加
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cmsPage1 != null) {
//            页面已经存在
//            抛出异常,内容就是异常已经存在
//            throw new CustomExcetion(CommonCode.FAIL);
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        if (cmsPage1 == null) {
//        调用dao新增页面
            cmsPage.setPageId(null);
            cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }
//        提交添加失败
        return new CmsPageResult(CommonCode.FAIL, null);

    }

    //    根据id查询页面
    public CmsPage getById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            return cmsPage;
        }
        return null;

    }

    //    修改页面
    public CmsPageResult update(String id, CmsPage cmsPage) {
//根据id从数据库查询页面信息
        CmsPage cmsPage1 = this.getById(id);
        if (cmsPage1 != null) {
//            准备跟新数据
//            设置要修改的值
//            更新面板id
            cmsPage1.setPageId(cmsPage.getPageId());
//   更新所属站点
            cmsPage1.setSiteId(cmsPage.getSiteId());
//            跟新模板id
            cmsPage1.setTemplateId(cmsPage.getTemplateId());
//            更新页面别名
            cmsPage1.setPageAliase(cmsPage.getPageAliase());
//            更新页面名称
            cmsPage1.setPageName(cmsPage.getPageName());
//            更新访问路径
            cmsPage1.setPageWebPath(cmsPage.getPageWebPath());
//            更新物理路径
            cmsPage1.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
//            更新dataUrl
            cmsPage1.setDataUrl(cmsPage.getDataUrl());
//            提交修改
            cmsPageRepository.save(cmsPage1);
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage1);

        }
//        修改失败
        return new CmsPageResult(CommonCode.FAIL, null);
    }


    //    根据id删除页面
    public ResponseResult delete(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            cmsPageRepository.deleteById(id);

            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    //    根据id查询cmsConfig
    public CmsConfig getConfigById(String id) {
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        if (optional.isPresent()) {
            CmsConfig cmsConfig = optional.get();
            return cmsConfig;
        }
        return null;

    }


//    页面静态化的方法

    /**
     * 2.静态化程序获取页面的Dataurl
     * 3.静态程序远程请求Dataurl获取数据类型
     * 4.静态话程序获取程序页面的模板信息
     * 5.执行页面静态化
     */
    public String getPageHtml(String pageId) {
//        获取数据模型
        Map model = getModelByPageId(pageId);
        if (model == null) {
//            数据模型获取不到
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
//        获取页面的模板

        String template = getTemplateById(pageId);
        if (StringUtils.isEmpty(template)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
//        执行静态化
        String html = generateHtml(template, model);
        return html;

    }

    //    执行静态化
    private String generateHtml(String templateContent, Map model) {
//        创建一个配置对象
        Configuration configuration = new Configuration(Configuration.getVersion());
//      创建一个模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", templateContent);
//        像configuration中配置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
//        获取模板内容
        try {
            Template template = configuration.getTemplate("template");
//            调用api进行静态化
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //    获取页面的模板
    private String getTemplateById(String pageId) {
//        取出页面信息
        CmsPage cmsPage = this.getById(pageId);
        if (cmsPage == null) {
//            页面不存在
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXTSTS);
        }
//        获取页面的模板id
        String templateId = cmsPage.getTemplateId();
        if (StringUtils.isEmpty(templateId)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
//        查询模板信息
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);

        if (optional.isPresent()) {
            CmsTemplate cmsTemplate = optional.get();
//            获取模板文件id
            String templateFileId = cmsTemplate.getTemplateFileId();
//            从GridFS中获取模板文件的内容
            //        根据id查询文件
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));

//        打开一个下载流对象
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
//        创建一个GridFSResource对象,操作流
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
//        从流中获取数据
            String content = null;
            try {
                content = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
                return content;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(content);

        }
        return null;

    }

    ;


    //    获取数据模型
    private Map getModelByPageId(String pageId) {

//        取出页面的信息
        CmsPage cmsPage = this.getById(pageId);
        if (cmsPage == null) {
//            页面不存在
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXTSTS);
        }
//        获取页面的DataUrl
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isEmpty(dataUrl)) {
//        页面的DataUrl为空异常
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
//        通过restTemplate来请求DataUrl数据
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        return body;

    }

//     页面发布
    public ResponseResult post (String pageId){

//        执行页面的静态化
        String pageHtml = this.getPageHtml(pageId);
//        将页面静态化的文件存储到GridFs中
        CmsPage cmsPage = saveHtml(pageId, pageHtml);
//        向MQ中发送消息
        sendPostPage(pageId);
        return new ResponseResult(CommonCode.SUCCESS);
    }
//    向mq发送消息
    private void sendPostPage(String pageId){
//        得到页面信息
        CmsPage cmsPage = this.getById(pageId);
        if (cmsPage ==null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
//        创建消息对象
        Map<String,String> msg = new HashMap<>();
        msg.put("pageId",pageId );
//        转成JSON串
        String jsonString = JSON.toJSONString(msg);
//        发送给mq
//        站点id
        String siteId = cmsPage.getSiteId();
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE,siteId,jsonString);
    }

//    保存html到GridFs
    private CmsPage saveHtml(String pageId ,String htmlContent){
//        先得到页面的信息
        CmsPage cmsPage = this.getById(pageId);
        if (cmsPage ==null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
//        将htmlContent转换为输入流
        ObjectId objectId = null;
        try {
            InputStream inputStream = IOUtils.toInputStream(htmlContent, "utf-8");
//        将html文件保存到GeridFs中
             objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        将html文件的id更新到cmsPage中
        cmsPage.setHtmlFileId(objectId.toHexString());
        cmsPageRepository.save(cmsPage);
        return cmsPage;

    }
}
