package com.xuecheng.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

/**
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description
 * @date 2022/11/17 23:38:49
 */
@Service
public class   PageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageService.class);
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    GridFSBucket gridFSBucket;
    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    CmsSiteRepository cmsSiteRepository;

    //    保存html页面到服务器的物理路径
    public void  savePageToServerPath(String pageId) {
//        根据pageId查询cmsPage
        CmsPage cmsPage = this.findCmsPageById(pageId);
//        1.得到html文件的id,从cmspage中获取htmlFiledId内容
        String htmlFileId = cmsPage.getHtmlFileId();

//        2.从GridFS中查询html文件

         InputStream inputStream = this.getFileById(htmlFileId);
        if (inputStream == null) {
            LOGGER.error("getFileById InputStream is null,htmlFielId:{}", htmlFileId);
            return;
        }
//        得到站点的id
        String siteId = cmsPage.getSiteId();
//        得到站点的信息
        CmsSite cmsSite = this.findCmsSiteById(siteId);

//        得到站点的物理路径
         String sitePhysicalPath = cmsSite.getSitePhysicalPath();


//        得到页面的物理路径
        String pagePath = sitePhysicalPath + cmsPage.getPagePhysicalPath() + cmsPage.getPageName();
        FileOutputStream fileOutputStream = null;
//        3.将html文件保存到服务器的物理路径上面
        try {
            fileOutputStream = new FileOutputStream(new File(pagePath));
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //    根据文件的id从GridFs中查询文件的内容
    public InputStream getFileById(String fileId) {
//        文件的对象
        GridFSFile gridFsFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
//        打开下载流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFsFile.getObjectId());
//        定义GridFsResourse
        GridFsResource gridFsResource = new GridFsResource(gridFsFile, gridFSDownloadStream);

        try {
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //    根据页面的id来查询html文件的id
    public CmsPage findCmsPageById(String pageId) {
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;

    }

    //    根据站点的id来查询站点文件的信息
    public CmsSite findCmsSiteById(String siteId) {
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;

    }
}
