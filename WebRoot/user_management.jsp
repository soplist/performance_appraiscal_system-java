<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>user management</title>
    <link rel="stylesheet" href="css/user_management.css" type="text/css"></link>
  </head>
  
  <body>
    <s:form action="insertUserResume" method="post" enctype="multipart/form-data">
      <s:textfield name="realName" label="realName"></s:textfield>
      <s:textfield name="departmentId" label="departmentId"></s:textfield>
      <s:textfield name="position" label="position"></s:textfield>
      <s:textfield name="joinTime" label="joinTime"></s:textfield>
      <s:select name="status" list="#{'false':'false','true':'true'}" listKey="key" listValue="value"/>
      <s:file name="photo"></s:file>
      <s:submit label="submit" value="insert"></s:submit>
    </s:form>
    
    <s:iterator value="#session.userResumeList" id="ur"> 
      <div class="div_user_item">
        <s:property value="#ur.id"/>
        <s:property value="#ur.realName"/>
        <s:property value="#ur.departmentId"/>
        <s:property value="#ur.position"/>
        <s:property value="%{getText('{0,date,yyyy-MM-dd }',{#ur.joinTime})}"/>
        <s:property value="#ur.status"/>
      </div>
    </s:iterator>
  </body>
</html>
