<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common-head.jsp" %>
    <title>期刊 - 在线投稿网站</title>
</head>
<body>

<div class="layui-layout layui-layout-admin">
    <%@ include file="common-body-layui-header.jsp" %>

    <%@ include file="common-body-layui-side.jsp" %>

    <div class="layui-body" style="padding: 30px; min-width: 1000px;">
        <!-- 内容主体区域 -->
        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
            <ul class="layui-tab-title">
                <li class="layui-this">所有期刊</li>
                <li>教科文艺</li>
                <li>经济管理</li>
                <li>基础科学</li>
                <li>社会科学</li>
                <li>工程科技</li>
                <li>信息科技</li>
                <li>农业科技</li>
                <li>医药卫生</li>
                <li>>哲学政法</li>
            </ul>

            <div class="layui-tab-content">
                <table class="layui-hide" id="pubList" lay-filter="pubTable"></table>
            </div>
        </div>
    </div>

    <%@ include file="common-body-layui-script.jsp" %>
</div>
</body>

<script type="text/html" id="pubBar">
    <a class="layui-btn layui-btn-xs" lay-event="content">查看详情</a>
</script>

<script>
    // 使用 layui table 插件
    layui.use(['table'], function () {
        // 实例化一个表格对象
        const table = layui.table;

        // 开始渲染表格
        table.render({
            elem: '#pubList', // 选择 table 标签的 id
            url: '<c:url value="/publication/list"/>', // 后端接口
            method: 'get', // http 请求方法
            cellMinWidth: 80, // 列最小宽度
            text: "加载数据出错", // 数据错误时提示
            limits: [10, 20, 50], // 分页限制
            page: true, // 开启分页
            cols: [[ // 表头
                {field: 'issn', title: '国际刊号', sort: true, fixed: 'left'},
                {field: 'name', title: '刊物名称'},
                {field: 'category', title: '类别', sort: true},
                {field: 'language', title: '语种'},
                {field: 'organizer', title: '主办单位'},
                {field: 'publicationFrequency', title: '刊期', sort: true},
                {fixed: 'right', title: '操作', toolbar: '#pubBar'}
            ]]
        });

        // 点击 pubBar 的事件
        table.on('tool(pubTable)', function (obj) {
            console.log(obj);
            const publicationId = obj.data.id;
            window.open('<c:url value="/publication/content/id/"/>' + publicationId);
        });
    });
</script>
</html>