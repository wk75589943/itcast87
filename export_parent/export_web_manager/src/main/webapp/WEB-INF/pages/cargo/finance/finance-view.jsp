<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
    <script src="${ctx}/plugins/jQuery/jquery-2.2.3.min.js"></script>
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            财务管理
            <small>财务运单</small>
        </h1>
        <ol class="breadcrumb"></ol>
    </section>

    <!-- 正文区域 -->
    <section class="content">
        <div class="panel panel-default">
            <div class="panel-heading">财务详情</div>
                    <%--<input type="hidden" name="invoiceId" value="${invoice.invoiceId}">--%>
                    <div class="row data-type" style="margin: 0px">
                        <div class="col-md-2 title">制单日期</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            <div class="input-group date">
                                <fmt:formatDate value="${finance.inputDate}" pattern="yyyy-MM-dd"/>
                            </div>
                        </div>
                        <div class="col-md-2 title">制单人</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${finance.inputBy}
                        </div>
                        <div class="col-md-2 title">状态</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            <td><c:if test="${finance.state==0}">草稿</c:if>
                                <c:if test="${finance.state==1}"><font color="green">已上报</font></c:if>
                                <c:if test="${finance.state==2}"><font color="red">已装箱</font></c:if>
                            </td>
                        </div>
                        <div class="col-md-2 title">创建人</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${finance.createBy}
                        </div>
                        <div class="col-md-2 title">创建部门</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${finance.createDept}
                        </div>
                        <div class="col-md-2 title">创建日期</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            <div class="input-group date">
                                <fmt:formatDate value="${finance.createTime}" pattern="yyyy-MM-dd"/>
                            </div>
                        </div>
                </div>
        </div>
        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
        </div>
    </section>
</div>
<!-- 内容区域 /-->
</body>
</body>
<script src="../../plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="../../plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="../../css/style.css">
<script>
    $('#inputDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#limitDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#createTime').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
</script>
</html>