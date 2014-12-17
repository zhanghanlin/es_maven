<%@page import="com.search.es.BrandFacade"%>
<%@page import="com.search.bean.ExtendObject"%>
<%@page import="com.search.es.ProductFacade"%>
<%@page import="com.search.bean.Product"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="com.search.es.bussiness.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/context.jsp" %>
<%
String type = "";
try{
	Long id = Long.valueOf(request.getParameter("id"));
	type = request.getParameter("type");
	ExtendObject t = null;
	if ("product".equals(type)) {
		ProductFacadeImpl productFacade = (ProductFacadeImpl)context.getBean(ProductFacade.BEAN_ID);
		t = productFacade.get(id);
	} else if ("product".equals(type)) {
		BrandFacadeImpl brandFacadeImpl = (BrandFacadeImpl)context.getBean(BrandFacade.BEAN_ID);
		t = brandFacadeImpl.get(id);
	}
	out.print(JSONObject.toJSONString(t));
}catch(Exception e){
	out.print("{\"info\":\"get "+type+" error : "+e.getMessage()+"\"}");
}
%>