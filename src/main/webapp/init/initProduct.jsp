<%@page import="com.search.util.StringUtils"%>
<%@page import="com.search.job.Job"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/context.jsp" %>
<%
String pids = request.getParameter("ids");
if(StringUtils.isNotBlank(pids)){
	Job job = (Job)context.getBean("job");
	List<String> pidList = new ArrayList<String>();
	try{
		String ids[] = pids.split(",");
		for(String i : ids) {
			pidList.add(i);
		}
		try{
			job.flushProduct(pidList);
		}catch(Exception e){
			out.print("{\"info\":\"flush error : "+e.getMessage()+"\"}");
		}
		out.print("{\"info\":\"Success\"}");
	}catch(Exception e){
		out.print("{\"info\":\"ids type error : "+e.getMessage()+"\"}");
	}
} else {
	out.print("{\"info\":\"ids is null\"}");
}
%>