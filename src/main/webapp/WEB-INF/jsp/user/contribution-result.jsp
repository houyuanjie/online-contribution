<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../common-head.jsp" %>
    <title>${title} - 在线投稿网站</title>
</head>
<body>

<div class="layui-layout layui-layout-admin">
    <%@ include file="../common-body-layui-header.jsp" %>

    <%@ include file="../common-body-layui-side.jsp" %>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        ${msg}

        <%-- TODO: 添加样式 --%>

        <c:if test="${success}">
            <%-- 投稿成功时显示 --%>

        </c:if>
        <c:if test="${!success}">
            <%-- 投稿失败时显示 --%>

        </c:if>
    </div>
</div>

<%@ include file="../common-body-layui-script.jsp" %>
</body>
</html>
