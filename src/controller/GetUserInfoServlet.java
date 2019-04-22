package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dao.DBDAO;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

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
@WebServlet(name = "GetUserInfoServlet", urlPatterns = "/GetUserInfoServlet")
public class GetUserInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String phone = request.getParameter("phone");
        String nickname = ""; //昵称
        Integer height = 0; //身高
        Integer weight = 0; //体重
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("select * from user where phone = '%s';", phone);
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                nickname = resultSet.getString("nickname");
                height = resultSet.getInt("height");
                weight = resultSet.getInt("weight");
                //........
            }
            //System.out.println("##########################"+phone+height+weight+nickname);
            Writer writer = response.getWriter();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("nickname", nickname);
            jsonObject.addProperty("height", height);
            jsonObject.addProperty("weight", weight);

            Gson gson = new Gson();
            writer.write(gson.toJson(jsonObject));
            writer.flush();
            writer.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
