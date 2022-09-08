<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 页面侧栏 --%>
<div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
        <!-- 左侧导航区域 -->
        <ul class="layui-nav layui-nav-tree" lay-filter="test" style="text-align: center;">
            <li class="layui-nav-item"><a href="<c:url value="/"/>">首页</a></li>
            <security:authorize access="!hasAnyRole('ADMIN')">
                <li class="layui-nav-item"><a href="<c:url value="/publication"/>">期刊</a></li>
            </security:authorize>
            <li class="layui-nav-item"><a href="<c:url value="/user/contribution"/>">投稿</a></li>
            <security:authorize access="hasAnyRole('EDITOR')">
                <li class="layui-nav-item"><a href="<c:url value="/editor/examine"/>">审核</a></li>
            </security:authorize>
            <security:authorize access="hasAnyRole('ADMIN')">
                <li class="layui-nav-item"><a href="<c:url value="/admin/manage/user"/>">用户管理</a></li>
                <li class="layui-nav-item"><a href="<c:url value="/admin/manage/publication"/>">期刊管理</a></li>
            </security:authorize>
        </ul>
    </div>
</div>