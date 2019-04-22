package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dao.DBDAO;
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

@WebServlet(name = "SportCaloryServlet", urlPatterns = "/SportCaloryServlet")
public class SportCaloryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        Writer writer = response.getWriter();

        /*JsonArray jsonArray = new JsonArray();
        Connection connection = DBDAO.getConnection();
        try {
            String sql = "select sportname, calory from sportcalory;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                sportName = resultSet.getString("sportname");
                calory = resultSet.getInt("calory");
                System.out.println(""+sportName+calory);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(sportName, calory);
                jsonArray.add(jsonObject);
            }
            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        writer.write(gson.toJson(jsonArray));*/
        List<SportCalory> sportCaloryList = new ArrayList<>();
        Connection connection = DBDAO.getConnection();
        try {
            //Statement statement = connection.createStatement();
            String sql = "select * from sportcalory;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                SportCalory sportCalory = new SportCalory();
                sportCalory.setId(resultSet.getInt("id"));
                sportCalory.setSportname(resultSet.getString("sportname"));
                sportCalory.setCalory(resultSet.getInt("calory"));
                sportCaloryList.add(sportCalory);
            }

            statement.close();
            resultSet.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        writer.write(new Gson().toJson(sportCaloryList));

        writer.flush();
        writer.close();
    }
}
