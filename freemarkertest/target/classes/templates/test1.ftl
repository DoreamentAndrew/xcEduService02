<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello WorD!</title>
</head>
<body>
Hello${name}!
<br/>
遍历List中的学生数据(数据模型当中的名称为stus)
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>金额</td>
        <td>出生日期</td>

    </tr>
    <#if stus??>
        <#list stus as stu>
            <tr>
                <td>${stu_index+1}</td>
                <td <#if stu.name=='小明'>style="background-color: darkgray" </#if>>${stu.name}</td>
                <td>${stu.age}</td>
                <td <#if stu.money gt 300    > style="background-color: darkgray"</#if>>${stu.money}</td>
                <td>${stu.birthday?string("YYYY年MM月dd日 ")}</td>
            </tr>
        </#list>

    </#if>
</table>
学生个数:${stus?size}
<br/>
遍历数据模型中的stumap(Hashmap数据),第一种方法,在中括号当中填写map的key
<br/>
姓名:${studentHashMap['stu1'].name}<br/>
年龄:${studentHashMap['stu1'].age}<br/>
第二种方法,在map后面直接加点,然后key<br/>
姓名:${studentHashMap.stu2.name}<br/>
年龄:${studentHashMap.stu2.age}<br/>
遍历map中的key,stuMap?keys就是key列表(是一个list)
<br/>
<#list studentHashMap?keys as k>
    ${studentHashMap[k].name}<br/>
    ${studentHashMap[k].age}<br/>
</#list>

<br/>point:${point?c}<br/>
<#assign text="{'bank':'工商银行','account':'100098098800212'}"/>
<#assign data=text?eval/>
开户银行:${data.bank}<br/>
账户:${data.account}<br/>
</body>
</html>