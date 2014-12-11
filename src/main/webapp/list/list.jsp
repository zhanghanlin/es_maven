<%@page import="com.search.es.ProductFacade"%>
<%@page import="com.search.bean.Product"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="com.search.es.bussiness.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	Long id = 8260L;
ProductFacadeImpl productFacade = (ProductFacadeImpl)context.getBean(ProductFacade.BEAN_ID);
Product product = productFacade.get(id);
out.print(JSONObject.toJSONString(product));
%>
</body>
</html>