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
            <div class="panel-heading">委托单详情</div>
                    <%--<input type="hidden" name="invoiceId" value="${invoice.invoiceId}">--%>
                    <div class="row data-type" style="margin: 0px">
                        <div class="col-md-2 title">海运/空运</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.orderType}
                        </div>
                        <div class="col-md-2 title">货主</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.shipper}
                        </div>
                        <div class="col-md-2 title">提单抬头</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.consignee}
                        </div>
                        <div class="col-md-2 title">正本通知人</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.notifyParty}
                        </div>
                        <div class="col-md-2 title">信用证</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.lcNo}
                        </div>
                        <div class="col-md-2 title">装运港</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.portOfLoading}
                        </div>
                        <div class="col-md-2 title">转船港</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.portOfTrans}
                        </div>
                        <div class="col-md-2 title">卸货港</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.portOfDischarge}
                        </div>
                        <div class="col-md-2 title">装期</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            <div class="input-group date">
                                <fmt:formatDate value="${entrust.loadingDate}" pattern="yyyy-MM-dd"/>
                            </div>
                        </div>
                        <div class="col-md-2 title">效期</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            <div class="input-group date">
                                <fmt:formatDate value="${entrust.limitDate}" pattern="yyyy-MM-dd"/>
                            </div>
                        </div>
                        <div class="col-md-2 title">是否分批</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.isBatch}
                        </div>
                        <div class="col-md-2 title">是否转船</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.isTrans}
                        </div>
                        <div class="col-md-2 title">份数</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.copyNum}
                        </div>
                        <div class="col-md-2 title">扼要说明</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.remark}
                        </div>
                        <div class="col-md-2 title">运输要求</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.specialCondition}
                        </div>
                        <div class="col-md-2 title">运输说明</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.freight}
                        </div>
                        <div class="col-md-2 title">复核人</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.checkBy}
                        </div>
                        <div class="col-md-2 title">状态</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.state}
                        </div>
                        <div class="col-md-2 title">创始人</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.createBy}
                        </div>
                        <div class="col-md-2 title">创建部门</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            ${entrust.createDept}
                        </div>
                        <div class="col-md-2 title">创建日期</div>
                        <div class="col-md-4 data" style="line-height:34px">
                            <div class="input-group date">
                                <fmt:formatDate value="${entrust.createTime}" pattern="yyyy-MM-dd"/>
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
<script src="../../plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="../../plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="../../css/style.css">
<script>
    $('#loadingDate').datepicker({
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