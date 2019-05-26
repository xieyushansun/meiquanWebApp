package controller;

import com.google.gson.JsonObject;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetTodayCaloryDetailServlet", urlPatterns = "/GetTodayCaloryDetailServlet")
public class GetTodayCaloryDetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String phone = request.getParameter("phone");
        String foodOrsport = request.getParameter("foodOrsport");

        List<JsonObject> detailList_jsonObject = new ArrayList<>();
        Writer writer = response.getWriter();
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "";
            if (foodOrsport.compareTo("food") == 0){
                sql = String.format("select * from foodrecord where phone = '%s';", phone);
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()){
                    String foodname = resultSet.getString("foodname");
                    String intake = resultSet.getString("intake");
                    String foodwhen = resultSet.getString("foodwhen");
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("name", foodname);
                    jsonObject.addProperty("amount", intake+"克");
                    jsonObject.addProperty("when", foodwhen);
                    detailList_jsonObject.add(jsonObject);
                }
                resultSet.close();
            }else if (foodOrsport.compareTo("sport") == 0){
                sql = String.format("select * from sportrecord where phone = '%s';", phone);
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()){
                    String sportname = resultSet.getString("sportname");
                    String duration = resultSet.getString("duration");
                    String sportwhen = resultSet.getString("sportwhen");

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("name", sportname);
                    DecimalFormat df = new DecimalFormat("#.00");
                    double d = Double.valueOf(duration);
                    duration = df.format(d);
                    jsonObject.addProperty("amount", duration+"小时");
                    jsonObject.addProperty("when", sportwhen);
                    detailList_jsonObject.add(jsonObject);
                }
                resultSet.close();
            }
            writer.write(detailList_jsonObject.toString());
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
