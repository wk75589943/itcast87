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
            货运管理
            <small>发票运单</small>
        </h1>
        <ol class="breadcrumb"></ol>
    </section>

    <!-- 正文区域 -->
    <section class="content">
        <div class="panel panel-default">
            <div class="panel-heading">发票详情</div>
                    <%--<input type="hidden" name="invoiceId" value="${invoice.invoiceId}">--%>
                    <div class="row data-type" style="margin: 0px">
                        <div class="col-md-2 title">合同号</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${invoice.scNo}
                        </div>
                        <div class="col-md-2 title">发票编码</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${invoice.blNo}
                        </div>
                        <div class="col-md-2 title">贸易条款</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${invoice.tradeTerms}
                        </div>
                        <div class="col-md-2 title">创建人</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${invoice.createBy}
                        </div>
                        <div class="col-md-2 title">创建部门</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${invoice.createDept}
                        </div>
                        <div class="col-md-2 title">创建日期</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            <div class="input-group date">
                                <fmt:formatDate value="${invoice.createTime}" pattern="yyyy-MM-dd"/>
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

</html>