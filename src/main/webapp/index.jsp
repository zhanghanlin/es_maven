<%@page import="com.search.es.ElasticsearchNodeFactoryBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
</head>
<body>
<%
org.elasticsearch.node.internal.InternalNode node = (org.elasticsearch.node.internal.InternalNode)context.getBean("esNode");
out.print(node.getClass());
%>
</body>
</html>