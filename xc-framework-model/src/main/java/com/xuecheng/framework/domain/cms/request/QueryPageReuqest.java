package com.xuecheng.framework.domain.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPageReuqest {
//    接收页面查询的条件
//    1.站点id
    @ApiModelProperty("站点id")
    private String siteId;
//    页面ID
    @ApiModelProperty("页面ID")
    private String PageId;
//   页面名称
    @ApiModelProperty("页面名称")
    private String pageName;
//    别名
    @ApiModelProperty("页面别名")
    private String pageAliase;
//    模板id
    @ApiModelProperty("模板id ")
    private String templateId;

}
