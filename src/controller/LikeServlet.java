package controller;

import dao.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "LikeServlet", urlPatterns = "/LikeServlet")
public class LikeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String flag = request.getParameter("flag");
        String id = request.getParameter("id");  //news对应的id

        Connection connection = DBDAO.getConnection();
        try {
            String sql;
            Statement statement = connection.createStatement();
            if (flag.compareTo("like") == 0){
                sql = String.format("update usernews set likenumber=likenumber+1 where id = %s;", id);
            }else{
                sql = String.format("update usernews set likenumber=likenumber-1 where id = %s;", id);
            }
            int n = statement.executeUpdate(sql);
            if (n == 1){
                //成功
            }else{
                //失败
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
