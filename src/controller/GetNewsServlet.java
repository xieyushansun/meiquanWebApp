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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetNewsServlet", urlPatterns = "/GetNewsServlet")
public class GetNewsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String phone = request.getParameter("phone");
        List<JsonObject> new_jsonObjectList = new ArrayList<>();

        String content = "";
        Timestamp timestamp;
        String sendtime;
        Writer writer = response.getWriter();
        String p;
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("select * from usernews where phone in (select followphone from follow where phone = '%s')", phone);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                content = resultSet.getString("content");
                timestamp = resultSet.getTimestamp("sendtime");
                p = resultSet.getString("phone");
                sendtime = timestamp.toString();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("content", content);
                jsonObject.addProperty("sendtime", sendtime);
                jsonObject.addProperty("phone", p);
                new_jsonObjectList.add(jsonObject);
            }
            writer.write(new_jsonObjectList.toString());
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
