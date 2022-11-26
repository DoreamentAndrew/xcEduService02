package com.xuecheng.test.freemarker;

import com.xuecheng.test.freemarker.model.Student;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description
 * @date 2022/10/19 13:32:59
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FreemarkerTest {
    //    测试freemarker的静态化,基于ftl模板文件来生成html
    @Test
    public void testGenerateHtml() throws IOException, TemplateException {
//        定义配置类
        Configuration configuration = new Configuration(Configuration.getVersion());

//        定义模板
//        得到classpath的模板路径
        String classpath = this.getClass().getResource("/").getPath();
//        定义一个模板路径
        configuration.setDirectoryForTemplateLoading(new File(classpath + "/templates"));//设置一个模板目录
//        获取模板文件的内容
        Template template = configuration.getTemplate("test1.ftl");
//        定义一个数据模型
        Map map = getMap();

//        静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
//        System.out.println(content);
        InputStream inputStream = IOUtils.toInputStream(content);
        FileOutputStream outputStream = new FileOutputStream(new File("d:/test1.html"));
//        输出文件
        IOUtils.copy(inputStream, outputStream);
        inputStream.close();
        outputStream.close();

    }

    //基于模板文件的内容(字符串 )生成html文件
    @Test
    public void GenerateHtmlByString() throws IOException, TemplateException {
//        定义配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
//      定义模板
//模板内容(字符串)
        String templateString    =""+
                "<html>\n"+
                "<head> </head>\n"+
                "<body>\n"+
                "名称:${name}\n"+
                "</body>\n"+
                "</html>";

//        使用一个模板加载起变为模板
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template",templateString);

//        在配置中设置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
//        获取模板的内容
        Template template = configuration.getTemplate("template", "utf-8");
//        定义模型数据

        Map map = getMap();

//        静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
//        System.out.println(content);
        InputStream inputStream = IOUtils.toInputStream(content);
        FileOutputStream outputStream = new FileOutputStream(new File("d:/test1.html"));
//        输出文件
        IOUtils.copy(inputStream, outputStream);
        inputStream.close();
        outputStream.close();
    }

    //    数据模型
    public Map getMap() {

        Map map = new HashMap();
        map.put("name", "传智播客");

        Student stu1 = new Student();
        stu1.setName("小明");
        stu1.setAge(18);
        stu1.setMoney(10089.89f);
        stu1.setBirthday(new Date());
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setAge(18);
        stu2.setMoney(10089.89f);
        stu2.setBirthday(new Date());
//朋友列表
        List<Student> friends = new ArrayList<>();
        friends.add(stu1);
//        给第二的同学设置朋友列表
        stu2.setFriends(friends);
        stu2.setBestFriends(stu1);
        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);
//        像模型数据中放数据
        map.put("stus", stus);

//        准备map数据
        HashMap<String, Student> studentHashMap = new HashMap<>();
        studentHashMap.put("stu1", stu1);
        studentHashMap.put("stu2", stu2);
        map.put("stu1", stu1);
        map.put("studentHashMap", studentHashMap);
        map.put("point", 1234564879);

//        返回freemarker模板的位置,基于resource/templates路径的

        return map;
    }
}
