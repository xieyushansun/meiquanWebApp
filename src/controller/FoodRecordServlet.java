package controller;

import dao.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "FoodRecordServlet", urlPatterns = "/FoodRecordServlet")
public class FoodRecordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String foodname = request.getParameter("foodname");
        String foodwhen = request.getParameter("foodwhen");
        String phone = request.getParameter("phone");
        int intake = Integer.valueOf(request.getParameter("intake"));

        String str_intake = String.valueOf(intake); //将摄入量转换为String类型

        Writer writer = response.getWriter();
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("insert into foodrecord(phone, foodname, intake, foodwhen) VALUES('%s', '%s', %s, '%s');",phone, foodname, str_intake, foodwhen);
            int n = statement.executeUpdate(sql);
            if (n == 1){
                writer.write("1");  //如果成功插入一条记录就返回1
            }else{
                writer.write("0");  //如果插入失败就返回0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
