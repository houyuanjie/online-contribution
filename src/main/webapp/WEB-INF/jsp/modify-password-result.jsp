<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common-head.jsp" %>
    <title>${title} - 在线投稿网站</title>
</head>
<body>

<div class="layui-layout layui-layout-admin">
    <%@ include file="common-body-layui-header.jsp" %>

    <%@ include file="common-body-layui-side.jsp" %>

    <div class="layui-body">
        <!-- 内容主体区域 -->

        <div class="layui-row layui-col-space10">
            <div class="layui-col-md4">

            </div>
            <div class="layui-col-md4"
                 style="line-height: 58px;padding-top: 200px; color: #393D49;font-size: 28px;font-weight: 300;">
                ${msg}
            </div>
            <div class="layui-col-md4">

            </div>
        </div>
    </div>
</div>

<%@ include file="common-body-layui-script.jsp" %>
</body>
</html>

