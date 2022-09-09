<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common-head.jsp" %>
    <title>登录</title>
</head>
<body>

<div class="layui-layout layui-layout-admin">
    <%@ include file="common-body-layui-header.jsp" %>

    <%@ include file="common-body-layui-side.jsp" %>
    <div class="layui-body" style="padding: 30px; min-width: 1000px;">
        <!-- use param.error assuming FormLoginConfigurer#failureUrl contains the query parameter error -->
        <c:if test="${param.error != null}">
            <div>
                登录失败
                <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                    原因: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                </c:if>
            </div>
        </c:if>
        <!-- the configured LogoutConfigurer#logoutSuccessUrl is /login?logout and contains the query param logout -->
        <c:if test="${param.logout != null}">
            <div>
                您已登出
            </div>
        </c:if>
        <div class="layui-fluid">
            <div class="layui-row">
                <div class="layui-col-md2 layui-col-md-offset2 login" style="width: 708px;">
                    <div class="layui-tab layui-tab-brief">
                        <ul class="layui-tab-title">
                            <li class="layui-this">登录</li>
                            <li>注册</li>
                        </ul>

                        <%------------------------------下面是登录-------------------------------%>


                        <div class="layui-tab-content">
                            <div class="layui-tab-item layui-show">
                                <c:url value="/login" var="loginProcessingUrl"/>
                                <form action="${loginProcessingUrl}" method="post" class="layui-form layui-form-pane">
                                    <div class="layui-form-item">
                                        <label class="layui-form-label"><i
                                                class="layui-icon layui-icon-user"></i></label>
                                        <div class="layui-input-block">
                                            <input type="text" id="username" name="username"
                                                   lay-verify="required|username"
                                                   class="layui-input" placeholder="用户名">
                                        </div>
                                    </div>
                                    <div class="layui-form-item">
                                        <label class="layui-form-label"><i
                                                class="layui-icon layui-icon-password"></i></label>
                                        <div class="layui-input-block">
                                            <input type="password" id="password" name="password" required
                                                   lay-verify="required|password" class="layui-input"
                                                   placeholder="密码"/>
                                        </div>
                                    </div>

                                    <div class="layui-form-item" style="display:block;margin:0 280px 0 280px;">
                                        <button type="submit" class="layui-btn" lay-submit>登录</button>
                                        <span style="margin-left: 20px"><a
                                                href="<c:url value="/modify-password"/>">修改密码</a></span>
                                    </div>
                                </form>
                            </div>

                            <%------------------------------下面是注册-------------------------------%>

                            <div class="layui-tab-item">
                                <form action="<c:url value="/register"/>" class="layui-form layui-form-pane"
                                      method="post">
                                    <form class="layui-form">

                                        <div class="layui-form-item">
                                            <label class="layui-form-label">用 户 名：</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="username" required
                                                       lay-verify="required|username" class="layui-input"
                                                       placeholder="请输入用户名"/>
                                            </div>
                                        </div>

                                        <div class="layui-form-item">
                                            <label class="layui-form-label">密 码：</label>
                                            <div class="layui-input-inline">
                                                <input type="password" id="passwordReg" name="password" lay-verify="required|password"
                                                       class="layui-input" placeholder="请输入密码"/>
                                            </div>
                                            <div class="layui-form-mid layui-word-aux">请填写6到12位密码</div>
                                        </div>

                                        <div class="layui-form-item">
                                            <label class="layui-form-label">确认密码：</label>
                                            <div class="layui-input-inline">
                                                <input type="password" lay-verify="required|confirmPass"
                                                       class="layui-input" placeholder="请再次输入密码"/>
                                            </div>
                                            <div class="layui-form-mid layui-word-aux">请填写6到12位密码</div>
                                        </div>

                                        <div class="layui-form-item">
                                            <label class="layui-form-label">姓 名：</label>
                                            <div class="layui-input-block">
                                                <input type="text" name="name" placeholder="请输入姓名"
                                                       class="layui-input"
                                                       required lay-verify="required"/>
                                            </div>
                                        </div>

                                        <div class="layui-form-item">
                                            <label class="layui-form-label">性 别：</label>
                                            <div class="layui-input-block">
                                                <input type="radio" name="gender" value="男" title="男"/>
                                                <input type="radio" name="gender" value="女" title="女"/>
                                            </div>
                                        </div>

                                        <div class="layui-form-item">
                                            <label class="layui-form-label">年 龄：</label>
                                            <div class="layui-input-inline">
                                                <input name="age" type="number" min="0" class="layui-input" max="200"
                                                       name="age"/>
                                            </div>
                                        </div>

                                        <div class="layui-form-item">
                                            <label class="layui-form-label">学 历：</label>
                                            <div class="layui-input-block">
                                                <input type="radio" name="educationalBackground" value="本科"
                                                       title="本科"/>
                                                <input type="radio" name="educationalBackground" value="高职"
                                                       title="高职"/>
                                                <input type="radio" name="educationalBackground" value="硕士"
                                                       title="硕士"/>
                                                <input type="radio" name="educationalBackground" value="博士"
                                                       title="博士"/>
                                                <input type="radio" name="educationalBackground" value="其他"
                                                       title="其他"/>
                                            </div>
                                        </div>

                                        <div class="layui-form-item">
                                            <label class="layui-form-label">毕业学校：</label>
                                            <div class="layui-input-block">
                                                <input type="text" placeholder="请输入毕业学校" name="graduatedSchool"
                                                       class="layui-input"/>
                                            </div>
                                        </div>

                                        <div style="height: 55px;">
                                            <div class="layui-inline">
                                                <label class="layui-form-label">邮 箱：</label>
                                                <div class="layui-input-inline">
                                                    <input type="text" name="email" lay-verify="email"
                                                           autocomplete="off" class="layui-input"/>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="layui-form-item">
                                            <div class="layui-inline">
                                                <label class="layui-form-label">联系方式：</label>
                                                <div class="layui-input-inline">
                                                    <input type="tel" name="phone" required lay-verify="required|phone"
                                                           autocomplete="off" class="layui-input"/>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="layui-form-item">
                                            <label class="layui-form-label">通讯地址：</label>
                                            <div class="layui-input-block">
                                                <input type="text" placeholder="通讯地址" name="contactAddress"
                                                       class="layui-input"/>
                                            </div>
                                        </div>

                                        <div class="layui-form-item">
                                            <label class="layui-form-label">邮政编码：</label>
                                            <div class="layui-input-block">
                                                <input type="text" name="zipCode" placeholder="请输入邮政编码"
                                                       class="layui-input"/>
                                            </div>
                                        </div>

                                        <div class="layui-form-item" style="display:block;margin:0 320px 0 320px;">
                                            <button class="layui-btn" type="submit" lay-submit>注册</button>
                                        </div>
                                    </form>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        layui.use(["element", "form", "layer", "jquery"], function () {
            var element = layui.element;
            var form = layui.form;
            var layer = layui.layer;
            var $ = layui.jquery;

            //表单验证功能
            form.verify({
                username: function (value, item) { //value：表单的值、item：表单的DOM对象
                    if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                        return '用户名不能有特殊字符';
                    }
                    if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                        return '用户名首尾不能出现下划线\'_\'';
                    }
                    if (/^\d+\d+\d$/.test(value)) {
                        return '用户名不能全为数字';
                    }
                }
                , password: [
                    /^[\S]{6,12}$/
                    , '密码必须6到12位，且不能出现空格'
                ]
                , phone: [
                    /^\d{11}$/
                    , "手机号必须为11位数字"
                ], confirmPass: function (value, item) {
                    if ($('#passwordReg').val() !== value)
                        return '两次密码输入不一致！';
                }
            });
        })
    </script>
</body>
</html>
