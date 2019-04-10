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

@WebServlet(name = "RegistServlet", urlPatterns = "/RegistServlet")
public class RegistServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("#####################################");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String passwordAgain = request.getParameter("passwordAgain");
        System.out.println("########################"+"phone = "+phone+"password = "+password+"passwordAgain = "+passwordAgain);
        int flag = 0; //标志该号码是否已经被注册
        Writer writer = response.getWriter();
        Connection con = DBDAO.getConnection();
        try {
            Statement statement = con.createStatement();
            String sql = "select phone from user;";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                if (resultSet.getString("phone").compareTo(phone) == 0){
                    writer.write("2");
                    flag = 1; //该号码已经被注册
                    break;
                }
            }
            if (flag == 0){
                if (phone.length() != 11) {
                    writer.write("0"); //电话号码长度不合法
                    flag = 1;
                }
                else if (flag == 0&&password.compareTo(passwordAgain) != 0){
                    writer.write("1"); //两次号码输入不一致
                    flag = 1;
                }
            }
            if (flag == 0){
                String sqlinsert = String.format("insert into user(phone, password) values('%s', '%s');", phone, password);
                int result = statement.executeUpdate(sqlinsert);
                if (result == 1) {
                    writer.write("3"); //注册成功
                }

            }

            writer.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
