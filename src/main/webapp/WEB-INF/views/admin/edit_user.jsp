<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/admin_header.jsp"%>
<%@include file="../include/admin/admin_navigator.jsp"%>

<title>编辑用户</title>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_user_list">所有用户</a></li>
        <li><a href="admin_user_list?uid=${user.id}">${user.name}</a></li>
        <li class="active">编辑用户</li>
    </ol>

    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑用户</div>
        <div class="panel-body">
            <form method="post" id="editForm" action="admin_user_editP">
                <table class="editTable">
                    <tr>
                        <td>
                            用户名
                        </td>
                        <td>
                            <input id="name" name="name" value="${user.name}"
                                   type="text" class="form-control">
                        </td>
                    </tr>

                    <tr>
                        <td>
                            密码
                        </td>
                        <td>
                            <input id="password" name="password" value="${user.password}"
                                   type="password" class="form-control">
                        </td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="id" value="${user.id}">

                            <button type="submit" class="btn btn-success">提 交</button></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>