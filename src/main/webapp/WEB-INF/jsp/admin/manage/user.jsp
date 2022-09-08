<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../common-head.jsp" %>
    <title>用户管理 - 在线投稿网站</title>
</head>
<body>

<div class="layui-layout layui-layout-admin">
    <%@ include file="../../common-body-layui-header.jsp" %>

    <%@ include file="../../common-body-layui-side.jsp" %>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief" style="padding: 50px;">
            <button id="addUser" type="button" class="layui-btn layui-btn-normal">
                <i class="layui-icon">&#xe654;</i>新增用户
            </button>

            <div class="layui-tab-content">
                <table class="layui-hide" id="userList" lay-filter="userTable"></table>
            </div>
        </div>

    </div>
</div>

<%@ include file="../../common-body-layui-script.jsp" %>
</body>

<script type="text/html" id="userBar">
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-xs" lay-event="editPassword">修改密码</a>
    <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete">删除</a>
</script>

<script>
    // 使用 layui table 插件
    layui.use(['table', 'layer', 'form', 'jquery'], function () {
        // 实例化一个表格对象
        const $ = layui.jquery;
        const table = layui.table;
        const layer = layui.layer;
        const form = layui.form;

        /*
         * 弹出添加用户弹窗
         */
        $("#addUser").click(function () {
            layer.open({
                title: "添加用户",
                type: 1,
                area: ['66%', '90%'],
                content:
                    `
                <div style="padding: 50px;">
                    <form lay-filter="newUserForm" class="layui-form">

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
                                <input type="password" name="password" lay-verify="required|password"
                                       class="layui-input" placeholder="请输入密码"/>
                            </div>
                            <div class="layui-form-mid layui-word-aux">请填写6到12位密码</div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">角 色：</label>
                            <div class="layui-input-block">
                                <input type="radio" name="userRole" value="ADMIN" title="管理员"/>
                                <input type="radio" name="userRole" value="EDITOR" title="编辑"/>
                                <input type="radio" name="userRole" value="USER" title="会员"/>
                            </div>
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
                    </form>
                </div>
                `,
                success: function (index) {
                    form.render();
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
                        ],
                    });
                },
                btn: ['确定'],
                yes: function (index) {
                    // 按钮【确定】的回调
                    form.submit('newUserForm', function (data) {
                        const formData = data.field;
                        const newUserUrl = '<c:url value="/admin/manage/user/add"/>';

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
                                table.reload("userList");
                            }
                        });

                        return false;
                    });
                }
            });
        });

        // 开始渲染表格
        const tableOptions = {
            elem: '#userList', // 选择 table 标签的 id
            url: '<c:url value="/admin/manage/user/list"/>', // 后端接口
            method: 'get', // http 请求方法
            cellMinWidth: 80, // 列最小宽度
            text: "加载数据出错", // 数据错误时提示
            limits: [10, 20, 50], // 分页限制
            page: true, // 开启分页
            cols: [[ // 表头
                {field: 'username', title: '用户名', fixed: 'left'},
                {field: 'role', title: '安全角色', sort: true},
                {field: 'name', title: '姓名'},
                {field: 'gender', title: '性别'},
                {field: 'phone', title: '电话号码'},
                {fixed: 'right', title: '操作', width: 250, toolbar: '#userBar'}
            ]],
        };

        table.render(tableOptions);

        table.on('tool(userTable)', function (obj) {

            if (obj.event === 'edit') {

                layer.open({
                    title: '修改用户' + obj.data.username + '的信息',
                    type: 1,
                    area: ['66%', '90%'],
                    content:
                        `
                        <div style="padding: 50px;">
                                <form class="layui-form" lay-filter="infoForm">
                                    <div class="layui-form-item">
                                        <label class="layui-form-label">姓 名：</label>
                                        <div class="layui-input-block">
                                            <input type="text" name="name" value="` + obj.data.name + `"
                                                   class="layui-input"
                                                   required lay-verify="required"/>
                                        </div>
                                    </div>
                                    <div class="layui-form-item">
                                        <label class="layui-form-label">性 别：</label>
                                        <div class="layui-input-block">
                                            <input type="radio" name="gender" value="男" id="男" title="男"/>
                                            <input type="radio" name="gender" value="女" id="女" title="女"/>
                                        </div>
                                    </div>

                                    <div class="layui-form-item">
                                        <label class="layui-form-label">年 龄：</label>
                                        <div class="layui-input-inline">
                                            <input type="number" min="0" class="layui-input" max="200" value="` + obj.data.age + `"
                                                   name="age"/>
                                        </div>

                                    </div>

                                    <div class="layui-form-item">
                                        <label class="layui-form-label">学 历：</label>
                                        <div class="layui-input-block">
                                            <input type="radio" name="educationalBackground" value="本科" id="本科"
                                                   title="本科"/>
                                            <input type="radio" name="educationalBackground" value="高职" id="高职"
                                                   title="高职"/>
                                            <input type="radio" name="educationalBackground" value="硕士" id="硕士"
                                                   title="硕士"/>
                                            <input type="radio" name="educationalBackground" value="博士" id="博士"
                                                   title="博士"/>
                                            <input type="radio" name="educationalBackground" value="其他" id="其他"
                                                   title="其他"/>
                                        </div>
                                    </div>

                                    <div class="layui-form-item">
                                        <label class="layui-form-label">毕业学校：</label>
                                        <div class="layui-input-block">
                                            <input type="text" placeholder="请输入毕业学校" name="graduatedSchool" value="` + obj.data.graduatedSchool + `"
                                                   class="layui-input"/>
                                        </div>
                                    </div>

                                    <div style="height: 55px;">
                                        <div class="layui-inline">
                                            <label class="layui-form-label">邮 箱：</label>
                                            <div class="layui-input-inline">
                                                <input type="text" name="email" lay-verify="email" value="` + obj.data.email + `"
                                                       autocomplete="off" class="layui-input"/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="layui-form-item">
                                        <div class="layui-inline">
                                            <label class="layui-form-label">联系方式：</label>
                                            <div class="layui-input-inline">
                                                <input type="tel" name="phone" required lay-verify="required|phone" value="` + obj.data.phone + `"
                                                       autocomplete="off" class="layui-input"/>
                                            </div>
                                        </div>
                                    </div>


                                    <div class="layui-form-item">
                                        <label class="layui-form-label">通讯地址：</label>
                                        <div class="layui-input-block">
                                            <input type="text" placeholder="通讯地址" name="contactAddress" value="` + obj.data.contactAddress + `"
                                                   class="layui-input"/>
                                        </div>
                                    </div>

                                    <div class="layui-form-item">
                                        <label class="layui-form-label">邮政编码：</label>
                                        <div class="layui-input-block">
                                            <input type="text" name="zipCode" placeholder="请输入邮政编码" value="` + obj.data.zipCode + `"
                                                   class="layui-input"/>
                                        </div>
                                    </div>
                                </form>
                        </div>
                        `,
                    btn: ['确定'],
                    yes: function (index) {
                        // 按钮【确定】的回调
                        form.submit('infoForm', function (data) {
                            const formData = data.field;
                            const saveUrl = '<c:url value="/admin/manage/user/edit/info/"/>' + obj.data.id;

                            $.ajax({
                                url: saveUrl,
                                data: formData,
                                type: 'post',
                                async: false,
                                success: function (res) {
                                    layer.msg(res.msg);
                                    layer.close(index); //关闭弹出层
                                    //刷新表格
                                    table.reload("userList");
                                }
                            });
                        });
                    },
                    success: function (index) {
                        if (obj.data.gender === '男') {
                            $('#男').attr('checked', 'checked');
                        } else if (obj.data.gender === '女') {
                            $('#女').attr('checked', 'checked');
                        }

                        if (obj.data.educationalBackground === '本科') {
                            $('#本科').attr('checked', 'checked');
                        } else if (obj.data.educationalBackground === '高职') {
                            $('#高职').attr('checked', 'checked');
                        } else if (obj.data.educationalBackground === '硕士') {
                            $('#硕士').attr('checked', 'checked');
                        } else if (obj.data.educationalBackground === '博士') {
                            $('#博士').attr('checked', 'checked');
                        } else if (obj.data.educationalBackground === '其他') {
                            $('#其他').attr('checked', 'checked');
                        }

                        form.render(); // 弹出后渲染表单
                        form.verify({
                            phone: [
                                /^\d{11}$/
                                , "手机号必须为11位数字"
                            ]
                        });
                    },
                });

            } else if (obj.event === 'editPassword') {

                const title = '修改 ' + obj.data.username + ' 的密码';

                layer.open({
                    title: title,
                    type: 1,
                    area: ['60%', '38%'],
                    content:
                        `
                        <div style="padding: 35px;">
                            <form lay-filter="modifyPasswordForm" class="layui-form">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">新密码：</label>
                                    <div class="layui-input-inline">
                                        <input type="password" name="newPassword" lay-verify="required|password"
                                               class="layui-input" placeholder="请输入新密码"/>
                                    </div>
                                    <div class="layui-form-mid layui-word-aux">请填写6到12位密码</div>
                                </div>
                            </form>
                        </div>
                        `,
                    btn: ['确定'],
                    yes: function (index) {
                        // 按钮【确定】的回调
                        form.submit('modifyPasswordForm', function (data) {
                            const formData = data.field;
                            const editPasswordUrl = '<c:url value="/admin/manage/user/edit/password/"/>' + obj.data.id;

                            $.ajax({
                                url: editPasswordUrl,
                                data: formData,
                                type: 'post',
                                async: false,
                                success: function (res) {
                                    layer.msg(res.msg);
                                    layer.close(index); //关闭弹出层

                                }
                            });
                            return false;
                        });
                    },
                    success: function (index) {
                        form.render();
                        form.verify({
                            password: [
                                /^[\S]{6,12}$/
                                , '密码必须6到12位，且不能出现空格'
                            ]
                        });
                    },
                });

            } else if (obj.event === 'delete') {
                //弹出删除窗口,确认是否删除
                layer.confirm("是否删除?", {icon: 3, title: "提示"}, function (index) {
                    //调用AJAX删除后台数据--> 获取删除数据的ID
                    const delUrl = '<c:url value="/admin/manage/user/delete/"/>' + obj.data.id;

                    $.ajax({
                        url: delUrl,
                        data: {},
                        type: 'post',
                        async: false,
                        success: function (res) {
                            layer.msg(res.msg);
                            //关闭弹出层
                            layer.close(index);
                            //刷新表格
                            table.reload("userList");
                        },
                    });

                    return false;
                });

            }

            return false;
        });
    });
</script>
</html>
