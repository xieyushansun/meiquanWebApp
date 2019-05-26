package controller;

import com.google.gson.JsonObject;
import dao.DBDAO;
import util.GetUserTodayTotalFoodCalory;
import util.GetUserTodayTotalSportCalory;

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

@WebServlet(name = "GetRankServlet", urlPatterns = "/GetRankServlet")
public class GetRankServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        List<JsonObject> followusercalory_jsonObjectList = new ArrayList<>();
        String phone = request.getParameter("phone");
        String rankway = request.getParameter("rankway");
        Connection connection = DBDAO.getConnection();

        Writer writer = response.getWriter();

        try {
            Statement statement = connection.createStatement();
            String sql1 = String.format("select * from follow_user where phone = '%s';", phone);
            ResultSet resultSet = statement.executeQuery(sql1);
            while (resultSet.next()){
                String followphone = resultSet.getString("followphone");
                String headimage_url = resultSet.getString("headimage_url");
                String nickname = resultSet.getString("nickname");

                GetUserTodayTotalFoodCalory getUserTodayTotalFoodCalory = new GetUserTodayTotalFoodCalory();
                int total_foodcalory = (int)getUserTodayTotalFoodCalory.getFoodCalory(followphone);

                GetUserTodayTotalSportCalory getUserTodayTotalSportCalory = new GetUserTodayTotalSportCalory();
                int total_sportcalory = (int)getUserTodayTotalSportCalory.getSportCalory(followphone);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("followphone", followphone);
                jsonObject.addProperty("headimage_url", headimage_url);
                jsonObject.addProperty("nickname", nickname);
                jsonObject.addProperty("total_foodcalory", total_foodcalory);
                jsonObject.addProperty("total_sportcalory", total_sportcalory);

                followusercalory_jsonObjectList.add(jsonObject);
            }
            writer.write(followusercalory_jsonObjectList.toString());
            writer.close();
            resultSet.close();
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
