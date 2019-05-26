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

@WebServlet(name = "GetFoodNutrientServlet", urlPatterns = "/GetFoodNutrientServlet")
public class GetFoodNutrientServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String foodname = request.getParameter("foodname");

        List<JsonObject> detail_jsonObjectList = new ArrayList<>();
        Writer writer = response.getWriter();
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("select * from foodcalory where foodname = '%s';", foodname);
            ResultSet resultSet =  statement.executeQuery(sql);
            if (resultSet.next()){
                int calory = resultSet.getInt("calory"); //卡路里
                double water = resultSet.getDouble("water"); //含水量
                double protein = resultSet.getDouble("protein"); //蛋白质
                double fat = resultSet.getDouble("fat"); //脂肪
                double dietaryfiber = resultSet.getDouble("dietaryfiber"); //膳食纤维
                double carbohydrate = resultSet.getDouble("carbohydrate"); //碳水化合物
                double vitamin_e = resultSet.getDouble("vitamin_e"); //维e
                double sodium = resultSet.getDouble("sodium"); //钠
                double calcium = resultSet.getDouble("calcium"); //钙
                double iron = resultSet.getDouble("iron"); //铁
                double cholesterol = resultSet.getDouble("cholesterol"); //胆固醇


                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", "卡路里");
                jsonObject.addProperty("number", calory+"大卡");
                detail_jsonObjectList.add(jsonObject);

                jsonObject = new JsonObject();
                jsonObject.addProperty("name","水" );
                jsonObject.addProperty("number", water+"克");
                detail_jsonObjectList.add(jsonObject);

                jsonObject = new JsonObject();
                jsonObject.addProperty("name","蛋白质" );
                jsonObject.addProperty("number", protein+"克");
                detail_jsonObjectList.add(jsonObject);

                jsonObject = new JsonObject();
                jsonObject.addProperty("name","脂肪");
                jsonObject.addProperty("number", fat+"克");
                detail_jsonObjectList.add(jsonObject);

                jsonObject = new JsonObject();
                jsonObject.addProperty("name","膳食纤维" );
                jsonObject.addProperty("number", dietaryfiber+"克");
                detail_jsonObjectList.add(jsonObject);

                jsonObject = new JsonObject();
                jsonObject.addProperty("name","碳水化合物" );
                jsonObject.addProperty("number", carbohydrate+"克");
                detail_jsonObjectList.add(jsonObject);

                jsonObject = new JsonObject();
                jsonObject.addProperty("name","维他命E" );
                jsonObject.addProperty("number", vitamin_e+"毫克");
                detail_jsonObjectList.add(jsonObject);

                jsonObject = new JsonObject();
                jsonObject.addProperty("name","胆固醇" );
                jsonObject.addProperty("number", cholesterol+"毫克");
                detail_jsonObjectList.add(jsonObject);

                jsonObject = new JsonObject();
                jsonObject.addProperty("name","钠Na" );
                jsonObject.addProperty("number", sodium+"毫克");
                detail_jsonObjectList.add(jsonObject);

                jsonObject = new JsonObject();
                jsonObject.addProperty("name","钙Ca" );
                jsonObject.addProperty("number", calcium+"毫克");
                detail_jsonObjectList.add(jsonObject);

                jsonObject = new JsonObject();
                jsonObject.addProperty("name","铁Fe" );
                jsonObject.addProperty("number", iron+"毫克");
                detail_jsonObjectList.add(jsonObject);


            }
            writer.write(detail_jsonObjectList.toString());
            statement.close();
            connection.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
