<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            订单管理
            <small>订单表单</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">发票管理</a></li>
            <li class="active">发票表单</li>
        </ol>
    </section>
    <!-- 内容头部 /-->
<%--<input type="hidden" name="invoiceId" value="${invoice.invoiceId}">--%>
    <%--name="scNo",,name里的属性值必须和实体类的属性一直才能给响应的属性赋值--%>
    <!-- 正文区域 -->

    <section class="content">
        <form id="editForm" action="${ctx}/cargo/invoice/save.do" method="post">
        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">新建发票</div>


                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">发票编码</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="发票编码" name="blNo" value="${invoice.blNo}">
                    </div>
                    <div class="col-md-2 title">贸易条款</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="贸易条款" name="tradeTerms" value="${invoice.tradeTerms}">
                    </div>
                </div>

        </div>
        <!--订单信息/-->

        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="button" onclick='document.getElementById("editForm").submit()' class="btn bg-maroon">保存</button>
            <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
        </div>
        <!--工具栏/-->



        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">委托单列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">
                    <!--数据列表-->
                    <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <th class="" style="padding-right:0px;"></th>
                            <td class="tableHeader">序号</td>
                            <td class="tableHeader">海运/空运</td>
                            <td class="tableHeader">货主</td>
                            <td class="tableHeader">正本通知人</td>
                            <td class="tableHeader">装运港</td>
                            <td class="tableHeader">转船港</td>
                            <td class="tableHeader">卸货港</td>
                            <td class="tableHeader">复核人</td>
                            <td class="tableHeader">状态</td>
                        </tr>
                        </thead>
                        <tbody class="tableBody">
                        ${links }
                        <c:forEach items="${page.list}" var="o" varStatus="status">
                            <c:if test="${o.state==1}">
                            <tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'">
                                <td><input type="radio" name="invoiceId" value="${o.shippingOrderId}"/></td>
                                <td>${status.index+1}</td>
                                <td>${o.orderType}</td>
                                <td>${o.shipper}</td>
                                <td>${o.notifyParty}</td>
                                <td>${o.portOfLoading}</td>
                                <td>${o.portOfTrans}</td>
                                <td>${o.portOfDischarge}</td>
                                <td>${o.checkBy}</td>
                                <td><c:if test="${o.state==0}">草稿</c:if>
                                    <c:if test="${o.state==1}"><font color="green">已上报</font></c:if>
                                    <c:if test="${o.state==2}"><font color="red">已装箱</font></c:if>
                                </td>
                                <td>
                                    <a href="${ctx }/cargo/invoice/toViewShow.do?id=${o.shippingOrderId}">[查看详细]</a>
                                </td>
                            </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                    <!--数据列表/-->
                    <!--工具栏/-->
                </div>
                <!-- 数据表格 /-->
            </div>
            <!-- /.box-body -->

            <!-- .box-footer-->
            <div class="box-footer">
                <jsp:include page="../../common/page.jsp">
                    <jsp:param value="${ctx}/cargo/invoice/toAdd.do?" name="pageUrl"/>
                </jsp:include>
            </div>
            <!-- /.box-footer-->
        </div>
        </form>
    </section>

</div>
<!-- 内容区域 /-->
</body>
<script src="../../plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="../../plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="../../css/style.css">
<script>
    $('#createTime').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#loadingDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#limitDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });

</script>

</html>