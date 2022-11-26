package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsPageParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description
 * @date 2022/10/4 15:21:57
 */

@SpringBootTest//一旦运行,就回去springboot的启动类,找到启动类后,类中有许多扫描的设置,就会扫描指定包下面的bean,然后我们就可以从容器中的去拿我们所编写的东西
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    CmsPageRepository cmsPageRepository;

    @Test
    public void testFindAll() {
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }

    //    分页查询
    @Test
    public void findPage() {
//        分页参数
        int page = 0;//从0开始
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    //    自定义条件查询
    @Test
    public void testFindAllByExample() {
        //上面 的一个分页参数
        int page = 0;//从0开始
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);

//        条件值对象
        CmsPage cmsPage = new CmsPage();
//        表示要查询5a751fab6abb5044e0d19ea1站点的页面
//        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setPageAliase("播图");
//        设置模板那id条件
//        cmsPage.setTemplateId("5a962b52b00ffc514038faf7");
//       条件匹配器
        ExampleMatcher  exampleMatcher =  ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());

//         exampleMatcher = exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
//         ExampleMatcher.GenericPropertyMatchers.contains()包含关键子
//        ExampleMatcher.GenericPropertyMatchers.exact()精确匹配
//        ExampleMatcher.GenericPropertyMatchers.endsWith()末尾匹配
//        ExampleMatcher.GenericPropertyMatchers.startsWith()前置匹配
//        ExampleMatcher.GenericPropertyMatchers.caseSensitive()区分大小写

        //定义Example

        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> content = all.getContent();
        System.out.println(content);


    }

    //    添加
    @Test
    public void testInsert() {
//        定义实体类
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("s01");
        cmsPage.setTemplateId("t01");
        cmsPage.setPageName("测试页面");
        cmsPage.setPageCreateTime(new Date());
        List<CmsPageParam> cmsPageParams = new ArrayList<>();
        CmsPageParam cmsPageParam = new CmsPageParam();
        cmsPageParam.setPageParamName("param1");
        cmsPageParam.setPageParamValue("value1");
        cmsPageParams.add(cmsPageParam);
        cmsPage.setPageParams(cmsPageParams);
        cmsPageRepository.save(cmsPage);
        System.out.println(cmsPage);
    }

    //    删除
    @Test
    public void testDelete() {
        cmsPageRepository.deleteById("633be646c7380b11dcfbfc5c");
    }

    //    修改
    @Test
    public void testUpdate() {
        Optional<CmsPage> optional = cmsPageRepository.findById("633be814c7380b4204442292");
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            cmsPage.setPageName("测试页面01");
            cmsPage.setPageAliase("test01");
            CmsPage save = cmsPageRepository.save(cmsPage);
            System.out.println(save);
        }

    }

    //    根据页面名称查询
    @Test
    public void testfindByPageName() {
        CmsPage cmsPage = cmsPageRepository.findByPageName("测试页面01");
        System.out.println(cmsPage);
    }
}
