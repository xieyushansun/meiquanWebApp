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
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.sql.*;

@WebServlet(name = "GetUserInfoServlet", urlPatterns = "/GetUserInfoServlet")
public class GetUserInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String phone = request.getParameter("phone");
        String sex = "";
        String nickname = ""; //昵称
        Integer height = 0; //身高
        Integer weight = 0; //体重
        String headimage_url = "";
        double sportintensity = 0;
        int birthyear = 0;
        int birthmonth = 0;
        int birthday = 0;
        String province = "";
        String city = "";
        String type = "";
        Date date = null;
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("select * from user where phone = '%s';", phone);
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                sex = resultSet.getString("sex");
                nickname = resultSet.getString("nickname");
                height = resultSet.getInt("height");
                weight = resultSet.getInt("weight");
                headimage_url = resultSet.getString("headimage_url");
                sportintensity = resultSet.getDouble("sportintensity");
                date = resultSet.getDate("birth");
                province = resultSet.getString("province");
                city = resultSet.getString("city");
                type = resultSet.getString("type");
                //........
            }

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("sex", sex);
            jsonObject.addProperty("nickname", nickname);
            jsonObject.addProperty("height", height);
            jsonObject.addProperty("weight", weight);

            jsonObject.addProperty("headimage_url", headimage_url);
            jsonObject.addProperty("sportintensity", sportintensity);


            String []s = date.toString().split("-");
            jsonObject.addProperty("birthyear", s[0]);
            jsonObject.addProperty("birthmonth", s[1]);
            jsonObject.addProperty("birthday", s[2]);
            jsonObject.addProperty("province", province);
            jsonObject.addProperty("city", city);
            jsonObject.addProperty("type", type);


            if (!headimage_url.isEmpty()){
                File file = new File(headimage_url);
                if (file.exists()){

                }
            }

            Writer writer = response.getWriter();
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
