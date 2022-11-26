package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Create by Andrewer on 2022/10/4.
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {

}