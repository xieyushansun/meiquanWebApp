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

@WebServlet(name = "GetNewsCommentServlet", urlPatterns = "/GetNewsCommentServlet")
public class GetNewsCommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        List<JsonObject> comment_jsonObjectList = new ArrayList<>();
        String newsid = request.getParameter("newsid");
        String content = "";
        String nickname = "";
        Writer writer = response.getWriter();
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("select * from comment_user where newsid=%s", newsid);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                nickname = resultSet.getString("nickname");
                content = resultSet.getString("content");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("nickname", nickname);
                jsonObject.addProperty("content", content);
                comment_jsonObjectList.add(jsonObject);
            }
            writer.write(comment_jsonObjectList.toString());
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
