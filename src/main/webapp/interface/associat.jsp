<%@page import="com.search.util.StringUtils"%>
<%@page import="com.search.bean.Brand"%>
<%@page import="java.util.List"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="com.search.es.BrandFacade"%>
<%@page import="com.search.es.bussiness.BrandFacadeImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/context.jsp" %>
<%
String key = request.getParameter("key");
if(StringUtils.isNotBlank(key)) {
	BrandFacadeImpl brandFacede = (BrandFacadeImpl)context.getBean(BrandFacade.BEAN_ID);
	List<Brand> items = brandFacede.associateWord(key);
	out.print(JSONObject.toJSONString(items));
} else {
	out.print("{}");
}
%>