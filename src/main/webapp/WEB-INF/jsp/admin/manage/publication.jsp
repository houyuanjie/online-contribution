<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../common-head.jsp" %>
    <title>期刊管理 - 在线投稿网站</title>
</head>
<body>

<div class="layui-layout layui-layout-admin">
    <%@ include file="../../common-body-layui-header.jsp" %>

    <%@ include file="../../common-body-layui-side.jsp" %>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div class="layui-tab layui-tab-brief" style="padding: 50px;">
            <div class="layui-inline">
                <button id="addPublication" type="button" class="layui-btn layui-btn-normal">
                    <i class="layui-icon">&#xe654;</i>新增期刊
                </button>

                <div class="layui-input-inline" style="padding-left: 30px;">
                    <input type="text" id="search" class="layui-input" placeholder="搜索刊物..."/>
                </div>
                <button type="button" id="searchBtn" class="layui-btn layui-btn-sm">
                    <i class="layui-icon">&#xe615;</i>
                </button>
            </div>

            <ul class="layui-tab-title">
                <li class="${ "_ALL".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/admin/manage/publication"><c:param name="category" value="_ALL"/></c:url>'>所有期刊</a>
                </li>
                <li class="${ "教科文艺".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/admin/manage/publication"><c:param name="category" value="教科文艺"/></c:url>'>教科文艺</a>
                </li>
                <li class="${ "经济管理".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/admin/manage/publication"><c:param name="category" value="经济管理"/></c:url>'>经济管理</a>
                </li>
                <li class="${ "基础科学".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/admin/manage/publication"><c:param name="category" value="基础科学"/></c:url>'>基础科学</a>
                </li>
                <li class="${ "社会科学".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/admin/manage/publication"><c:param name="category" value="社会科学"/></c:url>'>社会科学</a>
                </li>
                <li class="${ "工程科技".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/admin/manage/publication"><c:param name="category" value="工程科技"/></c:url>'>工程科技</a>
                </li>
                <li class="${ "信息科技".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/admin/manage/publication"><c:param name="category" value="信息科技"/></c:url>'>信息科技</a>
                </li>
                <li class="${ "农业科技".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/admin/manage/publication"><c:param name="category" value="农业科技"/></c:url>'>农业科技</a>
                </li>
                <li class="${ "医药卫生".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/admin/manage/publication"><c:param name="category" value="医药卫生"/></c:url>'>医药卫生</a>
                </li>
                <li class="${ "哲学政法".equals(category) ? "layui-this" : "" }"><a
                        href='<c:url value="/admin/manage/publication"><c:param name="category" value="哲学政法"/></c:url>'>哲学政法</a>
                </li>
            </ul>

            <div class="layui-tab-content">
                <table class="layui-hide" id="pubList" lay-filter="pubTable"></table>
            </div>
        </div>
    </div>
</div>

<%@ include file="../../common-body-layui-script.jsp" %>
</body>

<script type="text/html" id="pubBar">
    <a class="layui-btn layui-btn-xs" lay-event="content">查看详情</a>
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="setCoverPicture">设置封面图片</a>
    <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete">删除</a>
</script>

