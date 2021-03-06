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

@WebServlet(name = "GetAllMyFollowsServlet", urlPatterns = "/GetAllMyFollowsServlet")
public class GetAllMyFollowsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String phone = request.getParameter("phone");

        String followphone = "";
        String nickname = "";

        Connection connection = DBDAO.getConnection();
        Writer writer = response.getWriter();
        List<JsonObject> follow_jsonObjectList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String sql = String.format("select * from follow_user where phone='%s';", phone);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                followphone = resultSet.getString("followphone");
                if (followphone.compareTo(phone) == 0){
                    continue;
                }
                nickname = resultSet.getString("nickname");
                String headimage_url = resultSet.getString("headimage_url");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("followphone", followphone);
                jsonObject.addProperty("nickname", nickname);
                jsonObject.addProperty("headimage_url", headimage_url);
                follow_jsonObjectList.add(jsonObject);
            }
            writer.write(follow_jsonObjectList.toString());
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
