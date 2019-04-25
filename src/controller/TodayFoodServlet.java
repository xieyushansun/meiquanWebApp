package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "TodayFoodServlet", urlPatterns = "/TodayFoodServlet")
public class TodayFoodServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String sportname = "";
        String sportwhen = "";

        int intake = 0;
        int calory = 0;
        double total_morning = 0;  //早上
        double total_aftermorning = 0;  //上午
        double total_noon = 0;  //中午
        double total_afternoon = 0;  //下午
        double total_night = 0;  //晚上
        double total_afternight = 0;  //深夜
        String phone = request.getParameter("phone");
        Writer writer = response.getWriter();

        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from foodrecord a join foodcalory b on a.foodname = b.foodname where DATE_FORMAT(date,'%Y-%m-%d') = CURDATE() and phone='"+phone+"';";
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                sportname = resultSet.getString("foodname");
                sportwhen = resultSet.getString("foodwhen");
                intake = resultSet.getInt("intake");
                calory = resultSet.getInt("calory");
                switch (sportwhen){
                    case "早上":total_morning += calory*intake/100;break;
                    case "上午":total_aftermorning += calory*intake/100;break;
                    case "中午":total_noon += calory*intake/100;break;
                    case "下午":total_afternoon += calory*intake/100;break;
                    case "晚上":total_night += calory*intake/100;break;
                    case "深夜":total_afternight += calory*intake/100;break;
                    default:break;
                }
            }
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("total_morning", total_morning);
            jsonObject.addProperty("total_aftermorning", total_aftermorning);
            jsonObject.addProperty("total_noon", total_noon);
            jsonObject.addProperty("total_afternoon", total_afternoon);
            jsonObject.addProperty("total_night", total_night);
            jsonObject.addProperty("total_afternight", total_afternight);

            Gson gson = new Gson();
            writer.write(gson.toJson(jsonObject));
            writer.flush();
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