<script>
    layui.use(['jquery', 'table', 'layer', 'form'], function () {
        const $ = layui.jquery;
        const table = layui.table;
        const layer = layui.layer;
        const form = layui.form;

        /*
         * 弹出添加期刊弹窗
         */
        $("#addPublication").click(function () {
            layer.open({
                title: "添加期刊",
                type: 1,
                area: ['66%', '90%'],
                content:
                    `
                    <div style="padding: 50px;">
                        <form class="layui-form" lay-filter="addPubForm">
                            <div class="layui-form-item">
                                <label class="layui-form-label">刊物名称: </label>
                                <div class="layui-input-inline">
                                    <input type="text" name="name" class="layui-input" required lay-verify="required"/>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">国际刊号: </label>
                                <div class="layui-input-inline">
                                    <input type="text" name="issn" class="layui-input" required lay-verify="required|issn"/>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">刊物类型: </label>
                                <div class="layui-input-block">
                                    <input type="radio" name="category" value="教科文艺" title="教科文艺" checked/>
                                    <input type="radio" name="category" value="经济管理" title="经济管理" />
                                    <input type="radio" name="category" value="基础科学" title="基础科学" />
                                    <input type="radio" name="category" value="社会科学" title="社会科学" />
                                    <input type="radio" name="category" value="工程科技" title="工程科技" />
                                    <input type="radio" name="category" value="信息科技" title="信息科技" />
                                    <input type="radio" name="category" value="农业科技" title="农业科技" />
                                    <input type="radio" name="category" value="医药卫生" title="医药卫生" />
                                    <input type="radio" name="category" value="哲学政法" title="哲学政法" />
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">主办单位: </label>
                                <div class="layui-input-inline">
                                    <input type="text" name="organizer" class="layui-input"/>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">刊期: </label>
                                <div class="layui-input-inline">
                                    <input type="text" name="publicationFrequency" class="layui-input" />
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">语种: </label>
                                <div class="layui-input-inline">
                                    <input type="text" name="language" class="layui-input" />
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">简介: </label>
                                <div class="layui-input-block">
                                    <textarea name="info" class="layui-textarea"></textarea>
                                </div>
                            </div>
                        </form>
                    </div>
                    `,
                success: function (index) {
                    form.render();
                    //表单验证功能
                    form.verify({
                        issn: [
                            /^(ISSN|eISSN) [\S]{4}\-[\S]{4}$/
                            , '国际刊号格式不正确,例：ISSN 1002-6487'
                        ]
                    });
                },
                btn: ['确定'],
                yes: function (index) {
                    // 按钮【确定】的回调
                    form.submit('addPubForm', function (data) {
                        const formData = data.field;
                        const newUserUrl = '<c:url value="/admin/manage/publication/add"/>';

                        $.ajax({
                            url: newUserUrl,
                            data: JSON.stringify(formData),
                            headers: {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json'
                            },
                            type: 'post',
                            async: false,
                            success: function (res) {
                                layer.msg(res.msg);
                                layer.close(index); //关闭弹出层
                                //刷新表格
                                table.reload("pubList");
                            }
                        });

                        return false;
                    });
                }
            });
        });

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
                {field: 'name', title: '刊物名称'},
                {field: 'category', title: '类别', sort: true},
                {field: 'language', title: '语种'},
                {field: 'organizer', title: '主办单位', width: 210},
                {field: 'publicationFrequency', title: '刊期', width: 95, sort: true},
                {fixed: 'right', title: '操作', width: 300, toolbar: '#pubBar'}
            ]]
        });

        // 点击 pubBar 的事件
        table.on('tool(pubTable)', function (obj) {
            const publication = obj.data;
            const publicationId = obj.data.id;
            const event = obj.event;

            if (event === 'edit') {

                const title = '修改 ' + obj.data.name + ' 的信息';

                layer.open({
                    title: title,
                    type: 1,
                    area: ['80%', '90%'],
                    content:
                        `
                        <div style="padding: 50px;">
                            <form class="layui-form" lay-filter="editPubForm">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">刊物名称: </label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="name" class="layui-input" required lay-verify="required" value="` + publication.name + `" />
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">国际刊号: </label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="issn" class="layui-input" required lay-verify="required|issn" value="` + publication.issn + `" />
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">刊物类型: </label>
                                    <div class="layui-input-block">
                                        <input type="radio" name="category" value="教科文艺" title="教科文艺" />
                                        <input type="radio" name="category" value="经济管理" title="经济管理" />
                                        <input type="radio" name="category" value="基础科学" title="基础科学" />
                                        <input type="radio" name="category" value="社会科学" title="社会科学" />
                                        <input type="radio" name="category" value="工程科技" title="工程科技" />
                                        <input type="radio" name="category" value="信息科技" title="信息科技" />
                                        <input type="radio" name="category" value="农业科技" title="农业科技" />
                                        <input type="radio" name="category" value="医药卫生" title="医药卫生" />
                                        <input type="radio" name="category" value="哲学政法" title="哲学政法" />
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">主办单位: </label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="organizer" class="layui-input" value="` + publication.organizer + `" />
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">刊期: </label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="publicationFrequency" class="layui-input" value="` + publication.publicationFrequency + `" />
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">语种: </label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="language" class="layui-input" value="` + publication.language + `" />
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">简介: </label>
                                    <div class="layui-input-block">
                                        <textarea name="info" class="layui-textarea">` + publication.info + `</textarea>
                                    </div>
                                </div>
                            </form>
                        </div>
                        `,
                    btn: ['确定'],
                    yes: function (index) {
                        form.submit('editPubForm', function (data) {
                            const formData = data.field;
                            const editPubUrl = '<c:url value="/admin/manage/publication/edit/info/" />' + publicationId;

                            $.ajax({
                                url: editPubUrl,
                                data: formData,
                                type: 'post',
                                async: false,
                                success: function (res) {
                                    layer.msg(res.msg);
                                    layer.close(index);
                                    table.reload("pubList");
                                },
                            });
                        });
                    },
                    success: function () {
                        $('input[value=' + publication.category + ']:radio').attr('checked', true);

                        form.render();
                        //表单验证功能
                        form.verify({
                            issn: [
                                /^(ISSN|eISSN) [\S]{4}\-[\S]{4}$/
                                , '国际刊号格式不正确,例：ISSN 1002-6487'
                            ]
                        });
                    },
                });

            } else if (event === 'delete') {

                layer.confirm(
                    '删除?',
                    {icon: 3, title: "提示"},
                    function (index) {
                        const delUrl = '<c:url value="/admin/manage/publication/delete/" />' + publicationId;

                        $.ajax({
                            url: delUrl,
                            data: {},
                            type: 'post',
                            async: false,
                            success: function (res) {
                                layer.msg(res.msg);
                                layer.close(index);
                                table.reload("pubList");
                            },
                        });
                    }
                );

            } else if (event === 'setCoverPicture') {

                const title = '为 ' + publication.name + ' 设置图片';

                layer.open({
                    title: title,
                    type: 1,
                    area: ['29%', '34%'],
                    content:
                        `
                        <form id="setCoverPictureForm" class="layui-form" enctype="multipart/form-data" method="post" lay-filter="setCoverPictureForm">
                            <div class="layui-form-item layui-form-text" style="padding-top: 10px;">
                                <label class="layui-form-label">请选择图片</label>
                                <input type="file" accept=".jpg" name="coverPicture" class="layui-btn" required/>
                            </div>
                        </form>
                        `,
                    btn: ['确定'],
                    yes: function (index) {
                        form.submit('setCoverPictureForm', function () {
                            const formData = new FormData($('#setCoverPictureForm')[0]);
                            const cpUrl = '<c:url value="/admin/manage/publication/set/cover-picture/"/>' + publicationId;

                            $.ajax({
                                url: cpUrl,
                                data: formData,
                                type: "POST",
                                enctype: 'multipart/form-data',
                                processData: false,
                                contentType: false,
                                cache: false,
                                async: false,
                                success: function (res) {
                                    layer.msg(res.msg);
                                    layer.close(index);
                                    table.reload("pubList");
                                },
                            });
                        });
                    },
                    success: function () {
                        form.render();
                    },
                });

            } else if (event === 'content') {
                window.open('<c:url value="/admin/manage/publication/content/id/"/>' + publicationId);
            }

            return false;
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
