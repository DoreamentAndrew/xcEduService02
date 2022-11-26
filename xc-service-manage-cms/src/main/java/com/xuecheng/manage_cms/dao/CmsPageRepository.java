package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Create by Andrewer on 2022/10/4.
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {//只需要继承MongoRepository<模型类型,组件类型>就可以了,而且里面提供了很多现成的方法供我们使用
//    根据页面名称查询
    CmsPage findByPageName(String pageName);
//    根据页面名称和页面类型来查
    CmsPage findByPageNameAndPageType(String pageName,String pageType);
//    根据站点和记录类型查询记录数
    int countBySiteIdAndPageType(String siteId,String pageType);
//    根据站点和页面类型分页查询
    Page<CmsPage> findBySiteIdAndPageType(String siteId, String pageType, Pageable pageable);//pageable分页参数,因为是分页查询,所以需要一个分页参数,此外,site
    // Id和pageType顺序不能乱,应该和前面的名称顺序一样

//    根据页面名称站点id,页面webpath查询
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName,String siteId,String pageWebPath); 
}
