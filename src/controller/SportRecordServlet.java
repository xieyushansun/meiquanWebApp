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

@WebServlet(name = "SportRecordServlet", urlPatterns = "/SportRecordServlet")
public class SportRecordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String sportname = request.getParameter("sportname");
        String sportwhen = request.getParameter("sportwhen");
        String phone = request.getParameter("phone");
        int sporthour = Integer.valueOf(request.getParameter("sporthour"));
        int sportminute = Integer.valueOf(request.getParameter("sportminute"));
        double duration = sporthour+(double)sportminute/60;  //将x小时x分钟转为x.x小时
        String str_duration = String.valueOf(duration);
        Connection connection = DBDAO.getConnection();
        Writer writer = response.getWriter();


        try {
            Statement statement = connection.createStatement();
            String sql = String.format("insert into sportrecord (phone, sportname, sportwhen, duration)VALUES('%s', '%s', '%s', %s);",phone, sportname, sportwhen, str_duration);
            int n = statement.executeUpdate(sql);
            if (n == 1) {
                writer.write("1"); //1表示成功
            }else {
                writer.write("0"); //0表示失败
            }
            writer.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
