<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../common-head.jsp" %>
    <title>投稿 - 在线投稿网站</title>
</head>
<body>

<div class="layui-layout layui-layout-admin">
    <%@ include file="../common-body-layui-header.jsp" %>

    <%@ include file="../common-body-layui-side.jsp" %>
    <%--刊物、论文题目、作者、、摘要、关键字、同时可以上传稿件--%>
    <div class="layui-body" style="padding: 30px; min-width: 1100px;">
        <!-- 内容主体区域 -->
        <fieldset class="layui-elem-field" style="padding-left: 50px; padding-right: 350px">
            <form class="layui-form" enctype="multipart/form-data" method="post"
                  action="<c:url value="/user/contribution/submit"/>">
                <div class="layui-form-item" style="padding-top: 30px">
                    <label class="layui-form-label">投往期刊</label>

                    <div class="layui-input-inline">
                        <select name="category" lay-filter="category" required>
                            <option value="">请选择期刊类型</option>
                            <option value="教科文艺">教科文艺</option>
                            <option value="经济管理">经济管理</option>
                            <option value="基础科学">基础科学</option>
                            <option value="社会科学">社会科学</option>
                            <option value="工程科技">工程科技</option>
                            <option value="信息科技">信息科技</option>
                            <option value="农业科技">农业科技</option>
                            <option value="医药卫生">医药卫生</option>
                            <option value="哲学政法">哲学政法</option>
                        </select>
                    </div>
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <select id="publication" name="publication" required>
                                <option value="">请先选择期刊类型</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">论文题目</label>
                    <div class="layui-input-block">
                        <input type="text" name="title" required lay-verify="required" placeholder="请输入论文题目"
                               autocomplete="off" class="layui-input"/>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">作者</label>
                    <div class="layui-input-block">
                        <input type="text" name="author" required lay-verify="required" placeholder="请输入作者"
                               autocomplete="off" class="layui-input"/>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">单位</label>
                    <div class="layui-input-block">
                        <input type="text" name="organization" lay-verify="" placeholder="请输入单位"
                               autocomplete="off" class="layui-input"/>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">关键字</label>
                    <div class="layui-input-block">
                        <input type="text" name="keywords" required lay-verify="required" placeholder="请输入关键字"
                               autocomplete="off" class="layui-input"/>
                    </div>
                </div>

                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">摘要</label>
                    <div class="layui-input-block">
                        <textarea name="summary" placeholder="请输入摘要内容" class="layui-textarea"></textarea>
                    </div>
                </div>

                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">请上传文章</label>
                    <input type="file" name="file" class="layui-btn" id="btnUpload" required/>
                </div>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <input type="submit" class="layui-btn" lay-filter="formSubmit" value="立即提交"/>
                        <input type="reset" class="layui-btn layui-btn-primary" value="重置"/>
                    </div>
                </div>
            </form>
        </fieldset>

    </div>
</div>
<%@ include file="../common-body-layui-script.jsp" %>
</body>

<script type="text/javascript">
    layui.use(['form', 'upload'], function () {
        const $ = layui.$;
        const form = layui.form;

        // 选择
        form.on('select(category)', function (data) {
            $.post("<c:url value="/publication/list"/>", {
                'category': data.value,
                'page': null,
                'limit': null
            }, function (res) {
                let html = '<option value="">请选择期刊</option>';

                // 将选项的值和选项加入到选择
                $.each(res.data, function (index, item) {
                    html += '<option value="' + (item.name) + '">' + item.name + '</option>';
                });

                $("#publication").html(html);
                //重新渲染select标签中的内容
                form.render($('#publication'));
            });
        });
    });
</script>

</html>