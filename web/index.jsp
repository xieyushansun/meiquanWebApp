<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="dao.DBDAO" %><%--
  Created by IntelliJ IDEA.
  User: xie
  Date: 2019/4/8
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  成功配置了 tomcat.
  </body>

  <%
    try {
      // 建立连接
      Connection con = DBDAO.getConnection();
      // 创建状态
      Statement state = con.createStatement();
      // 查询
      String sql = String.format("SELECT * from user;");
      ResultSet rs = state.executeQuery(sql);
      while (rs.next()) {
        String phone = rs.getString("phone");
        String password = rs.getString("password");

        out.println("phone:" + phone + "    password:" + password  + "<br>");
      }
      state.close();
      con.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  %>
</html>
