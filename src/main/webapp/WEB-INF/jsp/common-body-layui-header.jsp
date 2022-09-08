<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%-- 页面顶栏 --%>
<div class="layui-header">
    <div class="layui-logo layui-hide-xs layui-bg-black">在线投稿</div>
    <!-- 头部区域（可配合layui 已有的水平导航） -->
    <div class="layui-nav layui-layout-left">
        <div style="margin-bottom: 10px;padding: 15px;line-height: 1.6;">
            <security:authorize access="isAuthenticated()">
                你好 <security:authentication property="principal.username"/> ,
            </security:authorize>
            欢迎来到在线投稿网站!
        </div>
    </div>
    <ul class="layui-nav layui-layout-right">
        <li class="layui-nav-item layui-hide layui-show-md-inline-block">
            <a href="javascript:">
                <img src="//tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"
                     class="layui-nav-img">
                <security:authorize access="isAuthenticated()">
                    <security:authentication property="principal.username"/>
                </security:authorize>
                <security:authorize access="!isAuthenticated()">
                    <dl class="layui-nav-child">
                        <dd><a href="<c:url value="/login"/>">登录</a></dd>
                    </dl>
                </security:authorize>
            </a>
            <security:authorize access="isAuthenticated()">
                <dl class="layui-nav-child">
                    <dd><a href="<c:url value="/user/myManuscripts"/>">我的稿件</a></dd>
                    <dd><a href="<c:url value="/logout"/>">退出</a></dd>
                </dl>
            </security:authorize>
        </li>
    </ul>
</div>
