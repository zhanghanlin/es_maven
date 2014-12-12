<%@page import="com.search.es.ProductFacade"%>
<%@page import="com.search.bean.Product"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="com.search.es.bussiness.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/context.jsp" %>
<%
try{
	Long id = Long.valueOf(request.getParameter("id"));
	ProductFacadeImpl productFacade = (ProductFacadeImpl)context.getBean(ProductFacade.BEAN_ID);
	Product product = productFacade.get(id);
	out.print(JSONObject.toJSONString(product));
}catch(Exception e){
	out.print("{\"info\":\"get Product error : "+e.getMessage()+"\"}");
}
%>