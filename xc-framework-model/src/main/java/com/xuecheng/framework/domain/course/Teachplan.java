package com.xuecheng.framework.domain.course;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by admin on 2018/2/7.
 */
@Data
@ToString
@Entity//JPA中模型类需要设置@Entity表示
@Table(name="teachplan")//表名
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")//设置主键,和生成策略uuid自动生成id
public class Teachplan implements Serializable {
    private static final long serialVersionUID = -916357110051689485L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    private String pname;
    private String parentid;
    private String grade;
    private String ptype;
    private String description;
    private String courseid;
    private String status;
    private Integer orderby;
    private Double timelength;
    private String trylearn;

}
