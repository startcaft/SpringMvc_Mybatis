<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品列表</title>
</head>
<body>
	商品查询
	<hr>
	商品列表
	<table width="100%" border="1">
		<tr>
			<td>商品名称</td>
			<td>商品价格</td>
			<td>生产日期</td>
			<td>商品说明</td>
			<td>操作</td>
		</tr>
		<c:forEach items="${ requestScope.itemsList }" var="item"> 
			<tr>
				<td>item.name</td>
				<td>item.price</td>
				<td>item.createtime</td>
				<td>item.detail</td>
				<td>++++++</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>