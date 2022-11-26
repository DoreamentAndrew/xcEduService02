package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Create by Andrewer on 2022/10/4.
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {//只需要继承MongoRepository<模型类型,组件类型>就可以了,而且里面提供了很多现成的方法供我们使用

}
