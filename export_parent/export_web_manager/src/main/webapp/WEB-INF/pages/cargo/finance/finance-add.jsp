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
            财务管理
            <small>财务表单</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">财务管理</a></li>
            <li class="active">财务表单</li>
        </ol>
    </section>
    <!-- 内容头部 /-->
<%--<input type="hidden" name="invoiceId" value="${invoice.invoiceId}">--%>
    <%--name="scNo",,name里的属性值必须和实体类的属性一直才能给响应的属性赋值--%>
    <!-- 正文区域 -->

    <section class="content">
        <form id="editForm" action="${ctx}/cargo/finance/save.do" method="post">
        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">新建财务表单</div>


                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">签单日期</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="签单日期"  name="inputDate" class="form-control pull-right"
                                   value="<fmt:formatDate value="${finance.inputDate}" pattern="yyyy-MM-dd"/>" id="inputDate">
                        </div>
                    </div>
                    <div class="col-md-2 title">制单人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="制单人" name="inputBy" value="${finance.inputBy}">
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
                <h3 class="box-title">发票列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">
                    <!--数据列表-->
                    <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <th class="sorting">合同号</th>
                            <th class="sorting">发票编码</th>
                            <th class="sorting">贸易条款</th>
                            <th class="sorting">状态</th>
                            <th class="sorting">创建人</th>
                            <th class="sorting">创建部门</th>
                            <th class="sorting">创建日期</th>
                        </tr>
                        </thead>
                        <tbody class="tableBody">
                        ${links }
                        <c:forEach items="${page.list}" var="o" varStatus="status">
                            <c:if test="${o.state==1}">
                            <tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'">
                                <td><input type="radio" name="financeId" value="${o.invoiceId}"/></td>
                                <td>${status.index+1}</td>
                                <td>${o.scNo}</td>
                                <td>${o.blNo}</td>
                                <td>${o.tradeTerms}</td>
                                <td><c:if test="${o.state==0}">草稿</c:if>
                                    <c:if test="${o.state==1}"><font color="green">已上报</font></c:if>
                                    <c:if test="${o.state==2}"><font color="red">已装箱</font></c:if>
                                </td>
                                <td>${o.createBy}</td>
                                <td>${o.createDept}</td>
                                <td><fmt:formatDate value="${o.createTime}" pattern="yyyy-MM-dd"/></td>

                                <td>
                                    <a href="${ctx }/cargo/finance/toViewShow.do?id=${o.invoiceId}">[查看详细]</a>
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
                    <jsp:param value="${ctx}/cargo/finance/toAdd.do?" name="pageUrl"/>
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
    $('#inputDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#limitDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });

</script>

</html>