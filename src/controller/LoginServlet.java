package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.DBDAO;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "LoginServlet", urlPatterns = "/LoginServlet")
public class LoginServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String phone = request.getParameter("phone"); //通过键取值
        String password = request.getParameter("password");

        Connection connection = DBDAO.getConnection();

        String sql = String.format("select phone, password from user where phone='%s';", phone);
        String phonequery;
        String passwordquery;
        Writer writer = response.getWriter();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.last(); // 将光标移动到最后一行
            int rowCount = resultSet.getRow(); // 得到当前行号，即结果集记录数
            if (rowCount == 0) {
                writer.write("-1"); //-1说明该号码没有注册过
            }
            else {
                resultSet.beforeFirst(); //回到查询指针初始化的位置
                if(resultSet.next()) {
                    phonequery = resultSet.getString("phone");
                    passwordquery = resultSet.getString("password");
                    if (phone.compareTo(phonequery) == 0 && password.compareTo(passwordquery) != 0){
                        writer.write("0"); //0说明密码错误
                    }
                    else if (phone.compareTo(phonequery) == 0 && password.compareTo(passwordquery) == 0) {
                        writer.write("1"); //1说明登录成功
                    }
                }
            }
            writer.flush();
            writer.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String phone = "";
        String password = "";

        // 连接数据库，查询数据
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("SELECT * from user where phone='18508333640';");
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                phone = resultSet.getString("phone");
                password = resultSet.getString("password");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // 向客户端回复
        Writer writer = response.getWriter();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phone", phone);
        jsonObject.addProperty("password", password);
        Gson gson = new Gson();
        writer.write(gson.toJson(jsonObject));
        writer.flush();
        writer.close();
    }
}
