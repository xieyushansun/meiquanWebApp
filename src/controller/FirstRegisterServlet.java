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
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;

@WebServlet(name = "FirstRegisterServlet", urlPatterns = "/FirstRegisterServlet")
public class FirstRegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");


        /*int birtyear = Integer.valueOf(request.getParameter("birthyear"));
        int birthmonth = Integer.valueOf(request.getParameter("birthmonth"));
        int birthday = Integer.valueOf(request.getParameter("birthday"));
        String type = request.getParameter("type");*/
        String phone = request.getParameter("phone");
        String nickname = request.getParameter("nickname");
        String birthyear = request.getParameter("birthyear");
        String birthmonth = request.getParameter("birthmonth");
        String birthday = request.getParameter("birthday");
        String birth=birthyear+"/"+birthmonth+"/"+birthday;
        String sex = request.getParameter("sex");
        String weight = request.getParameter("weight");
        String height = request.getParameter("height");
        String type = request.getParameter("type");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String sportintensity = request.getParameter("sportintensity");

        switch (sportintensity){
            case "A":sportintensity = "1.2";break;
            case "B":sportintensity = "1.3";break;
            case "C":sportintensity = "1.5";break;
            case "D":sportintensity = "1.7";break;
            case "E":sportintensity = "1.9";break;
            default:break;
        }

        Writer writer = response.getWriter();
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("update user set nickname='%s', birth='%s', type='%s', sex='%s', province='%s', city='%s', height='%s', weight='%s', sportintensity=%s where phone='%s';", nickname, birth, type, sex, province, city, height, weight, sportintensity, phone);
            int n = statement.executeUpdate(sql);
            if (n == 1){
                writer.write("1");  //成功插入
            }else {
                writer.write("0");  //失败
            }
            String sql2 = String.format("insert into follow(phone, followphone) values('%s', '%s');", phone, phone);
            statement.executeUpdate(sql2);

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
