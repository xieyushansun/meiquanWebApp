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
        String image_url = ""; //动态中图片路径
        String headimage_url = ""; //头像路径
        String nickname = "";
        int likenumber = 0; //点赞数
        int id = 0;
        Timestamp timestamp;
        String sendtime;
        Writer writer = response.getWriter();
        String p;
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("select * from user_usernews where phone in (select followphone from follow where phone = '%s') order by sendtime DESC", phone);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                content = resultSet.getString("content");
                timestamp = resultSet.getTimestamp("sendtime");
                image_url = resultSet.getString("image_url");
                headimage_url = resultSet.getString("headimage_url");
                nickname = resultSet.getString("nickname");
                likenumber = resultSet.getInt("likenumber");
                id = resultSet.getInt("id");
                p = resultSet.getString("phone");
                sendtime = timestamp.toString();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("content", content);
                jsonObject.addProperty("sendtime", sendtime);
                jsonObject.addProperty("phone", p);
                jsonObject.addProperty("image_url", image_url);
                jsonObject.addProperty("headimage_url", headimage_url);
                jsonObject.addProperty("nickname", nickname);
                jsonObject.addProperty("likenumber", likenumber);
                jsonObject.addProperty("id", id);
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
