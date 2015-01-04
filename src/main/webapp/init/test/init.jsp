<%@page import="com.search.bean.Brand"%>
<%@page import="com.search.bean.Product"%>
<%@page import="com.search.util.StringUtils"%>
<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="com.search.util.Constants"%>
<%@page import="com.search.util.Jerseys"%>
<%@page import="com.sun.jersey.api.client.WebResource"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/context.jsp" %>
<%
int proCount = 100;
for(int i = 0; i < proCount;i++){
	Product t = new Product();
	int id = StringUtils.randomInt(1000, 9999);
	String name = "p" + StringUtils.randomChinese(5);
	t.setId(Long.valueOf(id));
	t.setName(name);
	WebResource client = Jerseys.createClient(Constants.BASE_URL);
	WebResource wr = client.path("/" + Constants.GLOBAL_INDEX_NAME + "/product/" + id);
	String pjson = JSON.toJSON(t).toString();
	wr.put(pjson);
}

int brandCount = 100;
for(int i = 0; i < brandCount;i++){
	Brand t = new Brand();
	int id = StringUtils.randomInt(1000, 9999);
	String name ="b" +  StringUtils.randomChinese(4);
	t.setId(Long.valueOf(id));
	t.setName(name);
	WebResource client = Jerseys.createClient(Constants.BASE_URL);
	WebResource wr = client.path("/" + Constants.GLOBAL_INDEX_NAME + "/brand/" + id);
	String pjson = JSON.toJSON(t).toString();
	wr.put(pjson);
}
%>