<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" autoFlush="false"%>

<title>模仿天猫官网 ${p.name}</title>
<div class="categoryPictureInProductPageDiv">
    <img class="categoryPictureInProductPage" src="img/category/${p.category.id}.jpg">
</div>

<div class="productPageDiv">

    <%@include file="img_and_info.jsp" %>

    <%@include file="product_review.jsp" %>

    <%@include file="product_detail.jsp" %>
</div>