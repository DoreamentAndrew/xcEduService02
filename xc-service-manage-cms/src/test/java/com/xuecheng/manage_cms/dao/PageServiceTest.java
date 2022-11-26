package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsPageParam;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.manage_cms.service.PageService;
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
public class PageServiceTest {
    @Autowired
    PageService pageService;
@Autowired
CmsTemplateRepository cmsTemplateRepository;
   @Test
    public void testGetPageHtml(){
       String pageHtml = pageService.getPageHtml("634a47d4c7380b15bce1bd50");
       System.out.println(pageHtml);
   }
   @Test
    public void testTemplateFindById(){
       Optional<CmsTemplate> op = cmsTemplateRepository.findById("6354d498c7380b0e24c0d634");
       System.out.println(op);
   }
}
