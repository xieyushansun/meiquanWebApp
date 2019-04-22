package controller;

import com.google.gson.Gson;
import dao.DBDAO;
import entity.FoodCalory;
import entity.SportCalory;

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

@WebServlet(name = "FoodCaloryServlet", urlPatterns = "/FoodCaloryServlet")
public class FoodCaloryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        Writer writer = response.getWriter();

        List<FoodCalory> foodCaloryList = new ArrayList<>();

        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from foodcalory";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){

               FoodCalory foodCalory = new FoodCalory();
               foodCalory.setId(resultSet.getInt("id"));
               foodCalory.setFoodname(resultSet.getString("foodname"));
               foodCalory.setCalory(resultSet.getInt("calory"));
               foodCaloryList.add(foodCalory);

            }
            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        writer.write(new Gson().toJson(foodCaloryList));
        writer.flush();
        writer.close();
    }
}
