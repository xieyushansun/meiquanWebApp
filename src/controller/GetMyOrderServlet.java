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

@WebServlet(name = "GetMyOrderServlet", urlPatterns = "/GetMyOrderServlet")
public class GetMyOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        List<JsonObject> myorder_jsonObjectList = new ArrayList<>();
        String userphone = request.getParameter("userphone");
        Writer writer = response.getWriter();
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("select * from buyrecord_commodity where userphone = '%s';", userphone);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String commodity_name = resultSet.getString("commodity_name");
                String price = resultSet.getString("price");
                String commodity_imageurl = resultSet.getString("commodity_imageurl");
                String buynumber = resultSet.getString("buynumber");
                String totalmoney = resultSet.getString("totalmoney");
                Timestamp timestamp = resultSet.getTimestamp("buytime");
                String buytime = timestamp.toString();
                String isaccepted = resultSet.getString("isaccepted");
                String id_buyrecord = resultSet.getString("id_buyrecord");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id_buyrecord", id_buyrecord);
                jsonObject.addProperty("commodity_name", commodity_name);
                jsonObject.addProperty("price", price);
                jsonObject.addProperty("commodity_imageurl", commodity_imageurl);
                jsonObject.addProperty("buynumber", buynumber);
                jsonObject.addProperty("totalmoney", totalmoney);
                jsonObject.addProperty("buytime", buytime);
                jsonObject.addProperty("isaccepted", isaccepted);

                myorder_jsonObjectList.add(jsonObject);
            }
            writer.write(myorder_jsonObjectList.toString());
            writer.close();
            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
