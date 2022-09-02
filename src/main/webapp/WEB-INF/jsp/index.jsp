<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common-head.jsp" %>
    <title>首页 - 在线投稿网站</title>
</head>
<body>

<div class="layui-layout layui-layout-admin">
    <%@ include file="common-body-layui-header.jsp" %>

    <%@ include file="common-body-layui-side.jsp" %>

    <div class="layui-body" style="padding: 30px; min-width: 1000px;">
        <!-- 内容主体区域 -->

        <div class="layui-row">
            <div class="layui-col-md9">
                <fieldset class="layui-elem-field">
                    <legend style="font-weight: bold;">网站简介</legend>
                    <div class="layui-field-box" style="min-height: 30px;line-height: 30px;font-size: 16px;">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在线投稿中心是一个自助投稿平台，方便各界投稿爱好者，可以24
                        小时自助投稿。在线投稿中心编辑人员对于稿件根据作者的意向投递到的相关刊物进行审核，负责从作者注册会员一直到出刊期间的所
                        有问题的沟通与解决。完美地解决了作者投稿到杂志社刊发的不易。同时也避免了错发在假刊等。
                        <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在线投稿中心是基于服务社会大众而服务的产生的，与多家出版社
                        均有合作，学科齐全，先后编辑出版了上千部学术著作，覆盖了医学、护理、教育、管理、经济、艺术、法律、历史、政治、传媒、计
                        算机、电子信息、化工等几百个专业。
                    </div>
                </fieldset>
            </div>
            <%--<div class="layui-col-md3">
                你的内容 3/12
            </div>--%>
        </div>

        <div class="layui-row">
            <div class="layui-col-md9">
                <fieldset class="layui-elem-field">
                    <legend style="font-weight: bold;">投稿须知</legend>
                    <div class="layui-field-box" style="min-height: 30px;line-height: 30px;font-size: 16px;">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.收稿内容要求：立意新颖，观点明确，内容充实，论证严密，语
                        言精炼，资料可靠，能及时反映所研究领域的最新成果。本刊尤为欢迎有新观点、新方法、新视角的稿件和专家稿件。
                        <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.格式必备与顺序：标题、作者、作者单位、摘要、关键词、正文
                        、注释或参考文献。
                        <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.请在来稿末尾附上作者详细通讯地址。包括：收件人所在地的省
                        、市、区、街道名称、邮政编码、联系电话、电子信箱、代收人的姓名以及本人要求等，务必准确。论文有图表的，请保证图片和表格
                        的清晰，能和文字对应。
                        <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.本刊实行无纸化办公，来稿一律通过电子邮件（WORD文档附件
                        ）或QQ发送，严禁抄袭，文责自负，来稿必复，来稿不退，10日未见通知可自行处理。
                        <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.本刊来稿直接由编辑人员审阅，疑难重点稿件送交相关专家审阅
                        ，本刊坚持“公平、公正、公开、客观”的审稿原则，实行“三审三校”制度。
                        <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.来稿一经采用，平台将发出《用稿通知单》，出刊迅速，刊物精
                        美，稿件确认刊载后，赠送当期杂志1册。
                        <br>
                    </div>
                </fieldset>
            </div>
            <%--<div class="layui-col-md3">
                你的内容 3/12
            </div>--%>
        </div>
    </div>

    <%@ include file="common-body-layui-script.jsp" %>
</body>
</html>