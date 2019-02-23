<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/admin_header.jsp"%>
<%@include file="../include/admin/admin_navigator.jsp"%>

<script>
</script>

<title>用户管理</title>

<div class="workingArea">
    <h1 class="label label-info" >用户管理</h1>

    <br>
    <br>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
            <tr class="success">
                <th>ID</th>
                <th>用户名称</th>
                <th>编辑</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="u">
                <tr>
                    <td>${u.id}</td>
                    <td>${u.name}</td>
                    <td><a href="admin_user_edit?id=${u.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
                    <td><a deleteLink="true" href="admin_user_delete?id=${u.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%@include file="../include/admin/admin_page.jsp"%>
    </div>

    <div class="panel panel-warning addDiv">
        <div class="panel-heading">新增用户</div>
        <div class="panel-body">
            <form method="post" id="addForm" action="admin_user_add">
                <table class="addUsername">
                    <tr>
                        <td>用户名称</td>
                        <td><input  id="name" name="name" type="text" class="form-control"></td>
                    </tr>

                    <tr>
                        <td>用户密码</td>
                        <td><input  id="pwd" name="pwd" type="text" class="form-control"></td>
                    </tr>

                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>


</div>

<%@include file="../include/admin/admin_footer.jsp"%>








