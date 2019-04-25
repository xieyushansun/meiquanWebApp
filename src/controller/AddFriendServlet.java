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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "AddFriendServlet", urlPatterns = "/AddFriendServlet")
public class AddFriendServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        Writer writer = response.getWriter();
        String phone = request.getParameter("phone");
        String followphone = request.getParameter("followphone");
        String temp;
        int flag = 0; //0表示没有被注册
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            /*
            判断这个号码是否被注册了
             */
            String sql1 = "select * from user";
            ResultSet resultSet = statement.executeQuery(sql1);
            while (resultSet.next()){
                temp = resultSet.getString("phone");
                if (temp.compareTo(followphone) == 0){ //表示这个号码是被注册的
                    String sql2 = String.format("insert into follow (phone, followphone) values('%s', '%s')", phone, followphone);
                    int i = statement.executeUpdate(sql2);
                    if (i == 1){
                        writer.write("1");  //成功
                    }else {
                        writer.write("0");  //失败
                    }
                    flag = 1;
                    break;
                }
            }
            if (flag == 0){
                writer.write("-1"); //没有这个用户
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
