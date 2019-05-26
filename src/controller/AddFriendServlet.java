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

        /*
          判断这个用户是否为自己
        */
        if (phone.compareTo(followphone) == 0){
            writer.write("-1");  //不能关注自己
            writer.close();
            return;
        }
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            /*
            判断是否已经关注该用户
             */
            String sql1 = String.format("select followphone from follow where phone = '%s';", phone);
            ResultSet resultSet1 = statement.executeQuery(sql1);
            while (resultSet1.next()){
                if (resultSet1.getString("followphone").compareTo(followphone) == 0){
                    writer.write("-2");  //已经关注该用户了
                    writer.close();
                    statement.close();
                    resultSet1.close();
                    connection.close();
                    return;
                }
            }
            /*
            判断这个号码是否是被注册用户
             */
            int flag_isIllegallphonenumber = 0; //不合法
            String sql2 = "select * from user";
            ResultSet resultSet2 = statement.executeQuery(sql2);
            while (resultSet2.next()){
                String temp = resultSet2.getString("phone");
                if (temp.compareTo(followphone) == 0){ //表示这个号码是被注册的
                    flag_isIllegallphonenumber = 1;
                    String sql3 = String.format("insert into follow (phone, followphone) values('%s', '%s')", phone, followphone);
                    int i = statement.executeUpdate(sql3);
                    if (i == 1){
                        writer.write("1");  //成功
                    }else {
                        writer.write("0");  //失败
                    }
                    break;
                }
            }
            if(flag_isIllegallphonenumber == 0){
                writer.write("-3"); //没有该用户
            }
            writer.close();
            statement.close();
            resultSet2.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
