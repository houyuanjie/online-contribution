<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../common-head.jsp" %>
    <title>详细内容 - 期刊</title>
</head>
<body>

<div class="layui-layout layui-layout-admin">
    <%@ include file="../../common-body-layui-header.jsp" %>

    <%@ include file="../../common-body-layui-side.jsp" %>

    <div class="layui-body" style="padding: 30px; min-width: 1100px;">
        <!-- 内容主体区域 -->
        <fieldset class="layui-elem-field">
            <legend style="font-weight: bold;">刊物信息</legend>
            <div class="layui-field-box">
                <div style="width: 30%; float: left;">
                    <div style="padding-left: 80px; padding-bottom: 30px">
                        <img style="border: cadetblue solid 1px;"
                             src="<c:url value="/publication/cover-picture/name/${publication.name}"/>"
                             alt="图片加载失败" width="280px" height="380px"/>
                    </div>
                </div>
                <div style="width: 51%; float: right; min-height: 32px;line-height: 32px;font-size: 17px; padding-right: 110px; padding-bottom: 30px;">
                    <p><b>刊物名称: </b>${publication.name}</p>
                    <p><b>国际刊号: </b>${publication.issn}</p>
                    <p><b>刊物类型: </b>${publication.category}</p>
                    <p><b>主办单位: </b>${publication.organizer}</p>
                    <p><b>刊期: </b>${publication.publicationFrequency}</p>
                    <p><b>语种: </b>${publication.language}</p>
                    <p><b>简介: </b>${publication.info}</p>
                </div>
            </div>
        </fieldset>
        <br>
        <div class="layui-tab-content">
            <table class="layui-hide" id="manuList" lay-filter="manuTable"></table>
        </div>

    </div>
</div>
<%@ include file="../../common-body-layui-script.jsp" %>
</body>

<script type="text/html" id="manuBar">
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="view">查看信息</a>
    <a class="layui-btn layui-btn-xs" lay-event="download">下载文章</a>
    <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete">删除文章</a>
</script>

<script>
    // 使用 layui table 插件
    layui.use(['table', 'layer', 'jquery'], function () {
        // 实例化一个表格对象
        const table = layui.table;
        const layer = layui.layer;
        const $ = layui.jquery;

        // 开始渲染表格
        table.render({
            elem: '#manuList', // 选择 table 标签的 id
            url: '<c:url value="/publication/content/id/${publication.id}/manuscript/list/approved"/>', // 后端接口
            method: 'get', // http 请求方法
            cellMinWidth: 80, // 列最小宽度
            text: "加载数据出错", // 数据错误时提示
            limits: [10, 20, 50], // 分页限制
            page: true, // 开启分页
            cols: [[ // 表头
                {field: 'title', title: '文章题目', sort: true, fixed: 'left'},
                {field: 'author', title: '作者名称'},
                {field: 'organization', title: '单位', sort: true},
                {fixed: 'right', title: '操作', toolbar: '#manuBar'}
            ]]
        });

        // 点击 manuBar 的事件
        table.on('tool(manuTable)', function (obj) {
            if (obj.event === 'download') {
                const fileId = obj.data.bytesFile.id;
                window.open('<c:url value="/user/file/id/"/>' + fileId);
            } else if (obj.event === 'view') {
                layer.open({
                    title: obj.data.title,
                    type: 1,
                    area: ['55%', '80%'],
                    content: '<div style="min-height: 32px;line-height: 32px; font-size: 17px; padding: 75px;">'
                        + '<p><b>论文标题：</b>' + obj.data.title + '</p>'
                        + '<p><b>作者：</b>' + obj.data.author + '</p>'
                        + '<p><b>单位：</b>' + obj.data.organization + '</p>'
                        + '<p><b>关键字：</b>' + obj.data.keywords + '</p>'
                        + '<p><b>摘要：</b>' + obj.data.summary + '</p> </div>'

                });
            } else if (obj.event === 'delete') {
                //弹出删除窗口,确认是否删除
                layer.confirm("是否删除?", {icon: 3, title: "提示"}, function (index) {
                    //调用AJAX删除后台数据--> 获取删除数据的ID
                    const delUrl = '<c:url value="/user/manuscript/delete/"/>' + obj.data.id;

                    $.ajax({
                        url: delUrl,
                        data: {},
                        type: 'post',
                        async: false,
                        success: function (res) {
                            layer.msg(res.msg);
                            //关闭弹出层
                            layer.close(index);
                            //刷新表格
                            table.reload("manuList");
                        },
                    });
                });
            }

            return false;
        });
    });
</script>
</html>