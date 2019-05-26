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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetWeekAdviceServlet", urlPatterns = "/GetWeekAdviceServlet")
public class GetWeekAdviceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        List<JsonObject> advice_jsonObjectList = new ArrayList<>();
        String eattingtime = request.getParameter("eattingtime"); //获取饮食时间
        String totalcalory = request.getParameter("totalcalory");
        String temp = request.getParameter("dayofweek");
        int dayofweek = Integer.valueOf(temp);

        int i_totalcalory = Integer.valueOf(totalcalory);
        int grade = 0; //目前分为
        if (i_totalcalory >= 1500 && i_totalcalory <= 1750){
            grade = 0;
        }else if (i_totalcalory >= 1750 && i_totalcalory <= 2000){
            grade = 1;
        }else if (i_totalcalory >= 2000 && i_totalcalory <= 2250){
            grade = 2;
        }else if (i_totalcalory >= 2250 && i_totalcalory <= 2500){
            grade = 3;
        }else if (i_totalcalory >= 2500){
            grade = 4;
        }
        Writer writer = response.getWriter();
        String foodname; //食物名称
        int calory; //食物卡路里
        String intake; //饮食量
        int groupnumber; //组号
        int group_totalcalory; //标志这一组总的卡路里
        Connection connection = DBDAO.getConnection();

        groupnumber = dayofweek + 7*grade + 1;

        try {
            String sql = "";
            Statement statement = connection.createStatement();

            sql = String.format("select * from advice_calory_view where groupnumber=%s and eattingtime = '%s';", groupnumber, eattingtime);

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                foodname = resultSet.getString("foodname");
                calory = resultSet.getInt("calory");
                intake = resultSet.getString("intake");
                group_totalcalory = resultSet.getInt("totalcalory");

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("foodname", foodname);
                jsonObject.addProperty("calory", calory);
                jsonObject.addProperty("intake", intake);
                jsonObject.addProperty("group_totalcalory", group_totalcalory);
                advice_jsonObjectList.add(jsonObject);
            }
            writer.write(advice_jsonObjectList.toString());
            writer.close();
            statement.close();
            resultSet.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
