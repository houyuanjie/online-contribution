<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 页面顶栏 --%>
<div class="layui-header">
    <div class="layui-logo layui-hide-xs layui-bg-black">在线投稿</div>
    <!-- 头部区域（可配合layui 已有的水平导航） -->
    <div class="layui-nav layui-layout-left">
        <div style="margin-bottom: 10px;padding: 15px;line-height: 1.6;">你好[用户名], 欢迎来到在线投稿网站！</div>
    </div>
    <ul class="layui-nav layui-layout-right">
        <li class="layui-nav-item layui-hide layui-show-md-inline-block">
            <a href="javascript:">
                <img src="//tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg"
                     class="layui-nav-img">
                [用户名]
            </a>
            <dl class="layui-nav-child">
                <dd><a href="">已投稿</a></dd>
                <dd><a href="">退出</a></dd>
            </dl>
        </li>
    </ul>
</div>
