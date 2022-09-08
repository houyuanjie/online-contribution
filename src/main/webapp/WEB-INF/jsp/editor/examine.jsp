<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../common-head.jsp" %>
    <title>审核 - 在线投稿网站</title>
</head>
<body>

<div class="layui-layout layui-layout-admin">
    <%@ include file="../common-body-layui-header.jsp" %>

    <%@ include file="../common-body-layui-side.jsp" %>

    <div class="layui-body" style="padding: 30px; min-width: 1000px;">
        <!-- 内容主体区域 -->
        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief" style="padding: 50px;">
            <div class="layui-tab-content">
                <table class="layui-hide" id="manuToExamList" lay-filter="manuToExamTable"></table>
            </div>
        </div>
    </div>

    <%@ include file="../common-body-layui-script.jsp" %>
</div>
</body>

<script type="text/html" id="examBar">
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="view">查看信息</a>
    <a class="layui-btn layui-btn-xs" lay-event="download">下载稿件</a>
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="approved">审核通过</a>
    <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="rejected">驳回审核</a>
</script>

<script>
    // 使用 layui table 插件
    layui.use(['table', 'layer'], function () {
        // 实例化一个表格对象
        const $ = layui.$;
        const table = layui.table;
        const layer = layui.layer;

        // 开始渲染表格
        const tableOptions = {
            elem: '#manuToExamList', // 选择 table 标签的 id
            url: '<c:url value="/editor/examine/list"/>', // 后端接口
            method: 'get', // http 请求方法
            cellMinWidth: 80, // 列最小宽度
            text: "加载数据出错", // 数据错误时提示
            limits: [10, 20, 50], // 分页限制
            page: true, // 开启分页
            cols: [[ // 表头
                {field: 'title', title: '题目', sort: true, fixed: 'left'},
                {field: 'author', title: '作者', sort: true},
                {field: 'organization', title: '单位', sort: true},
                {field: 'reviewStatus', title: '稿件审核状态'},
                {fixed: 'right', title: '操作', width: 313, toolbar: '#examBar'}
            ]],
        };

        table.render(tableOptions);

        // 点击 examBar 的事件
        table.on('tool(manuToExamTable)', function (obj) {
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
                        + '<p><b>投往期刊类型：</b>' + obj.data.publication.category + '</p>'
                        + '<p><b>投往期刊：</b>' + obj.data.publication.name + '</p>'
                        + '<p><b>单位：</b>' + obj.data.organization + '</p>'
                        + '<p><b>关键字：</b>' + obj.data.keywords + '</p>'
                        + '<p><b>摘要：</b>' + obj.data.summary + '</p> </div>'

                });
            } else if (obj.event === 'approved') {
                const url = '<c:url value="/editor/examine/manuscript/id/"/>' + obj.data.id;
                $.post(url, {
                    status: 'approved'
                }, function (res) {
                    layer.msg(res.msg);
                    table.reloadData('manuToExamList', {});
                });
            } else if (obj.event === 'rejected') {
                const url = '<c:url value="/editor/examine/manuscript/id/"/>' + obj.data.id;
                $.post(url, {
                    status: 'rejected'
                }, function (res) {
                    layer.msg(res.msg);
                    table.reloadData('manuToExamList', {});
                });
            }

            return false;
        });
    });
</script>
</html>