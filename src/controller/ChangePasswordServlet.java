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

@WebServlet(name = "ChangePasswordServlet", urlPatterns = "/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String phone = request.getParameter("phone");
        String newpassword = request.getParameter("newpassword");
        Writer writer = response.getWriter();
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("update user set password = '%s' where phone = '%s';", newpassword, phone);
            int n = statement.executeUpdate(sql);
            if (n == 1){
                writer.write("1");
            }else {
                writer.write("0");
            }
            statement.close();
            connection.close();
            writer.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
