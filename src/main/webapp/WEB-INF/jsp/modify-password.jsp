<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common-head.jsp" %>
    <link rel="stylesheet" href="layui/css/layui.css" media="all">
    <title>修改</title>
</head>
<body>
<script src="/layui/layui.js"></script>
<div class="layui-layout layui-layout-admin">
    <%@ include file="common-body-layui-header.jsp" %>
    <%@ include file="common-body-layui-side.jsp" %>
    <div class="layui-body" style="padding: 30px; min-width: 1000px;">
        <div class="layui-col-md2 layui-col-md-offset2 login" style="width: 708px;">
            <div class="layui-form-item">
                <label class="layui-form-label">用户名：</label>
                <div class="layui-input-block">
                    <input type="text" name="username" lay-verify="required|username" class="layui-input"
                           placeholder="用户名">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">原始密码：</label>
                <div class="layui-input-block">
                    <input type="password" name="password3" required lay-verify="required|password" class="layui-input"
                           placeholder="原始密码">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">修改密码：</label>
                <div class="layui-input-block">
                    <input type="password" name="password4" required lay-verify="required|password" class="layui-input"
                           placeholder="修改密码">
                </div>
            </div>

            <div class="layui-form-item" style="display:block;margin:0 280px 0 280px;">
                <button class="layui-btn" lay-submit>确认修改</button>
            </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
