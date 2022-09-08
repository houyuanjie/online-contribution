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
        <div class="layui-inline">
            <div class="layui-input-inline">
                <input type="text" id="search" class="layui-input" placeholder="搜索刊物..."/>
            </div>
            <button type="button" id="searchBtn" class="layui-btn layui-btn-sm">
                <i class="layui-icon">&#xe615;</i>
            </button>
        </div>
        <div class="layui-tab layui-tab-brief">
            <ul class="layui-tab-title">
                <li class="${ "_ALL".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/publication"><c:param name="category" value="_ALL"/></c:url>'>所有期刊</a>
                </li>
                <li class="${ "教科文艺".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/publication"><c:param name="category" value="教科文艺"/></c:url>'>教科文艺</a>
                </li>
                <li class="${ "经济管理".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/publication"><c:param name="category" value="经济管理"/></c:url>'>经济管理</a>
                </li>
                <li class="${ "基础科学".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/publication"><c:param name="category" value="基础科学"/></c:url>'>基础科学</a>
                </li>
                <li class="${ "社会科学".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/publication"><c:param name="category" value="社会科学"/></c:url>'>社会科学</a>
                </li>
                <li class="${ "工程科技".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/publication"><c:param name="category" value="工程科技"/></c:url>'>工程科技</a>
                </li>
                <li class="${ "信息科技".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/publication"><c:param name="category" value="信息科技"/></c:url>'>信息科技</a>
                </li>
                <li class="${ "农业科技".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/publication"><c:param name="category" value="农业科技"/></c:url>'>农业科技</a>
                </li>
                <li class="${ "医药卫生".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/publication"><c:param name="category" value="医药卫生"/></c:url>'>医药卫生</a>
                </li>
                <li class="${ "哲学政法".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/publication"><c:param name="category" value="哲学政法"/></c:url>'>哲学政法</a>
                </li>
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
    layui.use(['table', 'jquery'], function () {
        const $ = layui.jquery;
        // 实例化一个表格对象
        const table = layui.table;

        // 开始渲染表格
        table.render({
            elem: '#pubList', // 选择 table 标签的 id
            url: '<c:url value="/publication/list"/>', // 后端接口
            method: 'get', // http 请求方法
            where: {
                'category': '${category}'
            },
            cellMinWidth: 80, // 列最小宽度
            text: "加载数据出错", // 数据错误时提示
            limits: [10, 20, 50], // 分页限制
            page: true, // 开启分页
            cols: [[ // 表头
                {field: 'issn', title: '国际刊号', sort: true, fixed: 'left'},
                {field: 'name', title: '刊物名称', width: 210},
                {field: 'category', title: '类别', width: 150, sort: true},
                {field: 'language', title: '语种'},
                {field: 'organizer', title: '主办单位', width: 390},
                {field: 'publicationFrequency', title: '刊期', sort: true},
                {fixed: 'right', title: '操作', toolbar: '#pubBar'}
            ]]
        });

        // 点击 pubBar 的事件
        table.on('tool(pubTable)', function (obj) {
            const publicationId = obj.data.id;
            window.open('<c:url value="/publication/content/id/"/>' + publicationId);
        });

        // 搜索事件
        $('#searchBtn').click(function () {
            const searchStr = $('#search').val();

            table.reloadData('pubList', {
                where: {
                    'category': '${category}',
                    'search': searchStr,
                },
                page: {
                    curr: 1
                },
            });
        });
    });
</script>
</html>