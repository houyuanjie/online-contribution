<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common-head.jsp" %>
    <title>详细内容 - 期刊</title>
</head>
<body>

<div class="layui-layout layui-layout-admin">
    <%@ include file="common-body-layui-header.jsp" %>

    <%@ include file="common-body-layui-side.jsp" %>

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
                    <p><b>刊物名称：</b>${publication.name}</p>
                    <p><b>国际刊号：</b>${publication.issn}</p>
                    <p><b>刊物类型：</b>${publication.category}</p>
                    <p><b>主办单位：</b>${publication.organizer}</p>
                    <p><b>刊期：</b>${publication.publicationFrequency}</p>
                    <p><b>语种：</b>${publication.language}</p>
                    <p><b>简介：</b>${publication.info}</p>
                </div>
            </div>
        </fieldset>
        <br>
        <div class="layui-tab-content">
            <table class="layui-hide" id="manuList" lay-filter="manuTable"></table>
        </div>

    </div>
</div>
<%@ include file="common-body-layui-script.jsp" %>
</body>

<script type="text/html" id="manuBar">
    <a class="layui-btn layui-btn-xs" lay-event="download">下载文章</a>
</script>

<script>
    // 使用 layui table 插件
    layui.use(['table'], function () {
        // 实例化一个表格对象
        const table = layui.table;

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
                {field: 'publication', title: '所属刊物'},
                {fixed: 'right', title: '操作', toolbar: '#manuBar'}
            ]]
        });

        // 点击 pubBar 的事件
        table.on('tool(manuTable)', function (obj) {
            console.log(obj);
            const publicationId = obj.data.id;
            window.open('<c:url value="/publication/content/id/"/>' + publicationId);
        });
    });
</script>
</html>